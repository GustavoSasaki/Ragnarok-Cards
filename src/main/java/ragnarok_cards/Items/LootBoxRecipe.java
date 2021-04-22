package ragnarok_cards.Items;


import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import static ragnarok_cards.Config.LOOT_BOX_COST;
import static ragnarok_cards.RagnarokCards.LOOT_BOX_RECIPE_SERIEALIZER;
import static ragnarok_cards.RegisterItems.LOOT_BOX;

public class LootBoxRecipe extends SpecialRecipe {

    public LootBoxRecipe(ResourceLocation resourceLocation) {
        super(resourceLocation);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        boolean hasChest = true;
        int ragnarokCards = 0;
        for(int i=0;i<inv.getSizeInventory();i++){

            if(Block.getBlockFromItem(inv.getStackInSlot(i).getItem()) instanceof ChestBlock){
                hasChest = true;
            };

            if(inv.getStackInSlot(i).getItem() instanceof RagnarokCard){
                ragnarokCards+=1;
            }
        }
        return (hasChest && ragnarokCards==LOOT_BOX_COST.get());
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        return new ItemStack(LOOT_BOX.get());
    }

    @Override
    public boolean canFit(int width, int height) {
        return width * height > 2;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return LOOT_BOX_RECIPE_SERIEALIZER;
    }
}
