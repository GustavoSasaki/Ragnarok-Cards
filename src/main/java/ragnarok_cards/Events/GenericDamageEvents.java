package ragnarok_cards.Events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ragnarok_cards.Events.VerificateCards.GetCardDamageMultiplier;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GenericDamageEvents {

    @SubscribeEvent
    public static void cardZombie(LivingHurtEvent event) {
        Entity attacker = event.getSource().getTrueSource();
        if(!(attacker instanceof ServerPlayerEntity)){
            return;
        }

        float multiplier = GetCardDamageMultiplier((PlayerEntity)attacker, (LivingEntity) event.getEntity());

        System.out.println( event.getAmount() );
        event.setAmount( event.getAmount() * multiplier);
        System.out.println( event.getAmount() );
    }
}


