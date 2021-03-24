package ragnarok_cards.Events;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import ragnarok_cards.Items.RagnarokCard;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VerificateCards {
    static protected final Random rand = new Random();
    static protected Map<String, Integer> cardNameToChance = new HashMap<String, Integer>();
    static{
        cardNameToChance.put("snowman", 20);
    }

    static public boolean cardActivate (PlayerEntity player, String cardName){
        int quantity = HowManyCards(player, cardName);

        return (rand.nextInt(100 ) < quantity * cardNameToChance.get(cardName) );
    }

    static public float GetCardDamageMultiplier (PlayerEntity player, LivingEntity target){
        Map<String, Integer> cards = HowManyCards(player);

        float multiplier = 1;

        if(target instanceof ZombieEntity){
            multiplier *= 1 + 0.2 * cards.get("zombie");
            System.out.println(multiplier);
        }
        return multiplier;
    }

    static public int HowManyCards(PlayerEntity player, String cardName){
        int quantity = 0;

        //go through upper inventory row
        for(int i=9;i<18;i++){
            Item item = player.inventory.mainInventory.get(i).getItem();

            if(item instanceof RagnarokCard && ((RagnarokCard)item).getCardName().equals(cardName)){
                quantity += 1;
            }
        }
        return quantity;
    }

    static public Map<String, Integer> HowManyCards(PlayerEntity player){
        Map<String, Integer> quantity = new HashMap<String, Integer>();

        //go through upper inventory row
        for(int i=9;i<18;i++){
            Item item = player.inventory.mainInventory.get(i).getItem();
            if(!(item instanceof RagnarokCard)){
                continue;
            }

            String cardName = ((RagnarokCard)item).getCardName();
            if(quantity.containsKey(cardName)){
                quantity.put(cardName, quantity.get(cardName) + 1);
            }else{
                quantity.put(cardName,1);
            }
        }
        return quantity;
    }
}
