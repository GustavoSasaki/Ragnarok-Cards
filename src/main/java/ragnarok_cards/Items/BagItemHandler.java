package ragnarok_cards.Items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BagItemHandler extends ItemStackHandler {
    private ItemStack itemStack;

    public BagItemHandler(ItemStack itemStack) {
        super(18);
        this.itemStack = itemStack;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.getItem() instanceof RagnarokCard;
        //can use insertItem to verify every time item go in
        // extractItem make the contrary
        //or can use content change, lol
    }

    public void load() {
        if ( itemStack.getOrCreateTag().contains("Inventory"))
            deserializeNBT( itemStack.getOrCreateTag().getCompound("Inventory"));
    }

    public void save() {
        CompoundNBT nbt = itemStack.getOrCreateTag();
        nbt.put("Inventory", serializeNBT());
    }
}