package ragnarok_cards.Items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.PacketDistributor;
import ragnarok_cards.Network.ModNetwork;
import ragnarok_cards.Network.SyncBagId;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.RegisterItems.cardNames;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;

public class BagItem extends Item {
    public static int size = 18;

    public BagItem() {
        super(new Item.Properties().group(ItemGroup.COMBAT).maxStackSize(1));
    }


    public int GetCurrentSlot(PlayerEntity player){
        if(player.getHeldItemMainhand().getItem() instanceof BagItem){
            for(int i=0;i<9;i++){
                if(player.inventory.getStackInSlot(i) == player.getHeldItemMainhand()){
                    return i;
                }
            }
        }

        //current slot is offhand
        return -2;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        if(handIn == Hand.OFF_HAND){
            return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
        }


        long bag_id = worldIn.rand.nextLong();
        int bag_slot = GetCurrentSlot(playerIn);

        //itemStack Nbt is always synced between client-server
        CompoundNBT bag_nbt = playerIn.getHeldItemMainhand().getOrCreateChildTag(MOD_ID);
        bag_nbt.putLong("bag_id", bag_id);
        bag_nbt.putInt("bag_slot", bag_slot);

        if (!worldIn.isRemote) {

            getNbtSafe(playerIn.getPersistentData(),MOD_ID).putInt("bag_slot", bag_slot);

            //setting nbt in the servers
            CompoundNBT persistent_nbt = getNbtSafe(playerIn.getPersistentData(),PlayerEntity.PERSISTED_NBT_TAG);

            CompoundNBT player_nbt = getNbtSafe(persistent_nbt,MOD_ID);
            player_nbt.putLong("bag_id", bag_id);

            //sending package from server to client
            Supplier<ServerPlayerEntity> supplier = () -> (ServerPlayerEntity) playerIn;
            ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(supplier), new SyncBagId(bag_id));

            System.out.println( "---------------------");
            for( String name : cardNames){
                if(bag_nbt.contains(name)){
                    System.out.println( name + ": "+Integer.toString(bag_nbt.getInt(name)));
                }
            }
            System.out.println( "---------------------");

            playerIn.openContainer(new INamedContainerProvider() {
                @Nullable
                @Override
                public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
                    return new BagContainer(p_createMenu_1_, p_createMenu_2_, p_createMenu_3_);
                }

                @Override
                public ITextComponent getDisplayName() {
                    return playerIn.getHeldItem(handIn).getDisplayName();

                }
            });
        }
        return ActionResult.resultSuccess(playerIn.getHeldItem(handIn));
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        return new BagCapability(stack, nbt);
    }


}
