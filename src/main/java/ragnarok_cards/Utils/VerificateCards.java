package ragnarok_cards.Utils;


import jdk.nashorn.internal.runtime.options.Option;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import ragnarok_cards.Items.RagnarokCard;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static ragnarok_cards.Utils.DamageMultiplier.ApplyDamageMultiplier;
import static ragnarok_cards.Utils.DefenseMultiplier.ApplyDefenseMultiplier;
import static ragnarok_cards.Utils.SecundaryEffectsAttack.ApplyAttackSecundayEffects;
import static ragnarok_cards.Utils.onKill.ApplyOnKillEffects;

public class VerificateCards {
    static public final Random rand = new Random();
    static protected Map<String, Integer> cardNameToChance = new HashMap<String, Integer>();
    static{
        cardNameToChance.put("blaze",30);
        cardNameToChance.put("snowman", 5);
        cardNameToChance.put("wolf", 20);
        cardNameToChance.put("ocelot", 30);
        cardNameToChance.put("whiter_skeleton", 2);
        cardNameToChance.put("zombie_piglin", 4);
        cardNameToChance.put("cave_spider", 4);
        cardNameToChance.put("creeper", 20);
        cardNameToChance.put("phantom", 2);//if pass, activate negative effect
        cardNameToChance.put("sheep", 2);//if pass, activate negative effect
        cardNameToChance.put("witch",3);
    }

    static public boolean passCheck(int quantity, double checkMultiplier){
        return (rand.nextInt(100 ) < quantity * checkMultiplier );
    }
    static public boolean passCheck(String cardName,  Map<String, Integer> cards){
        return rand.nextInt(100 ) < cards.get(cardName) * cardNameToChance.get(cardName) ;
    }

    static public int HowManyCards(PlayerEntity player, String cardName){
        int quantity = 0;

        //go through upper inventory row
        for(int i=9;i<18;i++){
            Item item = player.inventory.mainInventory.get(i).getItem();

            if(item instanceof RagnarokCard && ((RagnarokCard)item).getCardName().equals(cardName)){
                quantity += 1;
            }
        }
        return quantity;
    }

    static public Map<String, Integer> HowManyCards(PlayerEntity player){
        Map<String, Integer> quantity = new HashMap<String, Integer>();

        //go through upper inventory row
        for(int i=9;i<18;i++){
            Item item = player.inventory.mainInventory.get(i).getItem();
            if(!(item instanceof RagnarokCard)){
                continue;
            }

            String cardName = ((RagnarokCard)item).getCardName();
            if(quantity.containsKey(cardName)){
                quantity.put(cardName, quantity.get(cardName) + 1);
            }else{
                quantity.put(cardName,1);
            }
        }
        return quantity;
    }

    static public boolean SingleCardActivate (PlayerEntity player, String cardName, double multiplier){
        int quantity = HowManyCards(player, cardName);
        return passCheck(quantity,cardNameToChance.get(cardName) * multiplier);
    }

    static public boolean SingleCardActivate (PlayerEntity player, String cardName) {
        return SingleCardActivate (player, cardName, 1);
    }


    static public float ApplyAttackCards(float damage, DamageSource source, LivingEntity target){
        PlayerEntity player = (PlayerEntity) source.getTrueSource();
        Map<String, Integer> cards = HowManyCards(player);

        ApplyAttackSecundayEffects (source,target, cards);

        damage = ApplyDamageMultiplier (damage, source, target,cards);

        if(damage >= target.getHealth()){
            ApplyOnKillEffects(source,target, cards);
        }
        return damage;
    }

    static public float ApplyDefenseCards(float damage, PlayerEntity player, DamageSource source){
        Map<String, Integer> cards = HowManyCards(player);


        return ApplyDefenseMultiplier (damage, player, source,cards);
    }

    static public boolean isFlyingThing(LivingEntity entity){
        boolean result = entity instanceof GhastEntity || entity instanceof PhantomEntity;
        result |= entity instanceof EnderDragonEntity || entity instanceof WitherEntity;
        result |= entity instanceof BatEntity || entity instanceof BeeEntity;
        result |= entity instanceof ParrotEntity || entity instanceof SquidEntity;
        result |= entity instanceof BlazeEntity || entity instanceof VexEntity;
        return result;
    }

    static public boolean isEnderThing(LivingEntity entity){
        boolean result = entity instanceof EndermanEntity || entity instanceof ShulkerEntity;
        result |= entity instanceof EnderDragonEntity || entity instanceof EndermiteEntity;
        return result;
    }
}
