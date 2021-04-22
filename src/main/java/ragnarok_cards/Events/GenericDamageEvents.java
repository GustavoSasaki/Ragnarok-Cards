package ragnarok_cards.Events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

import static ragnarok_cards.Utils.DamageMultiplier.ApplyDamageMultiplier;
import static ragnarok_cards.Utils.DefenseMultiplier.ApplyDefenseMultiplier;
import static ragnarok_cards.Utils.VerificateCards.*;

@Mod.EventBusSubscriber()
public class GenericDamageEvents {

    @SubscribeEvent
    public static void ModHurtEvent(LivingHurtEvent event) {
        if(event.getEntity().world.isRemote()){
            return;
        }

        Entity attacker = event.getSource().getTrueSource();
        Entity target = event.getEntity();


        if(attacker instanceof PlayerEntity){
            float damage = ApplyAttackCards(event.getAmount(), event.getSource(), (LivingEntity) target);
            event.setAmount(damage);
        }else if (attacker instanceof TameableEntity ){
            LivingEntity tamer = ((TameableEntity)attacker).getOwner();

            if(tamer instanceof PlayerEntity){
                event.setAmount( event.getAmount() + 2* HowManyCards((PlayerEntity) tamer,"wolf"));
            }
        }

        if(target instanceof PlayerEntity){

            float damage = ApplyDefenseCards(event.getAmount(), (PlayerEntity)target,event.getSource());
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


