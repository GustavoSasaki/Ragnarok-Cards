package ragnarok_cards;

import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.RegisterItems.cardNames;

@Mod.EventBusSubscriber
public class Config {

        public static final String CATEGORY_CARDS = MOD_ID;

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

        public static ForgeConfigSpec.DoubleValue WOLF_DAMAGE;
        public static ForgeConfigSpec.IntValue WOLF_CHANCE;
        public static ForgeConfigSpec.DoubleValue WOLF_MULTIPLIER_NEG;

        public static ForgeConfigSpec.DoubleValue SHEEP_MULTIPLIER;
        public static ForgeConfigSpec.IntValue SHEEP_CHANCE_NEG;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> SHEEP_EFFECT_TIME;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> SHEEP_EFFECT_POWER;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> SHEEP_EFFECT;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> SHEEP_EFFECT_TIME_NEG;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> SHEEP_EFFECT_POWER_NEG;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> SHEEP_EFFECT_NEG;

        public static ForgeConfigSpec.DoubleValue WITCH_MULTIPLIER;
        public static ForgeConfigSpec.IntValue WITCH_CHANCE_NEG;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> WITCH_EFFECT_NEG;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> WITCH_TIME_NEG;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> WITCH_POWER_NEG;

        public static ForgeConfigSpec.DoubleValue PIG_MULTIPLIER;
        public static ForgeConfigSpec.DoubleValue PIG_MULTIPLIER_NEG;

        public static ForgeConfigSpec.IntValue SNOW_GOLEM_CHANCE;
        public static ForgeConfigSpec.IntValue SNOW_GOLEM_TIME;
        public static ForgeConfigSpec.BooleanValue SNOW_GOLEM_STACK;
        public static ForgeConfigSpec.IntValue SNOW_GOLEM_POWER;

        public static ForgeConfigSpec.DoubleValue SPIDER_DAMAGE;
        public static ForgeConfigSpec.IntValue SPIDER_TIME;
        public static ForgeConfigSpec.IntValue SPIDER_POWER;

        public static ForgeConfigSpec.IntValue CREEPER_CHANCE;
        public static ForgeConfigSpec.BooleanValue CREEPER_EXTRA_EXPLOSION;
        public static ForgeConfigSpec.IntValue CREEPER_CHANCE_NEG;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> CREEPER_EFFECT_NEG;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> CREEPER_EFFECT_TIME_NEG;
        public static ForgeConfigSpec.ConfigValue<List<? extends Integer>> CREEPER_EFFECT_POWER_NEG;

        public static ForgeConfigSpec.DoubleValue PHANTOM_MULTIPLIER;
        public static ForgeConfigSpec.DoubleValue PHANTOM_MULTIPLIER_NEG;

        public static ForgeConfigSpec.DoubleValue ZOMBIE_MULTIPLIER;
        public static ForgeConfigSpec.DoubleValue ZOMBIE_MULTIPLIER_NEG;

        public static ForgeConfigSpec.IntValue WITHER_SKELETON_CHANCE;
        public static ForgeConfigSpec.DoubleValue WITHER_SKELETON_MULTIPLIER;
        public static ForgeConfigSpec.IntValue WITHER_SKELETON_TIME;
        public static ForgeConfigSpec.DoubleValue WITHER_SKELETON_MULTIPLIER_NEG;

        public static ForgeConfigSpec.IntValue BLAZE_CHANCE1;
        public static ForgeConfigSpec.IntValue BLAZE_CHANCE2;
        public static ForgeConfigSpec.IntValue BLAZE_TIME1;
        public static ForgeConfigSpec.IntValue BLAZE_TIME2;
        public static ForgeConfigSpec.DoubleValue BLAZE_KNOCKBACK_NEG;


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

            SERVER_BUILDER.push("wolf");
            WOLF_DAMAGE = SERVER_BUILDER.comment("extra flat damage when tamed pets attack").
                    defineInRange("damage", 1f, 0, 10);
            WOLF_CHANCE = SERVER_BUILDER.comment("chance of tamed pets have damage reduce to 1").
                    defineInRange("chance", 20, 0, 100);
            WOLF_MULTIPLIER_NEG = SERVER_BUILDER.comment("damage multiplier apply to attacks against the player ").
                    defineInRange("multiplier_neg", 0.1, 0, 10);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("sheep");
            SHEEP_MULTIPLIER = SERVER_BUILDER.comment("damage multiplier apply against animals").
                    defineInRange("multiplier", 0.25f, 0, 5);
            SHEEP_CHANCE_NEG = SERVER_BUILDER.comment("chance of apply negative effects when eating meat").
                    defineInRange("chance_neg", 2, 0, 100);

            SERVER_BUILDER.comment("apply one of this effects when eating meat and dont pass the negative check");
            SHEEP_EFFECT = SERVER_BUILDER.comment("IDs of the potion effects; (you can get the ID in the Effect Wiki Page)").
                    defineList("effect", Arrays.asList(1, 5, 10, 11, 12, 3, 13, 16, 26), x -> true);
            SHEEP_EFFECT_POWER= SERVER_BUILDER.defineList("effect_power", Arrays.asList(2,2,2,2,2,2,2,2,2),
                    x -> ((Integer)x).intValue() > 0);
            SHEEP_EFFECT_TIME = SERVER_BUILDER.defineList("effect_time", Arrays.asList(200,200,200,200,200,200,200,600,600),
                    x -> ((Integer)x).intValue() > 0);
            SERVER_BUILDER.comment("apply all of this effects when eating meat and pass the negative check");
            SHEEP_EFFECT_NEG = SERVER_BUILDER.defineList("effect_neg",
                    Arrays.asList(15, 2, 19, 18),
                    x -> true);
            SHEEP_EFFECT_POWER_NEG = SERVER_BUILDER.defineList("power_neg", Arrays.asList(1,1,1,1),x -> ((Integer)x).intValue() > 0);
            SHEEP_EFFECT_TIME_NEG = SERVER_BUILDER.defineList("effect_time_neg", Arrays.asList(100,1000,1000,200),x -> ((Integer)x).intValue() > 0);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("witch");
            WITCH_MULTIPLIER = SERVER_BUILDER.comment("apply this damage multiplier for every effect in the enemy").
                    defineInRange("multiplier", 0.2f, 0, 5);
            WITCH_CHANCE_NEG= SERVER_BUILDER.comment("apply a random effect in the player and the enemy").
                    defineInRange("chance_neg", 3, 0, 100);
            WITCH_EFFECT_NEG = SERVER_BUILDER.comment("IDs of the potion effects; (you can get the ID in the Effect Wiki Page)").
                    defineList("effect_neg", Arrays.asList(1,2,5,10,11,12,13,14,19,20,21,22,24,25,28), x -> true);
            WITCH_TIME_NEG = SERVER_BUILDER.defineList("time_neg",
                    Arrays.asList(200,200,200,200,200,200,200,100,100,100,200,200,100,100,200), x -> true);
            WITCH_POWER_NEG  = SERVER_BUILDER.defineList("power_neg",
                    Arrays.asList(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1), x -> true);

            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("pig");
            PIG_MULTIPLIER = SERVER_BUILDER.comment("damage multiplier apply against Pig Type enemies").
                    defineInRange("multiplier", 0.25f, 0, 1);
            PIG_MULTIPLIER_NEG = SERVER_BUILDER.comment("damage multiplier when player receive fall damage").
                    defineInRange("multiplier_neg", 0.15f, 0, 1);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("snow_golem");
            SNOW_GOLEM_CHANCE = SERVER_BUILDER.comment("chance apply slow with snow balls").
                    defineInRange("chance", 5, 0, 1);
            SNOW_GOLEM_TIME = SERVER_BUILDER.comment("how much ticks for the slow applied").
                    defineInRange("time", 100, 0, 20000);
            SNOW_GOLEM_STACK = SERVER_BUILDER.comment("activate even if enemy already slowed").
                    define("stack",true);
            SNOW_GOLEM_POWER = SERVER_BUILDER.comment("how powerful is the slow").
                    defineInRange("power", 1, 0, 10);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("spider");
            SPIDER_DAMAGE = SERVER_BUILDER.comment("flat damage increase against arthropod").
                    defineInRange("damage", 0.25f, 0, 5);
            SPIDER_TIME = SERVER_BUILDER.comment("apply how much ticks of slow when attacking arthropods").
                    defineInRange("time", 100, 0, 20000);
            SPIDER_POWER = SERVER_BUILDER.comment("how powerfull is the slow").
                    defineInRange("power", 4, 1, 10);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("creeper");
            CREEPER_CHANCE = SERVER_BUILDER.comment("chance to nullify explosion").
                    defineInRange("chance", 20, 0, 100);
            CREEPER_EXTRA_EXPLOSION = SERVER_BUILDER.comment("when explosion nullify, explode back?").
                    define("extra_explosion", true);
            CREEPER_CHANCE_NEG  = SERVER_BUILDER.comment("chance to apply all the negative effects when hit by arrow").
                    defineInRange("chance_neg", 3, 0, 100);
            CREEPER_EFFECT_NEG = SERVER_BUILDER.comment("IDs of the potion effects; (you can get the ID in the Effect Wiki Page)").
                    defineList("effect_neg", Arrays.asList(2,15,18), x -> true);
            CREEPER_EFFECT_TIME_NEG = SERVER_BUILDER.comment("IDs of the potion effects; (you can get the ID in the Effect Wiki Page)").
                    defineList("effect_time_neg", Arrays.asList(100,30,30), x -> true);
            CREEPER_EFFECT_POWER_NEG= SERVER_BUILDER.comment("IDs of the potion effects; (you can get the ID in the Effect Wiki Page)").
                    defineList("effect_power_neg", Arrays.asList(1,2,1), x -> true);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("phantom");
            PHANTOM_MULTIPLIER = SERVER_BUILDER.comment("damage multiplier against flying enemies").
                    defineInRange("multiplier", 0.25f, 0, 5);
            PHANTOM_MULTIPLIER_NEG = SERVER_BUILDER.comment("damage multiplier when player hit by The End Mobs").
                    defineInRange("multiplier_neg", 0.15f, 0, 5);
            SERVER_BUILDER.pop();


            SERVER_BUILDER.push("zombie");
            ZOMBIE_MULTIPLIER = SERVER_BUILDER.comment("damage multiplier against undead").
                    defineInRange("multiplier", 0.2f, 0, 5);
            ZOMBIE_MULTIPLIER_NEG = SERVER_BUILDER.comment("damage multiplier when hit by Lhama").
                    defineInRange("multiplier_neg", 4f, 0, 5);
            SERVER_BUILDER.pop();

            SERVER_BUILDER.push("wither_skeleton");
            WITHER_SKELETON_CHANCE = SERVER_BUILDER.comment("chance to Apply wither when attack").
                    defineInRange("chance", 1, 0, 100);;
            WITHER_SKELETON_MULTIPLIER = SERVER_BUILDER.comment("damage multiplier against wither effected enemies").
                    defineInRange("multiplier", 0.5, 0, 5);;
            WITHER_SKELETON_MULTIPLIER_NEG = SERVER_BUILDER.comment("damage multiplier apply to explosion type damage to the player").
                    defineInRange("multiplier_neg", 0.3, 0, 5);;
            WITHER_SKELETON_TIME = SERVER_BUILDER.comment("how much time wither is apply in ticks").
                    defineInRange("time", 80, 0, 2000);
            SERVER_BUILDER.pop();


            SERVER_BUILDER.push("blaze");
            BLAZE_CHANCE1 = SERVER_BUILDER.comment("chance to set enemy on hit when attack").
                    defineInRange("chance1", 1, 0, 100);;
            BLAZE_CHANCE2 = SERVER_BUILDER.comment("chance to extend fire on enemy when attack").
                    defineInRange("chance2", 30, 0, 100);;
            BLAZE_TIME1 = SERVER_BUILDER.comment("time of fire when activate effect1").
                    defineInRange("time1", 100, 0, 2000);;
            BLAZE_TIME2 = SERVER_BUILDER.comment("time of fire when activate effect2").
                    defineInRange("time2", 100, 0, 2000);
            BLAZE_KNOCKBACK_NEG = SERVER_BUILDER.comment("percent of knockback add when player hit under water").
                    defineInRange("knockback_neg", 0.3f, 0, 5);
            SERVER_BUILDER.pop();

            //getting drop rates
            for (String cardName : cardNames){
                SERVER_BUILDER.push(cardName);
                mapDropRate.put(cardName, SERVER_BUILDER
                        .defineInRange("drop_rate", 3, 0, 100));
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
