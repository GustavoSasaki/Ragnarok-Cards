package ragnarok_cards.Utils;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FlyingEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.TieredItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;

import java.util.Map;

import static ragnarok_cards.Config.*;
import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;
import static ragnarok_cards.Utils.VerificateCards.*;

public class DamageMultiplier {


    static public float ApplyDamageMultiplier (float damage, DamageSource source, LivingEntity target, CompoundNBT cards){
        PlayerEntity player = (PlayerEntity) source.getTrueSource();
        float multiplier = 1;
        float flatDamageBuff = 0;

        if(cards.contains("zombie") && target.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
            multiplier *= 1 + cards.getInt("zombie") * ZOMBIE_MULTIPLIER.get();
        }

        if(cards.contains("sheep") && target instanceof AnimalEntity) {
            multiplier *= 1 + cards.getInt("sheep") * SHEEP_MULTIPLIER.get();
        }

        if(cards.contains("spider") && target.getCreatureAttribute() == CreatureAttribute.ARTHROPOD) {
            multiplier *= 1 + cards.getInt("spider") * SPIDER_DAMAGE.get();

            target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, SPIDER_TIME.get(),SPIDER_POWER.get()));
        }

        if(cards.contains("phantom") && isFlyingThing(target)) {
            multiplier *= 1 +  cards.getInt("phantom") * PHANTOM_MULTIPLIER.get();
        }

        if(cards.contains("pig") && ( target instanceof PigEntity || target instanceof AbstractPiglinEntity )) {
            multiplier *= 1 + cards.getInt("pig") * PIG_MULTIPLIER_NEG.get();
        }

        if(cards.contains("wither_skeleton") && target.isPotionActive(Effects.WITHER) && !source.isProjectile()) {
            multiplier *= 1 + cards.getInt("wither_skeleton") * WITHER_SKELETON_MULTIPLIER.get();
        }

        if(source.isProjectile() == false && cards.contains("witch")) {
            double witch_multiplier = 1 + cards.getInt("witch") * WITCH_MULTIPLIER.get();
            multiplier *= Math.pow(witch_multiplier, target.getActivePotionEffects().size());
        }

        if(cards.contains("pillager")) {
            if(source.isProjectile()  ) {
                multiplier *= 1 + cards.getInt("pillager") * PILLAGER_MULTIPLIER.get();
            }else{
                flatDamageBuff -= cards.getInt("pillager") * PILLAGER_DAMAGE_NEG.get();
            }
        }

        if(cards.contains("zombie_piglin")) {
            if (passCheck(cards.getInt("zombie_piglin"), ZOMBIE_PIGLIN_CHANCE.get() )) {
                multiplier *= ZOMBIE_PIGLIN_MULTIPLIER.get();
                player.onCriticalHit(target);
                player.onCriticalHit(target);
                player.onCriticalHit(target);
                player.onCriticalHit(target);
                player.onCriticalHit(target);
            }
        }

        if(cards.contains("piglin")) {
            Item currItem = player.inventory.getCurrentItem().getItem();

            if(currItem instanceof TieredItem && ((TieredItem)currItem).getTier().getAttackDamage() < PIGLIN_MAX_TIER.get()){
                flatDamageBuff += (int) (PIGLIN_DAMAGE.get() * cards.getInt("piglin"));
            }
        }


        if(cards.contains("vindicator") && target.getCreatureAttribute() == CreatureAttribute.ARTHROPOD) {
            multiplier *= Math.min(0.1, 1 - 0.30 * cards.getInt("vindicator"));
        }


        multiplier = ApplySkeletonCard(player,target,multiplier,cards);

        if(damage != 0){
            damage = ((damage + flatDamageBuff) * multiplier);
            damage = damage > 0 ? damage : 1;
        }else{
            damage = ((damage + flatDamageBuff) * multiplier);
        }
        return damage;
    }


    // bonus attack when player consecutively hitting same type of enemy
    private static float ApplySkeletonCard(PlayerEntity player,LivingEntity target, float multiplier,CompoundNBT cards){
        if(cards.contains("skeleton")) {

            CompoundNBT persistant_nbt = getNbtSafe(player.getPersistentData(),player.PERSISTED_NBT_TAG);
            CompoundNBT nbt = getNbtSafe(persistant_nbt,MOD_ID);

            if (!nbt.contains("lastTypeEnemyHitten")){
                nbt.putString("lastTypeEnemyHitten","none");
                nbt.putInt("consecutivelyHits",0);
            }

            String lastTypeEnemyHitten = nbt.getString("lastTypeEnemyHitten");
            int consecutivelyHits;

            if(lastTypeEnemyHitten.equals( target.getType().toString() ) ){
                consecutivelyHits = nbt.getInt("consecutivelyHits");
                consecutivelyHits += cards.getInt("skeleton");

                multiplier += consecutivelyHits * SKELETON_MULTIPLIER.get();
            }else{
                consecutivelyHits = 1;
                nbt.putString( "lastTypeEnemyHitten", target.getType().toString() );
            }

            nbt.putInt( "consecutivelyHits", consecutivelyHits);
        }

        return multiplier;
    }
}
