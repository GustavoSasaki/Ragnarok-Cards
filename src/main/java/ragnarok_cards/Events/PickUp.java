package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ragnarok_cards.Items.BagItem;
import ragnarok_cards.Items.RagnarokCard;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;

@Mod.EventBusSubscriber()
public class PickUp {
    @SubscribeEvent
    //mark as seen if ragnarok card pick up
    public static void PickUpItem(EntityItemPickupEvent event) {

        PickUpCard(event);
        PickUpBag(event);

    }


    public static void PickUpCard(EntityItemPickupEvent event) {
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

    public static void PickUpBag(EntityItemPickupEvent event) {
        if(!(event.getItem().getItem().getItem() instanceof BagItem)){
            return;
        }

        CompoundNBT persistent_nbt = getNbtSafe(event.getPlayer().getPersistentData(),PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT player_nbt = getNbtSafe(persistent_nbt,MOD_ID);
        if(!player_nbt.contains("bag_id")){
            return;
        }

        for(int slot=0;slot<event.getPlayer().inventory.getSizeInventory();slot++){
            CompoundNBT bag_nbt = event.getPlayer().inventory.getStackInSlot(slot).getOrCreateChildTag(MOD_ID);

            if(bag_nbt.contains("bag_id") && bag_nbt.getLong("bag_id" ) == player_nbt.getLong("bag_id")){
                System.out.println("RECUPEROU :)");
                player_nbt.putInt("bag_slot",slot);
            }
        }

    }


}
