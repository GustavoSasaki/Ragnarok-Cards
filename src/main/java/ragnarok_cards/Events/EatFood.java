package ragnarok_cards.Events;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import ragnarok_cards.Utils.SecundaryEffectsAttack;

import java.util.ArrayList;
import java.util.Random;

import static ragnarok_cards.Utils.VerificateCards.SingleCardActivate;

@Mod.EventBusSubscriber()
public class EatFood {
    private interface ApplyEffect{
        public EffectInstance apply();
    }
    static protected ArrayList<EatFood.ApplyEffect> applySheepRandomEffect = new ArrayList<EatFood.ApplyEffect>();
    static{
        applySheepRandomEffect.add( () -> new EffectInstance(Effects.SPEED, 200,2));
        applySheepRandomEffect.add( () -> new EffectInstance(Effects.STRENGTH, 200,2));
        applySheepRandomEffect.add( () -> new EffectInstance(Effects.REGENERATION, 200,2));
        applySheepRandomEffect.add( () -> new EffectInstance(Effects.RESISTANCE, 200,2));
        applySheepRandomEffect.add( () -> new EffectInstance(Effects.FIRE_RESISTANCE, 200,2));
        applySheepRandomEffect.add( () -> new EffectInstance(Effects.HASTE, 200,2));
        applySheepRandomEffect.add( () -> new EffectInstance(Effects.WATER_BREATHING, 200,2));
        applySheepRandomEffect.add( () -> new EffectInstance(Effects.NIGHT_VISION, 600,2));
        applySheepRandomEffect.add( () -> new EffectInstance(Effects.LUCK, 600,2));
    }


    @SubscribeEvent
    public static void finishEat(LivingEntityUseItemEvent.Finish event) {
        if(event.getEntity().world.isRemote() || !(event.getEntity() instanceof PlayerEntity)){
            return;
        }
        PlayerEntity player = (PlayerEntity) event.getEntity();
        Item item = event.getItem().getItem();

        if(item.isFood() && item.getFood().isMeat() ){
            //when pass, give negative effect
            if(SingleCardActivate (player, "sheep")){
                player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 60));
                player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 1000,2));
                player.addPotionEffect(new EffectInstance(Effects.POISON, 1000));
                player.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 250));
                ((ServerWorld) player.world).playSound((PlayerEntity)null, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.AMBIENT_CAVE, player.getSoundCategory(), 1.0F, 1.0F);

            }else {
                int randomEffects = player.world.rand.nextInt(applySheepRandomEffect.size());
                EffectInstance newEffect =  applySheepRandomEffect.get(randomEffects).apply();
                player.addPotionEffect(newEffect);

            }
        }
    }

}
