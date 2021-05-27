package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ragnarok_cards.Items.BagItem;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;


@Mod.EventBusSubscriber()
public class ToolTip {

    @SubscribeEvent
    public static void ToolTipEvent(ItemTooltipEvent event) {
        if(!(event.getItemStack().getItem() instanceof BagItem) || event.getPlayer() == null){
            return;
        }


        CompoundNBT persistent_nbt = getNbtSafe(event.getPlayer().getPersistentData(),PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT player_nbt = getNbtSafe(persistent_nbt,MOD_ID);

        CompoundNBT bag_nbt = event.getItemStack().getOrCreateChildTag(MOD_ID);


        if(!(player_nbt.contains("bag_id") && bag_nbt.contains("bag_id")) ){
            return;
        }

        if(player_nbt.getLong("bag_id") == bag_nbt.getLong("bag_id")){
            event.getToolTip().add(new TranslationTextComponent("item.ragnarok_cards.bag.active").mergeStyle(TextFormatting.YELLOW));
            event.getToolTip().add(new TranslationTextComponent("item.ragnarok_cards.bag.active.description").mergeStyle(TextFormatting.GRAY));
        }

    }
}
