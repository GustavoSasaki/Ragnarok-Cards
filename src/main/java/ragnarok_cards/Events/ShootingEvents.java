package ragnarok_cards.Events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.EggEntity;
import net.minecraft.entity.projectile.SnowballEntity;
import net.minecraft.entity.projectile.ThrowableEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ragnarok_cards.Events.VerificateCards.*;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ShootingEvents {

    @SubscribeEvent
    public static void pickupItem(EntityItemPickupEvent event) {
        System.out.println("Item picked up!");
    }

    @SubscribeEvent
    public static void cardSnowMan(ProjectileImpactEvent.Throwable event) {
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
            //3seconds of slow
            ((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 500));
        }

    }
}