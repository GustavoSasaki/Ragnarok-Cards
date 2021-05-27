package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import ragnarok_cards.Network.ModNetwork;
import ragnarok_cards.Network.SyncBagId;

import java.util.function.Supplier;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;

@Mod.EventBusSubscriber()
public class SyncBagIdNbt {
    @SubscribeEvent
    public static void LogIn(PlayerEvent.PlayerLoggedInEvent event) {
        SyncClientBagId(event.getPlayer());
    }

    @SubscribeEvent
    public static void ChangeDimension(PlayerEvent.PlayerChangedDimensionEvent event) {
        SyncClientBagId(event.getPlayer());
    }

    @SubscribeEvent
    public static void RespawnEvent(PlayerEvent.PlayerRespawnEvent event) {
        SyncClientBagId(event.getPlayer());
    }


    public static void SyncClientBagId(PlayerEntity player){
        CompoundNBT persistent_nbt = getNbtSafe(player.getPersistentData(), PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT player_nbt = getNbtSafe(persistent_nbt,MOD_ID);
        long bag_id = player_nbt.getLong("bag_id");


        //sending package from server to client
        Supplier<ServerPlayerEntity> supplier = () -> (ServerPlayerEntity) player;
        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(supplier), new SyncBagId(bag_id));
    }
}


