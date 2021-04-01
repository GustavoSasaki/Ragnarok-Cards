package ragnarok_cards.Items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RagnarokCard extends Item {
    String cardName;
    String positiveEffectText;
    String negativeEffectText;
    public RagnarokCard(String cardName,String positiveEffectText,@Nullable String negativeEffectText) {
        super(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT));
        this.cardName = cardName;
        this.positiveEffectText = positiveEffectText;
        this.negativeEffectText = negativeEffectText;
        setRegistryName("ragnarok_cards",cardName + "_card");
    }

    public RagnarokCard(String cardName,String positiveEffectText) {
        this(cardName,positiveEffectText,null);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flag) {
        super.addInformation(stack, worldIn, tooltip, flag);
        tooltip.add(new StringTextComponent(this.positiveEffectText).mergeStyle(TextFormatting.GRAY));
        if(negativeEffectText != null) {
            tooltip.add(new StringTextComponent(this.negativeEffectText).mergeStyle(TextFormatting.RED) );
        }
    }
    public String getCardName(){
        return this.cardName;
    }
}
