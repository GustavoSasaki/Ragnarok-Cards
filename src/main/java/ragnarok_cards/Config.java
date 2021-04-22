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

        public static ForgeConfigSpec.IntValue ZOMBIE;

        public static Map<String, ForgeConfigSpec.IntValue> mapDropRate = new HashMap<String, ForgeConfigSpec.IntValue>();
        public static ForgeConfigSpec.IntValue LOOT_BOX_COST;
        public static ForgeConfigSpec.IntValue LOOT_BOX_MAX_STACK;
        public static ForgeConfigSpec.IntValue ALL_CARDS_MAX_STACK;

        static{

            ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
            ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();



            SERVER_BUILDER.push(CATEGORY_CARDS);


            ALL_CARDS_MAX_STACK= SERVER_BUILDER.comment("Stack up to")
                    .defineInRange("max_stack", 8, 1, 64);

            for (String cardName : cardNames){
                SERVER_BUILDER.push(cardName);
                mapDropRate.put(cardName, SERVER_BUILDER.comment("drop rate of "+cardName)
                        .defineInRange("drop_rate", 100, 0, 100));
                SERVER_BUILDER.pop();
            }


            SERVER_BUILDER.push("loot_box");
            LOOT_BOX_COST= SERVER_BUILDER.comment("How many card to make a loot box")
                    .defineInRange("card_cost", 3, 1, 8);
            LOOT_BOX_MAX_STACK= SERVER_BUILDER.comment("Stack up to")
                    .defineInRange("max_stack", 16, 1, 64);
            SERVER_BUILDER.pop();


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
