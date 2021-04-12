package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ragnarok_cards.Utils.VerificateCards.HowManyCards;
import static ragnarok_cards.Utils.VerificateCards.SingleCardActivate;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KnockBack {


    @SubscribeEvent
    public static void finishEat(LivingKnockBackEvent event) {
        if(event.getEntity().world.isRemote() || !(event.getEntity() instanceof PlayerEntity)){
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntity();

        if(player.isInWater()) {
            event.setStrength(event.getStrength() + (0.3f * HowManyCards(player, "blaze")));
        }
    }
}



