package ragnarok_cards.Events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

import static ragnarok_cards.Utils.VerificateCards.*;

@Mod.EventBusSubscriber()
public class ShootingEvents {


    @SubscribeEvent
    public static void ProjectileImpactEvent(ProjectileImpactEvent.Throwable event) {
        if(event.getEntity().world.isRemote()){
            return;
        }

        ThrowableEntity throwable = event.getThrowable();
        if(!(throwable instanceof SnowballEntity)) {
            return;
        }
        //verifying if shooter is a player
        if(throwable.func_234616_v_()  == null || !(throwable.func_234616_v_() instanceof ServerPlayerEntity)){
            return;
        }
        PlayerEntity shooter = (PlayerEntity) throwable.func_234616_v_();

        if(!(event.getRayTraceResult() instanceof EntityRayTraceResult)){
            return;
        }
        Entity target = ((EntityRayTraceResult) event.getRayTraceResult()).getEntity();
        if(!(target instanceof LivingEntity)){
            return;
        }

        if(SingleCardActivate(shooter, "snowman")) {

            int curSlowTime = 0;
            if( ((LivingEntity) target).isPotionActive(Effects.SLOWNESS)){
                curSlowTime = ((LivingEntity) target).getActivePotionEffect(Effects.SLOWNESS).getDuration();
            }

            //3seconds of slow
            ((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 500 + curSlowTime));
        }

    }

    @SubscribeEvent
    public static void cardSnowMan(ProjectileImpactEvent.Arrow event) {

        if(!(event.getRayTraceResult() instanceof EntityRayTraceResult)){
            return;
        }
        Entity target = ((EntityRayTraceResult) event.getRayTraceResult()).getEntity();

        if(!(target instanceof  PlayerEntity)){
            return;
        }

        PlayerEntity player = (PlayerEntity) target;

        if(SingleCardActivate(player, "creeper", 1/5)) {
            player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 30));
            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 300,3));
            player.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 300,2));
        }
    }

}