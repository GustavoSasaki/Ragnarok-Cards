package ragnarok_cards.Utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Map;


import static ragnarok_cards.Config.*;
import static ragnarok_cards.Utils.IDtoEffect.toEffect;
import static ragnarok_cards.Utils.VerificateCards.HowManyCards;
import static ragnarok_cards.Utils.VerificateCards.passCheck;

public class SecundaryEffectsAttack {

    static public void ApplyAttackSecundayEffects (DamageSource source, LivingEntity target, CompoundNBT cards){
        PlayerEntity player = (PlayerEntity) source.getTrueSource();

        if(!source.isProjectile() && cards.contains("cave_spider")) {
            if (passCheck(cards.getInt("cave_spider"), CAVE_SPIDER_CHANCE.get())) {
                int curSlowTime = 0;
                if( target.isPotionActive(Effects.POISON)){
                    curSlowTime = target.getActivePotionEffect(Effects.POISON).getDuration();
                }

                if( !(CAVE_SPIDER_STACK.get() && target.isPotionActive(Effects.POISON) )) {

                    target.addPotionEffect(new EffectInstance(Effects.POISON, 20 * CAVE_SPIDER_POISON_TIME.get() + curSlowTime));
                    ((ServerWorld) player.world).playSound((PlayerEntity) null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_SPIDER_AMBIENT, player.getSoundCategory(), 1.0F, 1.0F);
                }
            }

        }

        if(!source.isProjectile() && cards.contains("whiter_skeleton")) {

            if (passCheck( cards.getInt("whiter_skeleton"), WITHER_SKELETON_CHANCE.get())) {

                ((ServerWorld) player.world).playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_WITHER_AMBIENT, player.getSoundCategory(), 1.0F, 1.0F);
                target.addPotionEffect(new EffectInstance(Effects.WITHER, WITHER_SKELETON_TIME.get()));
            }
        }

        if(!source.isProjectile() && cards.contains("witch")) {
            if (passCheck(cards.getInt("witch"),WITCH_CHANCE_NEG.get())) {

                applyRandomEffect(player);
                applyRandomEffect(target);
                ((ServerWorld) player.world).playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_WITCH_CELEBRATE, player.getSoundCategory(), 1.0F, 1.0F);
            }
        }

        if(cards.contains("blaze")) {

            if(target.getFireTimer() == -1){
                if (passCheck(cards.getInt("blaze"),BLAZE_CHANCE1.get())){
                    target.setFire( BLAZE_TIME1.get() );
                }
            }else {
                if (passCheck(cards.getInt("blaze"),BLAZE_CHANCE2.get())){

                    target.setFire(target.getFireTimer() + BLAZE_TIME2.get() );
                    ((ServerWorld) player.world).playSound((PlayerEntity) null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_BLAZE_AMBIENT, player.getSoundCategory(), 1.0F, 1.0F);
                }
            }
        }

    }

    private static void applyRandomEffect(LivingEntity entity){
        int randomEffects = entity.world.rand.nextInt(WITCH_POWER_NEG.get().size());
        int power = WITCH_POWER_NEG.get().get(randomEffects);
        int time = WITCH_EFFECT_NEG.get().get(randomEffects);
        int id = WITCH_EFFECT_NEG.get().get(randomEffects);

        entity.addPotionEffect(toEffect.get(id).apply(time,power));
    }

}
