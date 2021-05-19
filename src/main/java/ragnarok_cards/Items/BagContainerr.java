package ragnarok_cards.Items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;

public class BagContainerr extends Container {
    public static final ContainerType type = new ContainerType<>(BagContainerr::new).setRegistryName("ragnarok_bag_container");
    private PlayerInventory playerInv;
    public BagItemHandler handler;
    public ItemStack stack;
    public int slotContainer = -2;


    public BagContainerr(final int windowId, final PlayerInventory playerInventory) {
        this(windowId, playerInventory, playerInventory.player);
    }

    public BagContainerr(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
        super(type, windowId);

        playerInv = playerInventory;

        if(player.getHeldItemMainhand().getItem() instanceof BagItem){
            stack = player.getHeldItemMainhand();
            for(int i=0;i<9;i++){
                if(playerInventory.mainInventory.get(i) == stack){
                    slotContainer = i;
                    break;
                }
            }

        }else{
            stack = player.getHeldItemOffhand();
            slotContainer = -3;
        }

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
        if(slot < 0 && clickTypeIn != ClickType.QUICK_CRAFT){
            return ItemStack.EMPTY;
        }
        if(stack == null || slot == -2){
            return ItemStack.EMPTY;
        }
        //the slot from this function and from player intentory hae different orders
        if(slot - 45 == slotContainer ){
            return ItemStack.EMPTY;
        }

        if (clickTypeIn == ClickType.SWAP) {
            return ItemStack.EMPTY;
        }

        if (slot >= 0) getSlot(slot).inventory.markDirty();
        return super.slotClick(slot, dragType, clickTypeIn, player);
    }


    private void addPlayerInventory(PlayerInventory playerInventory) {

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                int index = (x + (y+1) * 9);
                this.addSlot(new Slot(playerInventory, index, 8 + x * 18, 68 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 126));
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

