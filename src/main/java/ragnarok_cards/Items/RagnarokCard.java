package ragnarok_cards.Items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class RagnarokCard extends Item {
    String cardName;
    public RagnarokCard(String cardName) {
        super(new Item.Properties().maxStackSize(1).group(ItemGroup.COMBAT));
        this.cardName = cardName;
        setRegistryName("ragnarok_cards",cardName + "_card");
    }

    public String getCardName(){
        return this.cardName;
    }
}
