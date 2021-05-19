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
public class Clone {
    @SubscribeEvent
    public static void ClonePersistentNbtClient(PlayerEvent.Clone event) {

        CompoundNBT old_persistent_nbt = getNbtSafe(event.getOriginal().getPersistentData(), PlayerEntity.PERSISTED_NBT_TAG);
        CompoundNBT old_nbt = getNbtSafe(old_persistent_nbt,MOD_ID);

        System.out.println(event.getPlayer().world.isRemote);
        System.out.println("clone");

        if(old_nbt.contains("bag_id")) {
            System.out.println("tem bag id");
            Long bag_id = old_nbt.getLong("bag_id");

            //sending package from server to client
            Supplier<ServerPlayerEntity> supplier = () -> (ServerPlayerEntity) event.getPlayer();
            ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(supplier), new SyncBagId(bag_id));

            Supplier<ServerPlayerEntity> supplier2 = () -> (ServerPlayerEntity) event.getOriginal();
            ModNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(supplier2), new SyncBagId(bag_id));

            System.out.println(bag_id);
        }
    }
}
