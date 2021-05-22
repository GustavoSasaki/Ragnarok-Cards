package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ragnarok_cards.Config.BLAZE_KNOCKBACK_NEG;
import static ragnarok_cards.Utils.VerificateCards.HowManyCards;

@Mod.EventBusSubscriber()
public class KnockBack {


    @SubscribeEvent
    public static void finishEat(LivingKnockBackEvent event) {
        if(event.getEntity().world.isRemote() || !(event.getEntity() instanceof PlayerEntity)){
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntity();

        if(player.isInWater()) {
            float extraKnockBack = (float) (HowManyCards(player, "blaze") * BLAZE_KNOCKBACK_NEG.get());
            event.setStrength(event.getStrength() * (1+extraKnockBack));
        }
    }
}



