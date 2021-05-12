package ragnarok_cards.Items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class BagItemHandler extends ItemStackHandler {
    private ItemStack itemStack;
    private boolean dirty;
    private boolean loaded = false;
    public BagItemHandler(ItemStack itemStack) {
        super(18);
        this.itemStack = itemStack;
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        dirty = true;
        return super.insertItem(slot, stack, simulate);
    }
    @Override
    public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
        return stack.getItem() instanceof RagnarokCard;
        //can use insertItem to verify every time item go in
        // extractItem make the contrary
        //or can use content change, lol
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        dirty = true;
        //System.out.print("EXTRACT");
        return super.extractItem(slot, amount, simulate);
    }

    @Override
    public void setStackInSlot(int slot, @Nonnull ItemStack stack) {
        validateSlotIndex(slot);
        if (!ItemStack.areItemStackTagsEqual(stack, stacks.get(slot))) {
            onContentsChanged(slot);
        }
        this.stacks.set(slot, stack);
        //System.out.print("SET");
    }

    public void setDirty() {
        this.dirty = true;
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
        //System.out.print("CHANGE");
        dirty = true;
    }

    public void load() {
        load(itemStack.getOrCreateTag());
    }

    public void loadIfNotLoaded() {
        if (!loaded)
            load();
        loaded = true;
    }

    public void load(@Nonnull CompoundNBT nbt) {
        if (nbt.contains("Inventory")) {
            deserializeNBT(nbt.getCompound("Inventory"));
           // System.out.print("LOAD");
        }
    }

    public void save() {
        if (dirty) {
            CompoundNBT nbt = itemStack.getOrCreateTag();
            nbt.put("Inventory", serializeNBT());
            dirty = false;
            //System.out.print("SAVE");
        }
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        setSize(18);
        System.out.println("DESERIALIZE");
        ListNBT tagList = nbt.getList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < tagList.size(); i++)
        {
            CompoundNBT itemTags = tagList.getCompound(i);
            int slot = itemTags.getInt("Slot");

            if (slot >= 0 && slot < stacks.size())
            {
                stacks.set(slot, ItemStack.read(itemTags));
            }
        }
        onLoad();
    }
}