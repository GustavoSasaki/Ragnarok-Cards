package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ragnarok_cards.Items.RagnarokCard;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PickUp {
    @SubscribeEvent
    public static void PickUpItem(EntityItemPickupEvent event) {
        if(event.getEntity().world.isRemote() || !(event.getItem().getItem().getItem() instanceof RagnarokCard)){
            return;
        }

        //set up a nbt key if player got a new card
        String nbtKey = event.getItem().getItem().getItem().getRegistryName().toString()+"seen";
        CompoundNBT nbt = event.getPlayer().getPersistentData();

        if(!nbt.contains(nbtKey)){
            nbt.putBoolean(nbtKey,true);
        }
    }
}
