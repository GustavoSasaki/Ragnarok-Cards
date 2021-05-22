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

import static ragnarok_cards.Config.*;
import static ragnarok_cards.Utils.IDtoEffect.toEffect;
import static ragnarok_cards.Utils.VerificateCards.*;

@Mod.EventBusSubscriber()
public class Shooting {


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

        //if(SingleCardActivate(shooter, "snowman",SNOW_GOLEM_CHANCE.get())) {

        //    int curSlowTime = SNOW_GOLEM_TIME.get();
        //    boolean slowed = ((LivingEntity) target).isPotionActive(Effects.SLOWNESS);
        //    if(slowed){
        //        curSlowTime += ((LivingEntity) target).getActivePotionEffect(Effects.SLOWNESS).getDuration();
        //    }

        //    if(!(SNOW_GOLEM_STACK.get() && slowed)) {
        //        ((LivingEntity) target).addPotionEffect(new EffectInstance(Effects.SLOWNESS, curSlowTime,SNOW_GOLEM_POWER.get()));
        //    }
        //}

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

        if(SingleCardActivate(player, "creeper", CREEPER_CHANCE_NEG.get())) {
            for(int i =0;i<CREEPER_EFFECT_NEG.get().size();i++) {
                int power = CREEPER_EFFECT_POWER_NEG.get().get(i);
                int time = CREEPER_EFFECT_TIME_NEG.get().get(i);
                int id = CREEPER_EFFECT_NEG.get().get(i);
                player.addPotionEffect(toEffect.get(id).apply(time,power));
            }
        }
    }

}