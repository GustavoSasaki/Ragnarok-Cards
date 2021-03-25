package ragnarok_cards.Events;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.monster.SpiderEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import ragnarok_cards.Items.RagnarokCard;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VerificateCards {
    static protected final Random rand = new Random();
    static protected Map<String, Integer> cardNameToChance = new HashMap<String, Integer>();
    static{
        cardNameToChance.put("snowman", 5);
        cardNameToChance.put("wolf", 20);
        cardNameToChance.put("ocelot", 30);
        cardNameToChance.put("whiter_skeleton", 2);
    }

    static public boolean SingleCardActivate (PlayerEntity player, String cardName){
        int quantity = HowManyCards(player, cardName);

        return passCheck(quantity,cardNameToChance.get(cardName) );
    }

    static public void CardAttackActivate (PlayerEntity player, LivingEntity target, Map<String, Integer> cards){
        if(cards.containsKey("cave_spider")) {
            if (passCheck(cards.get("cave_spider"), 0.03f)) {
                //6seconds of poison
                target.addPotionEffect(new EffectInstance(Effects.POISON, 1000));
            }
        }
        if(cards.containsKey("whiter_skeleton")) {
            if (passCheck(cards.get("whiter_skeleton"), cardNameToChance.get("whiter_skeleton"))) {
                //12seconds of poison
                target.addPotionEffect(new EffectInstance(Effects.WITHER, 2000));
            }
        }
    }

    static private boolean passCheck(int quantity, float checkMultiplier){
        return (rand.nextInt(100 ) < quantity * checkMultiplier );
    }

    static public float ApplyDamageMultiplier (float damage, PlayerEntity player, LivingEntity target){
        Map<String, Integer> cards = HowManyCards(player);

        float multiplier = 1;
        float flatDamageBuff = 0;

        if(cards.containsKey("zombie")) {
            if (target.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
                multiplier *= 1 + 0.2 * cards.get("zombie");
            }
        }

        if(cards.containsKey("sheep")) {
            if (target instanceof AnimalEntity) {
                multiplier *= 1 + 0.25 * cards.get("sheep");
            }
        }

        if(cards.containsKey("spider")) {
            if (target.getCreatureAttribute() == CreatureAttribute.ARTHROPOD) {
                multiplier *= 1 + 0.25 * cards.get("spider");
                //add slow for 1.5s
                target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 250,4));
            }
        }

        if(cards.containsKey("phantom")) {
            if (target instanceof FlyingEntity) {
                multiplier *= 1 + 0.25 * cards.get("phantom");
            }
        }

        if(cards.containsKey("pig")) {
            if (target instanceof PigEntity || target instanceof AbstractPiglinEntity) {
                multiplier *= 1 + 0.25 * cards.get("pig");
            }
        }

        if(cards.containsKey("witch")) {
            double witch_multiplier = 1 + 0.2 * cards.get("witch");
            multiplier *= Math.pow(witch_multiplier, target.getActivePotionEffects().size());
        }

        if(cards.containsKey("piglin")) {
            //detec using golden sword
            flatDamageBuff += 1 * cards.get("piglin");
        }

        if(cards.containsKey("whiter_skeleton")) {
            if (target.getActivePotionEffect(Effects.WITHER).getDuration() > 0 ) {
                multiplier *= 1 + 0.5 * cards.get("whiter_skeleton");
                System.out.println("WHITER");
            }
        }

        // bonus attack when player consecutively hitting same type of enemy
        if(cards.containsKey("skeleton")) {
            CompoundNBT nbt = player.getPersistentData();

            if (!nbt.contains("lastTypeEnemyHitten")){
                nbt.putString("lastTypeEnemyHitten","none");
                nbt.putInt("consecutivelyHits",0);
            }

            String lastTypeEnemyHitten = nbt.getString("lastTypeEnemyHitten");
            int consecutivelyHits;

            if(lastTypeEnemyHitten.equals( target.getType().toString() ) ){
                consecutivelyHits = nbt.getInt("consecutivelyHits");
                consecutivelyHits += cards.get("skeleton");

                multiplier += 0.01 * consecutivelyHits;
            }else{
                consecutivelyHits = 1;
                nbt.putString( "lastTypeEnemyHitten", target.getType().toString() );
            }

            nbt.putInt( "consecutivelyHits", consecutivelyHits);
        }

        CardAttackActivate(player ,target ,cards);
        return ((damage + flatDamageBuff) * multiplier);
    }


    static public float ApplyDefenseMultiplier (float damage, PlayerEntity player, DamageSource source){
        Map<String, Integer> cards = HowManyCards(player);
        Entity attacker = source.getTrueSource();
        boolean reduceToMin = false;
        float multiplier = 1;

        if(source.isExplosion()){
            if(cards.containsKey("creeper")) {
                if (passCheck(cards.get("creeper").intValue(), 10f )) {
                    CompoundNBT nbt = player.getPersistentData();
                    Long currentTime = player.world.getGameTime();

                    //30s cooldown
                    if(!(nbt.contains("creeperCardActivate")) || 5000 > currentTime - nbt.getLong("creeperCardActivate")){
                        reduceToMin = true;
                        nbt.putLong("creeperCardActivate",currentTime);
                        System.out.println(nbt.getLong("creeperCardActivate"));
                    }
                }
            }
        }

        if(reduceToMin){
            damage = 1;
        }else{
            damage *= multiplier;
        }
        return damage;
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
}
