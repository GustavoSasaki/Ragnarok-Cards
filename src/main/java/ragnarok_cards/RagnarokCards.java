package ragnarok_cards;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ragnarok_cards.Items.BagContainer;
import ragnarok_cards.Items.BagItem;
import ragnarok_cards.Items.BagScreen;
import ragnarok_cards.Items.LootBoxRecipe;
import ragnarok_cards.Network.ModNetwork;

import static ragnarok_cards.RegisterItems.ITEMS;


@Mod(RagnarokCards.MOD_ID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RagnarokCards
{
    public static final String MOD_ID = "ragnarok_cards";

    //registering mob drops
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, RagnarokCards.MOD_ID);


    public RagnarokCards() {

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ITEMS.register(bus);
        GLM.register(bus);
        bus.addListener(this::networkSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT,Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,Config.SERVER_CONFIG);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientGUI);
    }


    //register recipes
    public static final SpecialRecipeSerializer<LootBoxRecipe> LOOT_BOX_RECIPE_SERIEALIZER = new SpecialRecipeSerializer<>(LootBoxRecipe::new);
    @SubscribeEvent
    public static void registerRecipeSerializer(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        LOOT_BOX_RECIPE_SERIEALIZER.setRegistryName("ragnarok_cards:loot_box_special_recipe");
        event.getRegistry().register(LOOT_BOX_RECIPE_SERIEALIZER);
    }



    private void clientGUI(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(BagContainer.type, BagScreen::new);
    }

    public static ItemStack findBackpack(PlayerEntity player) {
        if (player.getHeldItemMainhand().getItem() instanceof BagItem)
            return player.getHeldItemMainhand();
        if (player.getHeldItemOffhand().getItem() instanceof BagItem)
            return player.getHeldItemOffhand();

        PlayerInventory inventory = player.inventory;
        for (int i = 0; i <= 35; i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack.getItem() instanceof BagItem)
                return stack;
        }
        return ItemStack.EMPTY;
    }


    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> containerRegistryEvent) {
            containerRegistryEvent.getRegistry().register(BagContainer.type);
        }
    }

    public void networkSetup(final FMLCommonSetupEvent event) {
        ModNetwork.init();
    }

    //todo port mod to all of 1.16 ; 1.15 ; 1.14
    //todo more cards
    //todo jei compatibity

    //todo texture change

    //todo put items when right click
    //todo change bag texture when open

}