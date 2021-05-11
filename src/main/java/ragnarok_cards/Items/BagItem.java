package ragnarok_cards.Items;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BagItem extends Item {


    public BagItem() {
        super(new Item.Properties().group(ItemGroup.COMBAT));
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if (!worldIn.isRemote) {

                //open
                playerIn.openContainer(new INamedContainerProvider() {
                    @Nullable
                    @Override
                    public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                        return new BagContainer(p_createMenu_1_, p_createMenu_3_.world, p_createMenu_3_.getPosition(), p_createMenu_2_, p_createMenu_3_);
                    }

                    @Override
                    public ITextComponent getDisplayName() {
                        return playerIn.getHeldItem(handIn).getDisplayName();

                    }
                });
        }
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new BagCaps(stack, nbt);
    }


    class BagCaps implements ICapabilitySerializable {
            private int size = 18;
            private ItemStack itemStack;
            private BagItemHandler inventory;
            private LazyOptional<IItemHandler> optional;

            public BagCaps(ItemStack stack,  CompoundNBT nbtIn) {
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
                else
                    return LazyOptional.empty();
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
}
