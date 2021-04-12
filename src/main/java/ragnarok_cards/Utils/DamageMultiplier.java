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

import static ragnarok_cards.Utils.VerificateCards.*;

public class DamageMultiplier {


    static public float ApplyDamageMultiplier (float damage, DamageSource source, LivingEntity target, Map<String, Integer> cards){
        PlayerEntity player = (PlayerEntity) source.getTrueSource();
        float multiplier = 1;
        float flatDamageBuff = 0;

        if(cards.containsKey("zombie") && target.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
            multiplier *= 1 + 0.2 * cards.get("zombie");
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
            multiplier *= 1 + 0.25 * cards.get("pig");
        }

        if(cards.containsKey("whiter_skeleton") && target.isPotionActive(Effects.WITHER) && !source.isProjectile()) {
            multiplier *= 1 + 0.5 * cards.get("whiter_skeleton");
        }

        if(source.isProjectile() && cards.containsKey("witch")) {
            double witch_multiplier = 1 + 0.2 * cards.get("witch");
            multiplier *= Math.pow(witch_multiplier, target.getActivePotionEffects().size());
        }

        if(cards.containsKey("zombie_piglin")) {
            if (passCheck(cards.get("zombie_piglin").intValue(), cardNameToChance.get("zombie_piglin") )) {
                multiplier *= 3;
                player.onCriticalHit(target);
                player.onCriticalHit(target);
                player.onCriticalHit(target);
                player.onCriticalHit(target);
                player.onCriticalHit(target);
            }
        }

        if(cards.containsKey("piglin")) {
            Item currItem = player.inventory.getCurrentItem().getItem();

            if(currItem instanceof TieredItem && ((TieredItem)currItem).getTier().getAttackDamage() < 2){
                flatDamageBuff += (int) (1.5 * cards.get("piglin"));
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
            System.out.println(multiplier);
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

                multiplier += 0.02 * consecutivelyHits;
            }else{
                consecutivelyHits = 1;
                nbt.putString( "lastTypeEnemyHitten", target.getType().toString() );
            }

            nbt.putInt( "consecutivelyHits", consecutivelyHits);
            System.out.println(multiplier);
        }

        return multiplier;
    }
}
