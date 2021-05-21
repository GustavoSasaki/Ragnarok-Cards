package ragnarok_cards.Items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

import static ragnarok_cards.RagnarokCards.MOD_ID;

public class BagItemHandler extends ItemStackHandler {
    private ItemStack itemStack;
    private boolean change;
    public BagItemHandler(ItemStack itemStack) {
        super(18);
        this.itemStack = itemStack;
    }

    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.getItem() instanceof RagnarokCard;
    }

    @Override
    protected void onContentsChanged(int slot) {
        change = true;
        super.onContentsChanged(slot);
    }

    public void load() {
        load(itemStack.getOrCreateTag());
    }





    public void load(@Nonnull CompoundNBT nbt) {
        if (nbt.contains("Inventory")) {
            deserializeNBT(nbt.getCompound("Inventory"));
        }
    }

    public void save() {
        if (change) {
            CompoundNBT nbt = itemStack.getOrCreateTag();
            nbt.put("Inventory", serializeNBT());
            change = false;
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        setSize(18);
        super.deserializeNBT(nbt);

    }
}