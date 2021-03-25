package ragnarok_cards;

import ragnarok_cards.Items.RagnarokCard;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod(RegisterEventsItems.MOD_ID)
@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
public class RegisterEventsItems
{
    public static final String MOD_ID = "ragnarok_cards";

    public static final Item ZOMBIE_CARD = new RagnarokCard("zombie"); //ok
    public static final Item CREEPER_CARD = new RagnarokCard("creeper");
    public static final Item SPIDER_CARD = new RagnarokCard("spider"); //ok
    public static final Item CAVE_SPIDER_CARD = new RagnarokCard("cave_spider"); //ok
    public static final Item SKELETON_CARD = new RagnarokCard("skeleton");// ok
    public static final Item SNOWMAN_CARD = new RagnarokCard("snowman"); //ok
    public static final Item SHEEP_CARD = new RagnarokCard("sheep"); //ok
    public static final Item PHANTOM_CARD = new RagnarokCard("phantom"); //ok
    public static final Item WITCH_CARD = new RagnarokCard("witch"); //ok
    public static final Item PIG_CARD = new RagnarokCard("pig"); //ok
    public static final Item PIGLIN_CARD = new RagnarokCard("piglin");
    public static final Item WOLF_CARD = new RagnarokCard("wolf"); // ok
    public static final Item OCELOT_CARD = new RagnarokCard("ocelot"); //ok
    public static final Item WHITER_SKELETON_CARD = new RagnarokCard("whiter_skeleton"); //ok

    //todo
    //chicken;cow;squid;enderman;zombie pigman;
    //todo
    //remove some effects for range weapons
    //todo after
    //blaze;slime;ghast;villager;bat
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ZOMBIE_CARD, CREEPER_CARD, SPIDER_CARD, SKELETON_CARD,SNOWMAN_CARD,SHEEP_CARD,
                PHANTOM_CARD,WITCH_CARD,PIG_CARD,PIGLIN_CARD,CAVE_SPIDER_CARD,WOLF_CARD,OCELOT_CARD,WHITER_SKELETON_CARD);
    }

}