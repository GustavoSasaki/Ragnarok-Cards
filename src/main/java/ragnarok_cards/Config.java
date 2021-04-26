package ragnarok_cards;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.HashMap;
import java.util.Map;

import static ragnarok_cards.RegisterItems.cardNames;

@Mod.EventBusSubscriber
public class Config {

        public static final String CATEGORY_CARDS = "cards";
        public static final String SUB_CATEGORY_CARDS = "drop_rate";

        public static ForgeConfigSpec SERVER_CONFIG;
        public static ForgeConfigSpec CLIENT_CONFIG;

        public static Map<String, ForgeConfigSpec.IntValue> mapDropRate = new HashMap<String, ForgeConfigSpec.IntValue>();
        public static ForgeConfigSpec.IntValue LOOT_BOX_COST;
        public static ForgeConfigSpec.IntValue LOOT_BOX_MAX_STACK;
        public static ForgeConfigSpec.IntValue ALL_CARDS_MAX_STACK;

        public static ForgeConfigSpec.IntValue CAVE_SPIDER_CHANCE;
        public static ForgeConfigSpec.IntValue CAVE_SPIDER_POISON_TIME;
        public static ForgeConfigSpec.BooleanValue CAVE_SPIDER_STACK;
        public static ForgeConfigSpec.IntValue CAVE_SPIDER_SLOW_TIME_NEG;
        public static ForgeConfigSpec.DoubleValue CAVE_SPIDER_DAMAGE_NEG;

        public static ForgeConfigSpec.DoubleValue ZOMBIE_PIGLIN_MULTIPLIER;
        public static ForgeConfigSpec.IntValue ZOMBIE_PIGLIN_CHANCE;
        public static ForgeConfigSpec.DoubleValue ZOMBIE_PIGLIN_MULTIPLIER_NEG;
        public static ForgeConfigSpec.IntValue ZOMBIE_PIGLIN_CHANCE_NEG;

        public static ForgeConfigSpec.IntValue OCELOT_CHANCE;
        public static ForgeConfigSpec.IntValue OCELOT_SPEED_TIME;
        public static ForgeConfigSpec.DoubleValue OCELOT_MULTIPLIER_NEG;

        public static ForgeConfigSpec.DoubleValue PIGLIN_DAMAGE;
        public static ForgeConfigSpec.DoubleValue PIGLIN_MAX_TIER;
        public static ForgeConfigSpec.DoubleValue PIGLIN_MULTIPLIER_NEG;
        public static ForgeConfigSpec.IntValue PIGLIN_CHANCE_NEG;
        public static ForgeConfigSpec.IntValue PIGLIN_TIME_NEG;

        public static ForgeConfigSpec.DoubleValue SKELETON_MULTIPLIER;
        public static ForgeConfigSpec.DoubleValue SKELETON_MULTIPLIER_NEG;
        static{

            ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
            ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

            SERVER_BUILDER.push("loot_box");
            LOOT_BOX_COST= SERVER_BUILDER.comment("How many card to make a loot box")
                    .defineInRange("card_cost", 3, 1, 8);
            LOOT_BOX_MAX_STACK= SERVER_BUILDER.comment("Stack up to")
                    .defineInRange("max_stack", 16, 1, 64);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push(CATEGORY_CARDS);


            ALL_CARDS_MAX_STACK= SERVER_BUILDER.comment("Stack up to")
                    .defineInRange("max_stack", 8, 1, 64);


            //cards combat configs
            SERVER_BUILDER.push("cave_spider");
            CAVE_SPIDER_CHANCE = SERVER_BUILDER.comment("chance of add poison").
                    defineInRange("chance", 4, 0, 100);
            CAVE_SPIDER_POISON_TIME = SERVER_BUILDER.comment("how much poison time added in seconds").
                    defineInRange("time", 5, 0, 100);
            CAVE_SPIDER_SLOW_TIME_NEG = SERVER_BUILDER.comment("how much time in ticks the player receive when hit by a arthropod").
                    defineInRange("slow_time_neg",40,0,2000);
            CAVE_SPIDER_DAMAGE_NEG = SERVER_BUILDER.comment("the receive extra flat damage from arthropods").
                    defineInRange("damage_neg", 1f, 0, 10);
            CAVE_SPIDER_STACK = SERVER_BUILDER.comment("activate even if enemy already poison").
                    define("stack",true);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("zombie_piglin");
            ZOMBIE_PIGLIN_CHANCE = SERVER_BUILDER.comment("chance of applying multiplier").
                    defineInRange("chance", 4, 0, 100);
            ZOMBIE_PIGLIN_CHANCE_NEG = SERVER_BUILDER.comment("chance of enemy attack having negative multiplier apply").
                    defineInRange("chance_neg", 2, 0, 100);
            ZOMBIE_PIGLIN_MULTIPLIER = SERVER_BUILDER.defineInRange("multiplier",3d,1d,10d);
            ZOMBIE_PIGLIN_MULTIPLIER_NEG = SERVER_BUILDER.defineInRange("multiplier_neg",3d,1d,10d);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("ocelot");
            OCELOT_CHANCE = SERVER_BUILDER.comment("chance of nullifying fall damage").
                    defineInRange("chance", 30, 0, 100);
            OCELOT_SPEED_TIME = SERVER_BUILDER.comment("when nullified fall damage, add X ticks of speed 4 to the player").
                    defineInRange("time", 30, 0, 2000);
            OCELOT_MULTIPLIER_NEG = SERVER_BUILDER.comment("damage multiplier of enemy when player under water").
                    defineInRange("multiplier_neg", 0.3, 0, 10);
            SERVER_BUILDER.pop();


            SERVER_BUILDER.push("piglin");
            PIGLIN_DAMAGE = SERVER_BUILDER.comment("flat damage increase when player uses weapon/tool under max tier material value").
                    defineInRange("damage", 1.5, 0, 10);
            PIGLIN_MAX_TIER = SERVER_BUILDER.comment("only weapons/tools with lower than max tier material value activate effect").
                    defineInRange("max_tier", 2f, 0, 10);
            PIGLIN_CHANCE_NEG = SERVER_BUILDER.comment("chance of undead applying hunger when hitting the player").
                    defineInRange("chance_neg", 100, 0, 100);
            PIGLIN_TIME_NEG = SERVER_BUILDER.comment("how much time in ticks for the applied hunger").
                    defineInRange("time_neg", 200, 0, 2000);
            PIGLIN_MULTIPLIER_NEG=  SERVER_BUILDER.comment("multiplier apply to undead attacks to the player").
                    defineInRange("multiplier_neg", 0.2, 0, 10);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("skeleton");
            SKELETON_MULTIPLIER = SERVER_BUILDER.comment("value added to the multiplier after every consecutively of the same enemy").
                    defineInRange("multiplier", 0.02, 0, 10);
            SKELETON_MULTIPLIER_NEG = SERVER_BUILDER.comment("damage multiplier of fire damage dealt to the player").
                    defineInRange("multiplier_neg", 0.2, 0, 10);
            SERVER_BUILDER.pop();

            //getting drop rates
            for (String cardName : cardNames){
                SERVER_BUILDER.push(cardName);
                mapDropRate.put(cardName, SERVER_BUILDER
                        .defineInRange("drop_rate", 100, 0, 100));
                SERVER_BUILDER.pop();
            }

            SERVER_BUILDER.pop();
            SERVER_CONFIG = SERVER_BUILDER.build();
            CLIENT_CONFIG = CLIENT_BUILDER.build();
        }


        @SubscribeEvent
        public static void onLoad(final ModConfig.Loading configEvent) {

        }

        @SubscribeEvent
        public static void onReload(final ModConfig.Reloading configEvent) {
        }
}
