package ragnarok_cards.Items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import org.apache.commons.lang3.Range;
import org.lwjgl.glfw.GLFW;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ragnarok_cards.RagnarokCards.GLM;

public class RagnarokCard extends Item {
    String cardName;
    List<String> translationKeyBuffs = new ArrayList<>();
    List<String> translationKeyDebuffs = new ArrayList<>();

    //this constructor will always activate
    public RagnarokCard(String cardName,int quantityBuffs,int quantityDebuffs) {
        super(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT).maxStackSize(8));

        this.cardName = cardName;
        translationKeyBuffs = IntStream.range(0, quantityBuffs).
                mapToObj(x -> "item.ragnarok_cards."+this.cardName+".buff"+String.valueOf(x)).collect(Collectors.toList());

        translationKeyDebuffs = IntStream.range(0, quantityDebuffs).
                mapToObj(x -> "item.ragnarok_cards."+this.cardName+".debuff"+String.valueOf(x)).collect(Collectors.toList());

        GLM.register(cardName + "_card", LootModifiers.AddMobDrop.Serializer::new);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {

            for (String keyBuff : translationKeyBuffs) {
                tooltip.add(new TranslationTextComponent(keyBuff).mergeStyle(TextFormatting.GRAY));

            }
            for (String keyDebuff : translationKeyDebuffs) {
                tooltip.add(new TranslationTextComponent(keyDebuff).mergeStyle(TextFormatting.RED));
            }
        } else {
            tooltip.add(new TranslationTextComponent("\u00A77Hold \u00A7eShift \u00A77for More Information") );
            //todo add support to translation
            //tooltip.add(new TranslationTextComponent("tooltip.special_item.hold_shift"));
        }
    }

    public String getCardName(){
        return this.cardName;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {

        //put int he upper row inventory if possible
        for(int i=9;i<18;i++){

            if(playerIn.inventory.mainInventory.get(i).isEmpty()){
                playerIn.inventory.setInventorySlotContents(i,playerIn.inventory.getStackInSlot(playerIn.inventory.currentItem));
                playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem,ItemStack.EMPTY);

                playerIn.playSound(SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, playerIn.getSoundCategory(), 1.0F, 1.0F);
                break;
            }
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

}
