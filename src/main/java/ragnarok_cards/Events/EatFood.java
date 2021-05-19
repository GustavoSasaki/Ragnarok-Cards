package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ragnarok_cards.Config.*;
import static ragnarok_cards.Utils.IDtoEffect.toEffect;
import static ragnarok_cards.Utils.VerificateCards.SingleCardActivate;

@Mod.EventBusSubscriber()
public class EatFood {

    @SubscribeEvent
    public static void finishEat(LivingEntityUseItemEvent.Finish event) {

        if(event.getEntity().world.isRemote() || !(event.getEntity() instanceof PlayerEntity)){
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntity();
        Item item = event.getItem().getItem();

        if(item.isFood() && item.getFood().isMeat() ){

            //when pass, give negative effect
            if(SingleCardActivate (player, "sheep", SHEEP_CHANCE_NEG.get())){
                for(int i=0;i<SHEEP_EFFECT_NEG.get().size();i++){
                    int power = SHEEP_EFFECT_POWER_NEG.get().get(i);
                    int time = SHEEP_EFFECT_TIME_NEG.get().get(i);
                    int id = SHEEP_EFFECT_NEG.get().get(i);
                    player.addPotionEffect(toEffect.get(id).apply(time,power));
                }
                ((ServerWorld) player.world).playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.AMBIENT_CAVE, player.getSoundCategory(), 1.0F, 1.0F);

            }else {
                int randomEffects = player.world.rand.nextInt(SHEEP_EFFECT.get().size());
                int power = SHEEP_EFFECT_POWER.get().get(randomEffects);
                int time = SHEEP_EFFECT_TIME.get().get(randomEffects);
                int id = SHEEP_EFFECT.get().get(randomEffects);
                player.addPotionEffect(toEffect.get(id).apply(time,power));
            }
        }
    }

}
