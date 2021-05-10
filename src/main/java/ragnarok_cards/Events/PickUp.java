package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ragnarok_cards.Items.RagnarokCard;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;

@Mod.EventBusSubscriber()
public class PickUp {
    @SubscribeEvent
    //mark as seen if ragnarok card pick up
    public static void PickUpItem(EntityItemPickupEvent event) {
        if(event.getEntity().world.isRemote() || !(event.getItem().getItem().getItem() instanceof RagnarokCard)){
            return;
        }

        //set up a nbt key if player got a new card
        String nbtKey = event.getItem().getItem().getItem().getRegistryName().toString()+".seen";

        CompoundNBT persist_nbt = getNbtSafe(event.getPlayer().getPersistentData(),PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT mod_nbt = getNbtSafe(persist_nbt,MOD_ID);

          if(!mod_nbt.contains(nbtKey)){
              mod_nbt.putBoolean(nbtKey,true);
        }
    }
}
