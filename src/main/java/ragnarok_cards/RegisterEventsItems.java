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

    public static final Item ZOMBIE_CARD = new RagnarokCard("zombie");
    public static final Item CREEPER_CARD = new RagnarokCard("creeper");
    public static final Item SPIDER_CARD = new RagnarokCard("spider");
    public static final Item SKELETON_CARD = new RagnarokCard("skeleton");
    public static final Item SnowMAN_CARD = new RagnarokCard("snowman");

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ZOMBIE_CARD, CREEPER_CARD, SPIDER_CARD, SKELETON_CARD,SnowMAN_CARD);
    }

}