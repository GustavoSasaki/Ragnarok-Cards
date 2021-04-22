package ragnarok_cards.Utils;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;

public class SafeNbt {
    public static CompoundNBT getNbtSafe(CompoundNBT nbt_father, String key){

        CompoundNBT nbt_child;
        if (nbt_father.contains(key)){
            nbt_child = nbt_father.getCompound(key);
        } else {
            nbt_child = new CompoundNBT();
            nbt_father.put(key, nbt_child);
        }

        return nbt_child;
    }
}
