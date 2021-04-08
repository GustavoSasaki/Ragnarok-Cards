package ragnarok_cards;

import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ragnarok_cards.Items.RagnarokCard;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ragnarok_cards.Items.aa;

import java.util.Arrays;



@Mod(RegisterEventsItems.MOD_ID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegisterEventsItems
{
    public static final String MOD_ID = "ragnarok_cards";


 private static final DeferredRegister<GlobalLootModifierSerializer<?>> GLM = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, RegisterEventsItems.MOD_ID);
 private static final RegistryObject<aa.WheatSeedsConverterModifier.Serializer> WHEATSEEDS = GLM.register("wheat_harvest", aa.WheatSeedsConverterModifier.Serializer::new);
 private static final RegistryObject<aa.WheatSeedsConverterModifier2.Serializer> SILVERFISH = GLM.register("wheat_harvest2",aa.WheatSeedsConverterModifier2.Serializer::new);

    //implementados mas falta melhor sinalização
    public static final Item SNOWMAN_CARD = new RagnarokCard("snowman","Snowballs have 5% to add 3s slowness"); //ok

    //todo add aprticle when hit enemy whiter e apply whiter
    //todo add a cooldown
    public static final Item WHITER_SKELETON_CARD = new RagnarokCard("whiter_skeleton",
            Arrays.asList("2% to apply whiter with close range weapons","Additional 50% damage against enemies with wither"),
            "explosions deal 30% more damage");
    //todo add option the remove or reduce explosion options
    //todo add better sound effect when activate
    public static final Item CREEPER_CARD = new RagnarokCard("creeper","20% chance to nullify explosion damage","4% chance to took an arrow in the knee");

    //totalmente implementados
    public static final Item PHANTOM_CARD = new RagnarokCard("phantom","25% damage increase against flying enemies","15% more damage from The End mobs");
    public static final Item ZOMBIE_CARD = new RagnarokCard("zombie","Additional 20% damage against undead","Llama deals 4x damage, be careful");
    public static final Item OCELOT_CARD = new RagnarokCard("ocelot","30% chance to negate fall damage","Receive 20% damage in water");
    public static final Item SKELETON_CARD = new RagnarokCard("skeleton",
            Arrays.asList("Attacks against same type enemies gets 2% consecutively stronger","Bonus reset if hit other type of enemy"),
            "fire damage deal 20% more");
    public static final Item SPIDER_CARD = new RagnarokCard("spider","Bane of arthropods","bane of arthropods");
    //maybe change to close range weapon
    public static final Item ZOMBIE_PIGLIN_CARD = new RagnarokCard("zombie_piglin","4% chance to deal 3x damage","2% chance to receive 3x damage");
    public static final Item CAVE_SPIDER_CARD = new RagnarokCard("cave_spider","4% chance to add poison with close range weapons","Bane against arthropods");
    public static final Item PIGLIN_CARD = new RagnarokCard("piglin","1.5+ damage with bad material weapons and tools","Attacks from undead are 20% stronger and causes hunger");
    public static final Item PIG_CARD = new RagnarokCard("pig","25% more damage against Pig type enemies","15% more fall damage");
    public static final Item WOLF_CARD = new RagnarokCard("wolf",
            Arrays.asList("Pets deal +1 damage","Pets have 20% chance to receive reduce damage"),
            "Receive 10% more damage from anything");
    public static final Item SHEEP_CARD = new RagnarokCard("sheep","Additional 25% damage against animals","Extra effects when eating meat"); //ok
    public static final Item WITCH_CARD = new RagnarokCard("witch","Multiplies damage by 1.2 for each effect in the enemy with close range weapons","3% chance with close range weapons to apply random effect in the user and the enemy");

    //100 tested

    //todo axe hit destroy equiped wapon/armor
    //todo hoe last hit chance to spawn golem 
    //todo
    //chicken;cow;squid;enderman;zombie pigman;
    //todo
    //remove some effects for range weapons
    //todo after
    //blaze;slime;ghast;villager;bats
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(CAVE_SPIDER_CARD,CREEPER_CARD,OCELOT_CARD,PHANTOM_CARD,PIG_CARD,PIGLIN_CARD,
                SHEEP_CARD,SKELETON_CARD,SNOWMAN_CARD,SPIDER_CARD,WHITER_SKELETON_CARD,WITCH_CARD,WOLF_CARD,ZOMBIE_CARD,
                ZOMBIE_PIGLIN_CARD);
    }


 public RegisterEventsItems() {
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
  GLM.register(FMLJavaModLoadingContext.get().getModEventBus());
 }

}