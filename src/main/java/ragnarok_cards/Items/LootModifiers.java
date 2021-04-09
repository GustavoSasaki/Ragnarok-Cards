package ragnarok_cards.Items;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import ragnarok_cards.RegisterEventsItems;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class LootModifiers {

    public static class WheatSeedsConverterModifier extends net.minecraftforge.common.loot.LootModifier {
        private final int numSeedsToConvert;
        private final Item itemToCheck;
        private final Item itemReward;
        public WheatSeedsConverterModifier(ILootCondition[] conditionsIn, int numSeeds, Item itemCheck, Item reward) {
            super(conditionsIn);
            numSeedsToConvert = numSeeds;
            itemToCheck = itemCheck;
            itemReward = reward;
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            int numSeeds = 0;
            for(ItemStack stack : generatedLoot) {
                if(stack.getItem() == itemToCheck)
                    numSeeds+=stack.getCount();
            }
            if(numSeeds >= numSeedsToConvert) {
                generatedLoot.removeIf(x -> x.getItem() == itemToCheck);
                generatedLoot.add(new ItemStack(itemReward, (numSeeds/numSeedsToConvert)));
                numSeeds = numSeeds%numSeedsToConvert;
                if(numSeeds > 0)
                    generatedLoot.add(new ItemStack(itemToCheck, numSeeds));
            }
            generatedLoot.add(new ItemStack(Items.BAMBOO,2));
            return generatedLoot;
        }

        public static class Serializer extends GlobalLootModifierSerializer<WheatSeedsConverterModifier> {

            @Override
            public WheatSeedsConverterModifier read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
                int numSeeds = JSONUtils.getInt(object, "numSeeds");
                Item seed = ForgeRegistries.ITEMS.getValue(new ResourceLocation((JSONUtils.getString(object, "seedItem"))));
                Item wheat = ForgeRegistries.ITEMS.getValue(new ResourceLocation(JSONUtils.getString(object, "replacement")));
                return new WheatSeedsConverterModifier(conditionsIn, numSeeds, seed, wheat);
            }

            @Override
            public JsonObject write(WheatSeedsConverterModifier instance) {
                JsonObject json = makeConditions(instance.conditions);
                json.addProperty("numSeeds", instance.numSeedsToConvert);
                json.addProperty("seedItem", ForgeRegistries.ITEMS.getKey(instance.itemToCheck).toString());
                json.addProperty("replacement", ForgeRegistries.ITEMS.getKey(instance.itemReward).toString());
                return json;
            }
        }
    }


    public static class AddMobDrop extends net.minecraftforge.common.loot.LootModifier {
        public AddMobDrop(ILootCondition[] conditionsIn) {
            super(conditionsIn);
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
            generatedLoot.add(new ItemStack(Items.BAMBOO,2));
            return generatedLoot;
        }

        public static class Serializer extends GlobalLootModifierSerializer<AddMobDrop> {

            @Override
            public AddMobDrop read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
                return new AddMobDrop(conditionsIn);
            }

            @Override
            public JsonObject write(AddMobDrop instance) {
                JsonObject json = makeConditions(instance.conditions);
                return json;
            }
        }
    }
}
