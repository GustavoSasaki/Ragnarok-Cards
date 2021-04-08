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
import org.lwjgl.glfw.GLFW;
import net.minecraft.util.text.*;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RagnarokCard extends Item {
    String cardName;
    ArrayList<String> positiveEffects = new ArrayList<String>();
    ArrayList<String> negativeEffects = new ArrayList<String>();

    public RagnarokCard(String cardName) {
        super(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT));
        this.cardName = cardName;
        setRegistryName("ragnarok_cards",cardName + "_card");
    }

    public RagnarokCard(String cardName,List<String> positiveEffectText,String negativeEffectText) {
        this(cardName);
        this.positiveEffects.addAll(positiveEffectText);
        this.negativeEffects.add(negativeEffectText);
    }

    public RagnarokCard(String cardName,String positiveEffectText,String negativeEffectText) {
        this(cardName);
        this.positiveEffects.add(positiveEffectText);
        this.negativeEffects.add(negativeEffectText);
    }

    public RagnarokCard(String cardName,String positiveEffectText) {
        this(cardName);
        this.positiveEffects.add(positiveEffectText);
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), GLFW.GLFW_KEY_LEFT_SHIFT)) {

            for (String effect : positiveEffects) {
                tooltip.add(new StringTextComponent(effect).mergeStyle(TextFormatting.GRAY));
            }
            for (String effect : negativeEffects) {
                tooltip.add(new StringTextComponent(effect).mergeStyle(TextFormatting.RED));
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
