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

    //meio implementados
    //random broken
    //todo verificate if all effects apply
    public static final Item WITCH_CARD = new RagnarokCard("witch","multiplies damage by 1.2 for each effect in the enemy with close range weapons","3% chance with close range weapons to apply random effect in the user and the enemy");


    //implementados mas falta melhor sinalização
    public static final Item SNOWMAN_CARD = new RagnarokCard("snowman","snowball have 5% to add 3s slowness"); //ok

    //todo add aprticle when hit enemy whiter e apply whiter
    //todo add a cooldown
    public static final Item WHITER_SKELETON_CARD = new RagnarokCard("whiter_skeleton","2% to apply whiter with close range weapons. Additional 50% damage against whiter enemies","explosions deal 30% more damage");
    //todo add option the remove or reduce explosion options
    //todo add better sound effect when activate
    public static final Item CREEPER_CARD = new RagnarokCard("creeper","20% chance to nullify explosion damage","4% chance to took an arrow in the knee");

    //totalmente implementados
    public static final Item PHANTOM_CARD = new RagnarokCard("phantom","25% damage increase against flying enemies","15% more damage from the end mobs");
    public static final Item ZOMBIE_CARD = new RagnarokCard("zombie","additional 20% damage against undead","Llama deals 4x damage, be careful");
    public static final Item OCELOT_CARD = new RagnarokCard("ocelot","30% chance to negate fall damage","receive 20% damage in water");
    public static final Item SKELETON_CARD = new RagnarokCard("skeleton","attacks against same type enemies gets 1% consecutively stronger","fire damage deal 20% more");
    public static final Item SPIDER_CARD = new RagnarokCard("spider","bane of arthropods","bane of arthropods");
    //maybe change to close range weapon
    public static final Item ZOMBIE_PIGLIN_CARD = new RagnarokCard("zombie_piglin","4% chance to deal 3x damage","2% chance to receive 3x damage");
    public static final Item CAVE_SPIDER_CARD = new RagnarokCard("cave_spider","4% chance to add poison with close range weapons","bane against arthropods");
    public static final Item PIGLIN_CARD = new RagnarokCard("piglin","1.5+ damage with bad material weapons and tools","attacks from undead are 20% stronger and causes hunger");
    public static final Item PIG_CARD = new RagnarokCard("pig","25% more damage against pig type enemies","15% more fall damage");
    public static final Item WOLF_CARD = new RagnarokCard("wolf","pets deal +1 damage and have 20% receive reduce damage","receive 10% more damage from anything");
    public static final Item SHEEP_CARD = new RagnarokCard("sheep","additional 25% damage against animals","extra effects when eating meat"); //ok

    //100 tested

    //todo
    //chicken;cow;squid;enderman;zombie pigman;
    //todo
    //remove some effects for range weapons
    //todo after
    //blaze;slime;ghast;villager;bat
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(ZOMBIE_CARD, CREEPER_CARD, SPIDER_CARD, SKELETON_CARD,SNOWMAN_CARD,SHEEP_CARD,
                PHANTOM_CARD,WITCH_CARD,PIG_CARD,PIGLIN_CARD,CAVE_SPIDER_CARD,WOLF_CARD,OCELOT_CARD,WHITER_SKELETON_CARD,
                ZOMBIE_PIGLIN_CARD);
    }

}