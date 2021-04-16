package ragnarok_cards;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ragnarok_cards.Items.RagnarokCard;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ragnarok_cards.RegisterItems;

import java.util.Arrays;

import static ragnarok_cards.RegisterItems.*;


@Mod(RagnarokCards.MOD_ID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RagnarokCards
{
    public static final String MOD_ID = "ragnarok_cards";

    //registering mob drops
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, RagnarokCards.MOD_ID);

    public RagnarokCards() {

        GLM.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT,Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,Config.SERVER_CONFIG);
    }




    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(CAVE_SPIDER_CARD,CREEPER_CARD,OCELOT_CARD,PHANTOM_CARD,PIG_CARD,PIGLIN_CARD,
                SHEEP_CARD,SKELETON_CARD,SNOWMAN_CARD,SPIDER_CARD,WHITER_SKELETON_CARD,WITCH_CARD,WOLF_CARD,ZOMBIE_CARD,
                ZOMBIE_PIGLIN_CARD,BLAZE_CARD);
    }

}