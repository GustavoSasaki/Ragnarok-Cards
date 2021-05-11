package ragnarok_cards;

import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ragnarok_cards.Items.BagContainer;
import ragnarok_cards.Items.BagGUI;
import ragnarok_cards.Items.LootBoxRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ragnarok_cards.Items.BagItem;
import ragnarok_cards.network.OpenMessage;
import ragnarok_cards.network.SBNetwork;

import static ragnarok_cards.RegisterItems.*;


@Mod(RagnarokCards.MOD_ID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RagnarokCards
{
    public static final String MOD_ID = "ragnarok_cards";
    public static SimpleChannel network;
    public static SBNetwork sbnetwork = new SBNetwork();
    private NonNullList<KeyBinding> keyBinds= NonNullList.create();

    //registering mob drops
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, RagnarokCards.MOD_ID);


    public void setup(final FMLCommonSetupEvent event) {
        network = sbnetwork.register();
    }

    private void onClientTick(TickEvent.ClientTickEvent event) {
        if (keyBinds.get(0).isPressed())
            network.sendToServer(new OpenMessage());
        if (keyBinds.get(1).isPressed())
            network.sendToServer(new OpenMessage());
    }


    public RagnarokCards() {

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        GLM.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT,Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,Config.SERVER_CONFIG);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientStuff);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
    }


    private void clientStuff(final FMLClientSetupEvent event) {
        ScreenManager.registerFactory(BagContainer.type, BagGUI::new);

        keyBinds.add(0, new KeyBinding("key.simplybackpacks.backpackpickup.desc", -1, "key.simplybackpacks.category"));
        ClientRegistry.registerKeyBinding(keyBinds.get(0));

        keyBinds.add(1, new KeyBinding("key.simplybackpacks.backpackpickup.desc", -1, "key.simplybackpacks.category"));
        ClientRegistry.registerKeyBinding(keyBinds.get(1));
    }

    //register recipes
    public static final SpecialRecipeSerializer<LootBoxRecipe> LOOT_BOX_RECIPE_SERIEALIZER = new SpecialRecipeSerializer<>(LootBoxRecipe::new);
    @SubscribeEvent
    public static void registerRecipeSerializer(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        LOOT_BOX_RECIPE_SERIEALIZER.setRegistryName("ragnarok_cards:loot_box_special_recipe");
        event.getRegistry().register(LOOT_BOX_RECIPE_SERIEALIZER);
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


}