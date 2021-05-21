package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.PacketDistributor;
import ragnarok_cards.Network.ModNetwork;
import ragnarok_cards.Network.SyncBagId;

import java.util.function.Supplier;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;

@Mod.EventBusSubscriber()
public class Respawn {
    @SubscribeEvent
    public static void ClonePersistentNbtClient(PlayerEvent.PlayerRespawnEvent event) {

        System.out.println(event.getPlayer().world.isRemote);
        System.out.println(event.getPlayer().world.isRemote);
        System.out.println(event.getPlayer().world.isRemote);
        //setting nbt in the servers
        CompoundNBT persistent_nbt = getNbtSafe(event.getPlayer().getPersistentData(),PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT player_nbt = getNbtSafe(persistent_nbt,MOD_ID);
        long bag_id = player_nbt.getLong("bag_id");


        //sending package from server to client
        Supplier<ServerPlayerEntity> supplier = () -> (ServerPlayerEntity) event.getPlayer();
        ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(supplier), new SyncBagId(bag_id));

    }
}
