package ragnarok_cards.Items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class BagContainer extends Container {
    public static final ContainerType type = new ContainerType<>(BagContainer::new).setRegistryName("ragnarok_bag_container");
    private PlayerInventory playerInv;
    public BagItemHandler handler;
    public ItemStack stack;


    public BagContainer(final int windowId, final PlayerInventory playerInventory) {
        this(windowId, playerInventory, playerInventory.player);
    }

    public BagContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        super(type, windowId);

        playerInv = playerInventory;
        stack = player.getHeldItemMainhand().getItem() instanceof BagItem
                ? player.getHeldItemMainhand()
                : player.getHeldItemOffhand();

        handler = (BagItemHandler) stack.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        handler.load();

        addBagInventory(stack);
        addPlayerInventory(playerInv);

    }



    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack()) {
            int bagslotcount = inventorySlots.size() - playerIn.inventory.mainInventory.size();
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (index < bagslotcount) {
                if (!this.mergeItemStack(itemstack1, bagslotcount, this.inventorySlots.size(), true))
                    return ItemStack.EMPTY;
            } else if (!this.mergeItemStack(itemstack1, 0, bagslotcount, false)) {
                return ItemStack.EMPTY;
            }
            if (itemstack1.isEmpty()) slot.putStack(ItemStack.EMPTY); else slot.onSlotChanged();
        }
        return itemstack;
    }


    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }

    @Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, PlayerEntity player) {
        if(slot <= 0 || stack == null || getSlot(slot).getStack() == stack){
            return ItemStack.EMPTY;
        }

        if (clickTypeIn == ClickType.SWAP) return ItemStack.EMPTY;
        if (slot >= 0) getSlot(slot).inventory.markDirty();

        return super.slotClick(slot, dragType, clickTypeIn, player);
    }

    private void addPlayerInventory(PlayerInventory playerInventory) {
        int originX = 7;
        int originY = 67;

        //Player Inventory
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int x = originX + col * 18;
                int y = originY + row * 18;
                int index = (col + row * 9) + 9;
                this.addSlot(new Slot(playerInventory, index, x+1, y+1));
            }
        }

        //Hotbar
        for (int col = 0; col < 9; col++) {
            int x = originX + col * 18;
            int y = originY + 58;
            this.addSlot(new Slot(playerInventory, col, x+1, y+1));
        }
    }

    private void addBagInventory(ItemStack stack) {
        if (handler == null) {
            return;
        }

        int maxY = BagItem.size / 9;
        int curIndex = 0;

        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new BagSlot(handler, curIndex, 8 + x*18, (y+1)*18));

                curIndex++;
                if (curIndex >= BagItem.size)
                    break;
            }
        }
    }
}

