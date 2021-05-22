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
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import ragnarok_cards.Items.RagnarokCard;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.DamageMultiplier.ApplyDamageMultiplier;
import static ragnarok_cards.Utils.DefenseMultiplier.ApplyDefenseMultiplier;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;
import static ragnarok_cards.Utils.SecundaryEffectsAttack.ApplyAttackSecundayEffects;
import static ragnarok_cards.Utils.onKill.ApplyOnKillEffects;

public class VerificateCards {
    static public final Random rand = new Random();

    static public boolean passCheck(int quantity, double checkMultiplier){
        return (rand.nextInt(100 ) < quantity * checkMultiplier );
    }

    static public boolean SingleCardActivate (PlayerEntity player,String cardName,int multiplier ){
        return passCheck( HowManyCards(player,cardName), multiplier);
    }

    static public int HowManyCards(PlayerEntity player, String cardName){
        CompoundNBT cards = HowManyCards(player);
        return cards.contains(cardName) ? cards.getInt(cardName) : 0;
    }

    static public CompoundNBT HowManyCards(PlayerEntity player){

        CompoundNBT player_not_persist_nbt =getNbtSafe(player.getPersistentData(),MOD_ID);
        if(! player_not_persist_nbt.contains("bag_slot")){
            return null;
        }

        int bag_slot = player_not_persist_nbt.getInt("bag_slot");

        CompoundNBT player_persist_nbt = getNbtSafe(player.getPersistentData(),PlayerEntity.PERSISTED_NBT_TAG);
        player_persist_nbt = getNbtSafe(player_persist_nbt,MOD_ID);
        long player_bag_id = player_persist_nbt.getLong("bag_id");



        if( IsBagActive(player.inventory.getStackInSlot( bag_slot),player_bag_id)){
            return player.inventory.getStackInSlot( bag_slot).getOrCreateChildTag(MOD_ID);
        }else{
            int slot = 0;
            for(ItemStack stack : player.inventory.mainInventory){
                if( IsBagActive(stack, player_bag_id) ){
                    player_not_persist_nbt.putInt("bag_slot",slot);
                    stack.getChildTag(MOD_ID).putInt("bag_slot",slot);
                    return stack.getChildTag(MOD_ID);
                }
                slot++;
            }

        }

        return null;
    }

    static private boolean IsBagActive(ItemStack stack, long player_bag_id){
        CompoundNBT bag_nbt = stack.getChildTag(MOD_ID);

        return bag_nbt != null && bag_nbt.contains("bag_id") && bag_nbt.getLong("bag_id") == player_bag_id;
    }



    static public float ApplyAttackCards(float damage, DamageSource source, LivingEntity target){
        PlayerEntity player = (PlayerEntity) source.getTrueSource();
        CompoundNBT cards = HowManyCards(player);
        if(cards == null){
            return damage;
        }

        ApplyAttackSecundayEffects (source,target, cards);

        damage = ApplyDamageMultiplier (damage, source, target,cards);

        //if(damage >= target.getHealth()){
        //    ApplyOnKillEffects(source,target, cards);
        //}
        return damage;
    }

    static public float ApplyDefenseCards(float damage, PlayerEntity player, DamageSource source){
        CompoundNBT cards = HowManyCards(player);
        if(cards == null){
            return damage;
        }


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
