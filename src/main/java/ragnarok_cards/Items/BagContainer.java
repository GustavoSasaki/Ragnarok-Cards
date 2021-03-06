package ragnarok_cards.Items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.items.CapabilityItemHandler;

import static ragnarok_cards.RagnarokCards.MOD_ID;

public class BagContainer extends Container {
    public static final ContainerType type = new ContainerType<>(BagContainer::new).setRegistryName("ragnarok_bag_container");
    private PlayerInventory playerInv;
    public BagItemHandler handler;
    public ItemStack stack;
    public int slotContainer = -2;


    public BagContainer(final int windowId, final PlayerInventory playerInventory) {
        this(windowId, playerInventory, playerInventory.player);
    }

    public BagContainer(int windowId, PlayerInventory playerInventory, PlayerEntity player) {
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
        if(slot - 36 == slotContainer ){
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
                this.addSlot(new Slot(playerInventory, index, 8 + x * 18, 50 + y * 18));
            }
        }

        for (int x = 0; x < 9; x++) {
            this.addSlot(new Slot(playerInventory, x, 8 + x * 18, 108));
        }
    }

    private void addBagInventory(ItemStack stack) {
        if (handler == null) {
            return;
        }

        int maxY = BagItem.size / 9;
        int curIndex = 0;

        //todo change xPos e Ypos
        for (int y = 0; y < maxY; y++) {
            for (int x = 0; x < 9; x++) {
                this.addSlot(new BagSlot(handler, curIndex, 8 + x*18, (y+1)*18));

                curIndex++;
                if (curIndex >= BagItem.size)
                    break;
            }
        }
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {

        //reset nbt
        long id = stack.getChildTag(MOD_ID).getLong("bag_id");
        stack.removeChildTag(MOD_ID);
        CompoundNBT nbt = stack.getOrCreateChildTag(MOD_ID);
        nbt.putLong("bag_id",id);

        //add all cards inside the bag
        for(int i=0;i<BagItem.size;i++){
            ItemStack card = this.inventorySlots.get(i).getStack();
            if(!card.isEmpty()) {
                String card_name = ((RagnarokCard) card.getItem()).cardName;

                if(nbt.contains(card_name)){
                    nbt.putInt(card_name, nbt.getInt(card_name) + 1);
                }else{
                    nbt.putInt(card_name, 1);
                }
            }
        }

        super.onContainerClosed(playerIn);
    }

}

