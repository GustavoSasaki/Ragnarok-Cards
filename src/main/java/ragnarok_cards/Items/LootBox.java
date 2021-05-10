package ragnarok_cards.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ragnarok_cards.Config.LOOT_BOX_MAX_STACK;
import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.RegisterItems.ITEMS;
import static ragnarok_cards.RegisterItems.cardNames;

public class LootBox extends Item {
    public LootBox( ) {

        super(new Item.Properties().maxStackSize(LOOT_BOX_MAX_STACK.get()).group(ItemGroup.COMBAT));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        if(worldIn.isRemote() ){
            return ActionResult.func_233538_a_(playerIn.getHeldItemMainhand(), worldIn.isRemote());
        }

        //getting all cards seen by the player
        CompoundNBT nbt = playerIn.getPersistentData().getCompound(playerIn.PERSISTED_NBT_TAG).getCompound(MOD_ID);

        List<RagnarokCard> cards_seen = ITEMS.getEntries().stream().
                filter(x -> nbt.contains(x.get().getRegistryName().toString()+".seen")).
                map(x -> ( (RagnarokCard)x.get()) ).
                collect(Collectors.toList());


        if(!cards_seen.isEmpty()){
            //spawning new card
            int randNumber = playerIn.world.rand.nextInt(cards_seen.size());
            ItemStack stack = new ItemStack(cards_seen.get(randNumber).getItem());

            ItemEntity itementity = new ItemEntity(playerIn.world, playerIn.getPosX(), playerIn.getPosY() + 1, playerIn.getPosZ(), stack);
            itementity.setPickupDelay(20);
            if (playerIn.captureDrops() != null){
                playerIn.captureDrops().add(itementity);
            }
            else {
                playerIn.world.addEntity(itementity);
            }

            //consuming the loot box
            playerIn.addStat(Stats.ITEM_USED.get(this));
            if (!playerIn.abilities.isCreativeMode) {
                playerIn.getHeldItemMainhand().shrink(1);
            }
        }
        return ActionResult.func_233538_a_(playerIn.getHeldItemMainhand(), worldIn.isRemote());
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(new TranslationTextComponent("item.ragnarok_cards.loot_box.description").mergeStyle(TextFormatting.GRAY));

    }


}
