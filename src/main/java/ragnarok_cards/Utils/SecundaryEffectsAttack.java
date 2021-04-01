package ragnarok_cards.Utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;

import java.util.Map;
import java.util.Random;

import static ragnarok_cards.Utils.VerificateCards.cardNameToChance;
import static ragnarok_cards.Utils.VerificateCards.passCheck;

public class SecundaryEffectsAttack {
    static public final Random rand = new Random();

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
        int randomEffects = rand.nextInt(24);
        if(randomEffects == 0)
            entity.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1000));
        if(randomEffects == 1)
            entity.addPotionEffect(new EffectInstance(Effects.JUMP_BOOST, 1000));
    }
}
