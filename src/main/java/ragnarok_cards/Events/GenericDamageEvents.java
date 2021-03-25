package ragnarok_cards.Events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static ragnarok_cards.Events.VerificateCards.*;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class GenericDamageEvents {

    @SubscribeEvent
    public static void cardZombie(LivingHurtEvent event) {
        Entity attacker = event.getSource().getTrueSource();
        Entity target = event.getEntity();

        if(attacker instanceof PlayerEntity){

            float damage = ApplyDamageMultiplier(event.getAmount(), (PlayerEntity)attacker, (LivingEntity) target);
            event.setAmount(damage);
        }

        if(target instanceof PlayerEntity){

            float damage = ApplyDefenseMultiplier(event.getAmount(), (PlayerEntity)target,event.getSource());
            event.setAmount(damage);

        }else if (target instanceof TameableEntity){
            LivingEntity tamer = ((TameableEntity)target).getOwner();

            if(tamer instanceof PlayerEntity){
                if(SingleCardActivate((PlayerEntity) tamer, "wolf")) {
                    event.setAmount(1);
                }
            }

        }
    }
}


