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
        if(!(event.getItemStack().getItem() instanceof BagItem)){
            return;
        }


        CompoundNBT persistent_nbt = getNbtSafe(event.getPlayer().getPersistentData(),PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT player_nbt = getNbtSafe(persistent_nbt,MOD_ID);

        CompoundNBT bag_nbt = event.getItemStack().getOrCreateChildTag(MOD_ID);

        event.getToolTip().add(new TranslationTextComponent(Boolean.toString(player_nbt.contains("bag_id"))).mergeStyle(TextFormatting.YELLOW));
        event.getToolTip().add(new TranslationTextComponent(Boolean.toString(bag_nbt.contains("bag_id"))).mergeStyle(TextFormatting.YELLOW));

        if(!(player_nbt.contains("bag_id") && bag_nbt.contains("bag_id")) ){
            return;
        }

        event.getToolTip().add(new TranslationTextComponent(Long.toString(player_nbt.getLong("bag_id"))).mergeStyle(TextFormatting.YELLOW));
        event.getToolTip().add(new TranslationTextComponent(Long.toString(bag_nbt.getLong("bag_id"))).mergeStyle(TextFormatting.YELLOW));
        if(player_nbt.getLong("bag_id") == bag_nbt.getLong("bag_id")){
            event.getToolTip().add(new TranslationTextComponent("Active").mergeStyle(TextFormatting.YELLOW));
            event.getToolTip().add(new TranslationTextComponent("Put Ragnarok Cards inside in different slots to get the effects").mergeStyle(TextFormatting.GRAY));
        }

    }
}
