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

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static ragnarok_cards.Config.mapDropRate;

public class LootModifiers {

    public static class AddMobDrop extends net.minecraftforge.common.loot.LootModifier {
        private final int chance;
        private final Item cardItem;
        public AddMobDrop(ILootCondition[] conditionsIn,int chance,Item cardItem) {
            super(conditionsIn);
            this.chance = chance;
            this.cardItem = cardItem;
        }

        @Nonnull
        @Override
        public List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {

            if(mapDropRate.get( ((RagnarokCard)this.cardItem).cardName).get() > context.getRandom().nextInt(100)){
                generatedLoot.add(new ItemStack(this.cardItem));
            }
            return generatedLoot;
        }

        public static class Serializer extends GlobalLootModifierSerializer<AddMobDrop> {

            @Override
            public AddMobDrop read(ResourceLocation name, JsonObject object, ILootCondition[] conditionsIn) {
                int chance = JSONUtils.getInt(object, "chance");
                Item itemCard = ForgeRegistries.ITEMS.getValue(new ResourceLocation((JSONUtils.getString(object, "cardItem"))));
                return new AddMobDrop(conditionsIn, chance, itemCard);
            }

            @Override
            public JsonObject write(AddMobDrop instance) {
                JsonObject json = makeConditions(instance.conditions);
                json.addProperty("chance", instance.chance);
                json.addProperty("cardItem", ForgeRegistries.ITEMS.getKey(instance.cardItem).toString());
                return json;
            }
        }
    }
}
