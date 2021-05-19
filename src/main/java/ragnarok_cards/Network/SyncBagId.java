package ragnarok_cards.Network;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;

public class SyncBagId {
    public long bag_id;

    public SyncBagId() {
    }

    public SyncBagId(long bag_id) {

        this.bag_id = bag_id;
    }

    public static void encode(SyncBagId message, PacketBuffer buffer) {

        buffer.writeLong(message.bag_id);
    }

    public static SyncBagId decode(PacketBuffer buffer) {

        return new SyncBagId(buffer.readLong() );
    }

    public static void handle(SyncBagId message, Supplier<NetworkEvent.Context> contextSupplier) {
        PlayerEntity player = Minecraft.getInstance().player;
        CompoundNBT persistent_nbt = getNbtSafe(player.getPersistentData(),PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT player_nbt = getNbtSafe(persistent_nbt,MOD_ID);

        player_nbt.putLong("bag_id", message.bag_id);
        contextSupplier.get().setPacketHandled(true);
    }
}
