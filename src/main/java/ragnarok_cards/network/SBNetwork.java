package ragnarok_cards.network;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import static ragnarok_cards.RagnarokCards.MOD_ID;

public class SBNetwork {
        public ResourceLocation channelName;
        public String networkVersion;

        private SimpleChannel network;

        public SimpleChannel register() {
            channelName = new ResourceLocation(MOD_ID, "network");
            networkVersion = new ResourceLocation(MOD_ID, "1").toString();

            network = NetworkRegistry.ChannelBuilder.named(channelName)
                    .clientAcceptedVersions(version -> true)
                    .serverAcceptedVersions(version -> true)
                    .networkProtocolVersion(() -> networkVersion)
                    .simpleChannel();

            network.messageBuilder(OpenMessage.class, 1)
                    .decoder(OpenMessage::decode)
                    .encoder(OpenMessage::encode)
                    .consumer(OpenMessage::handle)
                    .add();

            return network;
        }

        public SimpleChannel getNetworkChannel() {
            return network;
        }
    }
