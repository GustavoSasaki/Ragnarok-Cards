package ragnarok_cards.Utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import java.util.ArrayList;
import java.util.Map;


import static ragnarok_cards.Utils.VerificateCards.passCheck;

public class SecundaryEffectsAttack {

    private interface ApplyEffect{
        public EffectInstance apply();
    }
    static protected ArrayList<ApplyEffect> applyWitchRandomEffect = new ArrayList<ApplyEffect>();
    static{
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.SPEED, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.SLOWNESS, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.STRENGTH, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.WEAKNESS, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.REGENERATION, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.RESISTANCE, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.FIRE_RESISTANCE, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.INVISIBILITY, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.POISON, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.GLOWING, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.SLOW_FALLING, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.LEVITATION, 1000));
        applyWitchRandomEffect.add( () -> new EffectInstance(Effects.WITHER, 1000));
    }


    static public void ApplyAttackSecundayEffects (DamageSource source, LivingEntity target, Map<String, Integer> cards){
        PlayerEntity player = (PlayerEntity) source.getTrueSource();

        if(!source.isProjectile() && cards.containsKey("cave_spider")) {
            if (passCheck("cave_spider",cards)) {
                int curSlowTime = 0;
                if( target.isPotionActive(Effects.POISON)){
                    curSlowTime = target.getActivePotionEffect(Effects.POISON).getDuration();
                }
                //6seconds of poison
                target.addPotionEffect(new EffectInstance(Effects.POISON, 1000 + curSlowTime));
                ((ServerWorld) player.world).playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_SPIDER_AMBIENT, player.getSoundCategory(), 1.0F, 1.0F);
            }
        }

        if(!source.isProjectile() && cards.containsKey("whiter_skeleton")) {
            if (passCheck("whiter_skeleton",cards)) {
                //12seconds of poison
                ((ServerWorld) player.world).playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_WITHER_AMBIENT, player.getSoundCategory(), 1.0F, 1.0F);
                target.addPotionEffect(new EffectInstance(Effects.WITHER, 250));
            }
        }

        if(!source.isProjectile() && cards.containsKey("witch")) {
            if (passCheck("witch",cards)) {

                applyRandomEffect(player);
                applyRandomEffect(target);
                ((ServerWorld) player.world).playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_WITCH_CELEBRATE, player.getSoundCategory(), 1.0F, 1.0F);
            }
        }
    }

    private static void applyRandomEffect(LivingEntity entity){
        int randomEffects = entity.world.rand.nextInt(applyWitchRandomEffect.size());
        EffectInstance newEffect =  applyWitchRandomEffect.get(randomEffects).apply();
        entity.addPotionEffect(newEffect);
    }

}
