package ragnarok_cards.Items;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BagCapability  implements ICapabilitySerializable {
    private ItemStack itemStack;
    private BagItemHandler inventory;
    private LazyOptional<IItemHandler> optional;

    public BagCapability(ItemStack stack,  CompoundNBT nbtIn) {
        itemStack = stack;
        inventory = new BagItemHandler(itemStack);
        optional = LazyOptional.of(() -> inventory);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return optional.cast();
        }
        else {
            return LazyOptional.empty();
        }
    }

    @Override
    public INBT serializeNBT() {
        inventory.save();
        return new CompoundNBT();
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        inventory.load();
    }
}
