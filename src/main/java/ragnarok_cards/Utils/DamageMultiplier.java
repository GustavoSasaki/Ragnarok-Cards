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


    static public float ApplyDamageMultiplier (float damage, DamageSource source, LivingEntity target, Map<String, Integer> cards){
        PlayerEntity player = (PlayerEntity) source.getTrueSource();
        float multiplier = 1;
        float flatDamageBuff = 0;

        if(cards.containsKey("zombie") && target.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
            multiplier *= 1 + cards.get("zombie") * ZOMBIE_MULTIPLIER.get();
        }

        if(cards.containsKey("sheep") && target instanceof AnimalEntity) {
            multiplier *= 1 + 0.25 * cards.get("sheep");
        }

        if(cards.containsKey("spider") && target.getCreatureAttribute() == CreatureAttribute.ARTHROPOD) {
            multiplier *= 1 + 0.25 * cards.get("spider");

            //add slow for 1.5s
            target.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 250,4));
        }

        if(cards.containsKey("phantom") && isFlyingThing(target)) {
            multiplier *= 1 + 0.25 * cards.get("phantom");
        }

        if(cards.containsKey("pig") && ( target instanceof PigEntity || target instanceof AbstractPiglinEntity )) {
            multiplier *= 1 + cards.get("pig") * PIG_MULTIPLIER_NEG.get();
        }

        if(cards.containsKey("whiter_skeleton") && target.isPotionActive(Effects.WITHER) && !source.isProjectile()) {
            multiplier *= 1 + cards.get("whiter_skeleton") * WITHER_SKELETON_MULTIPLIER.get();
        }

        if(source.isProjectile() && cards.containsKey("witch")) {
            double witch_multiplier = 1 + 0.2 * cards.get("witch");
            multiplier *= Math.pow(witch_multiplier, target.getActivePotionEffects().size());
        }

        if(cards.containsKey("zombie_piglin")) {
            if (passCheck(cards.get("zombie_piglin").intValue(), ZOMBIE_PIGLIN_CHANCE.get() )) {
                multiplier *= ZOMBIE_PIGLIN_MULTIPLIER.get();
                player.onCriticalHit(target);
                player.onCriticalHit(target);
                player.onCriticalHit(target);
                player.onCriticalHit(target);
                player.onCriticalHit(target);
            }
        }

        if(cards.containsKey("piglin")) {
            Item currItem = player.inventory.getCurrentItem().getItem();

            if(currItem instanceof TieredItem && ((TieredItem)currItem).getTier().getAttackDamage() < PIGLIN_MAX_TIER.get()){
                flatDamageBuff += (int) (PIGLIN_DAMAGE.get() * cards.get("piglin"));
            }
        }


        if(cards.containsKey("vindicator") && target.getCreatureAttribute() == CreatureAttribute.ARTHROPOD) {
            multiplier *= Math.min(0.1, 1 - 0.30 * cards.get("vindicator"));
        }


        multiplier = ApplySkeletonCard(player,target,multiplier,cards);

        return ((damage + flatDamageBuff) * multiplier);
    }


    // bonus attack when player consecutively hitting same type of enemy
    private static float ApplySkeletonCard(PlayerEntity player,LivingEntity target, float multiplier,Map<String, Integer> cards){
        if(cards.containsKey("skeleton")) {

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
                consecutivelyHits += cards.get("skeleton");

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
