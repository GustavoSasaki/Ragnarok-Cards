package ragnarok_cards.Items;

import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

public class BagSlot extends SlotItemHandler {
        public BagSlot(IItemHandler itemHandler, int index, int xPosition, int yPosition) {
            super(itemHandler, index, xPosition, yPosition);
        }

        @Override
        public int getItemStackLimit(@Nonnull ItemStack stack) {
            return super.getSlotStackLimit();
        }

        @Override
        public boolean isItemValid(@Nonnull ItemStack stack) {
            return stack.getItem() instanceof RagnarokCard;
        }

        @Override
        public void onSlotChanged() {
            super.onSlotChanged();
        }

}
