package ragnarok_cards;

import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ragnarok_cards.Items.LootBoxRecipe;
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

        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());

        GLM.register(FMLJavaModLoadingContext.get().getModEventBus());

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT,Config.CLIENT_CONFIG);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER,Config.SERVER_CONFIG);
    }

    //register recipes
    public static final SpecialRecipeSerializer<LootBoxRecipe> LOOT_BOX_RECIPE_SERIEALIZER = new SpecialRecipeSerializer<>(LootBoxRecipe::new);
    @SubscribeEvent
    public static void registerRecipeSerializer(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        LOOT_BOX_RECIPE_SERIEALIZER.setRegistryName("ragnarok_cards:loot_box_special_recipe");
        event.getRegistry().register(LOOT_BOX_RECIPE_SERIEALIZER);
    }


}