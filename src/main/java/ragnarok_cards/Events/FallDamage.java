package ragnarok_cards.Events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ragnarok_cards.Config.*;
import static ragnarok_cards.Utils.VerificateCards.HowManyCards;
import static ragnarok_cards.Utils.VerificateCards.SingleCardActivate;

@Mod.EventBusSubscriber()
public class FallDamage {

    @SubscribeEvent
    public static void cardSnowMan(LivingFallEvent event) {
        if(event.getEntity().world.isRemote()){
            return;
        }
        if(event.getDistance() < 5){
            return;
        }
        Entity entity = event.getEntity();
        if(!(entity instanceof PlayerEntity)){
            return;
        }
        PlayerEntity player = (PlayerEntity) entity;

        int pigCardDamage = (int) ((event.getDistance()-3) * PIG_MULTIPLIER.get() * HowManyCards(player,"pig"));
        event.setDistance(event.getDistance() + pigCardDamage);


        if(SingleCardActivate(player, "ocelot",OCELOT_CHANCE.get())) {
            event.setDistance( 5 );
            player.addPotionEffect(new EffectInstance(Effects.SPEED, OCELOT_SPEED_TIME.get(),4));
            ((ServerWorld) player.world).playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_CAT_AMBIENT, player.getSoundCategory(), 1.0F, 1.0F);
        }
    }

}
