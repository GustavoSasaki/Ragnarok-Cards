package ragnarok_cards.Events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ragnarok_cards.Events.VerificateCards.SingleCardActivate;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class FallDamageEvent {

    @SubscribeEvent
    public static void cardSnowMan(LivingFallEvent event) {
        Entity entity = event.getEntity();
        if(!(entity instanceof PlayerEntity)){
            return;
        }
        PlayerEntity player = (PlayerEntity) entity;
        if(SingleCardActivate(player, "ocelot")) {
            event.setDistance( Math.min(event.getDistance(),5));
            //12seconds of speed
            player.addPotionEffect(new EffectInstance(Effects.SPEED, 2000));
        }
    }

}
