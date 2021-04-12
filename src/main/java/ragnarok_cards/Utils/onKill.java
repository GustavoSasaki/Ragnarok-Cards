package ragnarok_cards.Utils;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.SnowGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.util.DamageSource;

import java.util.Map;

public class onKill {
    static public void ApplyOnKillEffects (DamageSource source, LivingEntity target, Map<String, Integer> cards){
        PlayerEntity player = (PlayerEntity) source.getTrueSource();
        System.out.println("entrou 1");
        if(cards.containsKey("villager") && player.getHeldItemMainhand().getItem() instanceof HoeItem) {
            System.out.println("entrou 2");
            LivingEntity snowGolem = EntityType.SNOW_GOLEM.create(player.world);
            snowGolem.setLocationAndAngles(target.getPosX(), target.getPosY(), target.getPosZ(), target.rotationYaw, 0.0F);
            System.out.println(player.world.isRemote());
            player.world.addEntity(snowGolem);
        }
    }
}
