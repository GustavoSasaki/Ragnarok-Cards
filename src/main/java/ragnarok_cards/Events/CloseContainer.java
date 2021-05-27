package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ragnarok_cards.Items.RagnarokCard;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;

@Mod.EventBusSubscriber()
public class CloseContainer {
    //verifying new Ragnarok Card when container close
    @SubscribeEvent
    public static void ContainerClose(PlayerContainerEvent.Close event) {
        boolean gettingFromCreative = event.getContainer() instanceof PlayerContainer && event.getPlayer().isCreative();

        if(!gettingFromCreative && event.getContainer() instanceof PlayerContainer){
            return;
        }


        int firstInventoryIndex = gettingFromCreative ? 0 : event.getContainer().getInventory().size() - 36;

        CompoundNBT persist_nbt = getNbtSafe(event.getPlayer().getPersistentData(), PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT mod_nbt = getNbtSafe(persist_nbt,MOD_ID);

        for(int i=firstInventoryIndex; i < event.getContainer().getInventory().size();i++){

            Item item = event.getContainer().getInventory().get(i).getItem();

            if(item instanceof RagnarokCard){
                String nbtKey = item.getRegistryName().toString()+".seen";
                if(!mod_nbt.contains(nbtKey)){
                    mod_nbt.putBoolean(nbtKey,true);
                }
            }

        }

    }
}
