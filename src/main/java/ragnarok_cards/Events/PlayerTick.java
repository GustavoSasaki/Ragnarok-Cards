package ragnarok_cards.Events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.Explosion;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;

@Mod.EventBusSubscriber()
public class PlayerTick {

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent event) {
        if(event.player.world.isRemote() || event.player.getShouldBeDead()){
            return;
        }

        CompoundNBT nbt_persistant = getNbtSafe(event.player.getPersistentData(),event.player.PERSISTED_NBT_TAG);
        CompoundNBT nbt = getNbtSafe(nbt_persistant,MOD_ID);

        if(nbt.contains("creeperCardPlayerExplode") && nbt.getBoolean("creeperCardPlayerExplode")) {
            Long currentTime = event.player.world.getGameTime();
            if(10 < currentTime - nbt.getLong("creeperCardActivate")){
                double posX = event.player.getPosX();
                double posY = event.player.getPosY();
                double posZ = event.player.getPosZ();

                event.player.world.createExplosion((LivingEntity)null, posX, posY + 1, posZ, 5f, Explosion.Mode.DESTROY);

            }
        }

    }
}
