package ragnarok_cards;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ragnarok_cards.Items.LootBox;
import ragnarok_cards.Items.BagItem;
import ragnarok_cards.Items.RagnarokCard;

import static ragnarok_cards.RagnarokCards.MOD_ID;

public class RegisterItems {
    public static String[] cardNames = {"blaze", "cave_spider", "creeper", "ocelot","phantom", "pig", "piglin", "sheep",
            "skeleton","snow_golem", "spider", "wither_skeleton", "witch","wolf","zoglin","zombie","zombie_piglin"};


    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);

    static private RegistryObject<Item> RegisterCard(String cardName,int quantityBuffs,int quantityDebuffs){
        String registryName = cardName + "_card";
        return ITEMS.register(registryName, () -> new RagnarokCard(cardName,quantityBuffs,quantityDebuffs));
    }

    public static final RegistryObject<Item> BLAZE_CARD = RegisterCard("blaze",2,1);
    public static final RegistryObject<Item>  CAVE_SPIDER_CARD = RegisterCard("cave_spider",1,1);
    public static final RegistryObject<Item>  CREEPER_CARD = RegisterCard("creeper",1,1);
    public static final RegistryObject<Item>  OCELOT_CARD = RegisterCard("ocelot",1,1);
    public static final RegistryObject<Item>  PHANTOM_CARD = RegisterCard("phantom",1,1);
    public static final RegistryObject<Item>  PIG_CARD = RegisterCard("pig",1,1);
    public static final RegistryObject<Item>  PIGLIN_CARD = RegisterCard("piglin",1,1);
    public static final RegistryObject<Item>  SHEEP_CARD = RegisterCard("sheep",1,1);
    public static final RegistryObject<Item>  SKELETON_CARD = RegisterCard("skeleton",2,1);
    public static final RegistryObject<Item> SNOWMAN_CARD = RegisterCard("snow_golem",1,0);
    public static final RegistryObject<Item> SPIDER_CARD = RegisterCard("spider",1,1);
    public static final RegistryObject<Item>  WITHER_SKELETON_CARD = RegisterCard("wither_skeleton",2,1);
    public static final RegistryObject<Item>  WITCH_CARD = RegisterCard("witch",1,1);
    public static final RegistryObject<Item>  WOLF_CARD = RegisterCard("wolf",2,1);
    public static final RegistryObject<Item> ZOMBIE_CARD = RegisterCard("zombie",1,1);
    public static final RegistryObject<Item> ZOMBIE_PIGLIN_CARD = RegisterCard("zombie_piglin",1,1);

    public static final RegistryObject<Item> LOOT_BOX = ITEMS.register("loot_box", () -> new LootBox());
    public static final RegistryObject<Item> BAG = ITEMS.register("bag", () -> new BagItem());
    //public static final Item VINDICATOR_CARD = new RagnarokCard("vindicator","Fully charge axe attack have 10% to remove one of enemy armor or weapon","Deal 30% less damage to arthropods");
    //public static final Item VILLAGER_CARD = new RagnarokCard("villager","Killing a enemy with a hoe has 3% to spawn a golem","");


}
