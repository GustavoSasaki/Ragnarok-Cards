package ragnarok_cards;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import ragnarok_cards.Items.RagnarokCard;

import java.util.Arrays;

public class RegisterItems {
    public static String[] cardNames = {"blaze", "cave_spider", "creeper", "ocelot","phantom", "pig", "piglin", "sheep",
            "skeleton","snow_golem", "spider", "whiter_skeleton", "witch","wolf","zoglin","zombie","zombie_piglin"};



    public static final Item SNOWMAN_CARD = new RagnarokCard("snow_golem","Snowballs have 5% to add 3s slowness"); //ok
    public static final Item WHITER_SKELETON_CARD = new RagnarokCard("whiter_skeleton",
            Arrays.asList("2% to apply whiter with close range weapons","Additional 50% damage against enemies with wither"),
            "explosions deal 30% more damage");
    public static final Item CREEPER_CARD = new RagnarokCard("creeper","20% chance to nullify explosion damage","4% chance to took an arrow in the knee");
    public static final Item PHANTOM_CARD = new RagnarokCard("phantom","25% damage increase against flying enemies","15% more damage from The End mobs");
    public static final Item ZOMBIE_CARD = new RagnarokCard("zombie","Additional 20% damage against undead","Llama deals 4x damage, be careful");
    public static final Item OCELOT_CARD = new RagnarokCard("ocelot","30% chance to negate fall damage","Receive 20% damage in water");
    public static final Item SKELETON_CARD = new RagnarokCard("skeleton",
            Arrays.asList("Attacks against same type enemies gets 2% consecutively stronger","Bonus reset if hit other type of enemy"),
            "fire damage deal 20% more");
    public static final Item SPIDER_CARD = new RagnarokCard("spider","Bane of arthropods","Bane of arthropods");
    public static final Item ZOMBIE_PIGLIN_CARD = new RagnarokCard("zombie_piglin","4% chance to deal 3x damage","2% chance to receive 3x damage");
    public static final Item CAVE_SPIDER_CARD = new RagnarokCard("cave_spider","4% chance to add poison with close range weapons","Bane against arthropods");
    public static final Item PIGLIN_CARD = new RagnarokCard("piglin","1.5+ damage with bad material weapons and tools","Attacks from undead are 20% stronger and causes hunger");
    public static final Item PIG_CARD = new RagnarokCard("pig","25% more damage against Pig type enemies","15% more fall damage");
    public static final Item WOLF_CARD = new RagnarokCard("wolf",
            Arrays.asList("Pets deal +1 damage","Pets have 20% chance to receive reduce damage"),
            "Receive 10% more damage from anything");
    public static final Item SHEEP_CARD = new RagnarokCard("sheep","Additional 25% damage against animals","Extra effects when eating meat"); //ok
    public static final Item WITCH_CARD = new RagnarokCard("witch","Multiplies damage by 1.2 for each effect in the enemy with close range weapons","3% chance with close range weapons to apply random effect in the user and the enemy");
    public static final Item BLAZE_CARD = new RagnarokCard("blaze",
            Arrays.asList("Attacks have 1% chance to set enemy on fire for 5 seconds","Attacks have 30% to extend fire for 5 seconds"),
            "Extra 30% knockback under water");


    //public static final Item VINDICATOR_CARD = new RagnarokCard("vindicator","Fully charge axe attack have 10% to remove one of enemy armor or weapon","Deal 30% less damage to arthropods");
    //public static final Item VILLAGER_CARD = new RagnarokCard("villager","Killing a enemy with a hoe has 3% to spawn a golem","");
}
