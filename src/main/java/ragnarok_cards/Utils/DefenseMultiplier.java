package ragnarok_cards.Utils;

import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;

import java.util.Map;

import static ragnarok_cards.Config.*;
import static ragnarok_cards.RagnarokCards.MOD_ID;
import static ragnarok_cards.Utils.SafeNbt.getNbtSafe;
import static ragnarok_cards.Utils.VerificateCards.*;

public class DefenseMultiplier {

    static public float ApplyDefenseMultiplier (float damage, PlayerEntity player, DamageSource source,Map<String, Integer> cards){

        LivingEntity attacker = (LivingEntity) source.getTrueSource();
        boolean reduceToMin = false;
        float multiplier = 1;
        int flatDamageIncrease = 0;
        double posX = player.getPosX();
        double posY = player.getPosY();
        double posZ = player.getPosZ();

        if(source.isExplosion()){
            if(cards.containsKey("creeper")) {
                CompoundNBT nbt_persistant = getNbtSafe(player.getPersistentData(),player.PERSISTED_NBT_TAG);
                CompoundNBT nbt = getNbtSafe(nbt_persistant,MOD_ID);
                Long currentTime = player.world.getGameTime();

                if(nbt.contains("creeperCardPlayerExplode") && nbt.getBoolean("creeperCardPlayerExplode")){
                    nbt.putBoolean("creeperCardPlayerExplode",false);
                    reduceToMin = true;
                }
                else if (passCheck("creeper", cards)) {
                    if(!(nbt.contains("creeperCardActivate")) || 1000 < currentTime- nbt.getLong("creeperCardActivate")){
                        reduceToMin = true;
                        nbt.putLong("creeperCardActivate",currentTime);
                        nbt.putBoolean("creeperCardPlayerExplode",true);
                        attacker.world.playSound((PlayerEntity)null, posX, posY, posZ , SoundEvents.ENTITY_CREEPER_HURT, player.getSoundCategory(), 1.0F, 3.0F);

                    }
                }

            }
         }

        if(cards.containsKey("zombie_piglin")) {
            if (passCheck(cards.get("zombie_piglin"), ZOMBIE_PIGLIN_CHANCE_NEG.get())) {

                multiplier *= ZOMBIE_PIGLIN_MULTIPLIER_NEG.get();
                player.onCriticalHit(player);
                player.onCriticalHit(player);
                player.onCriticalHit(player);
                player.onCriticalHit(player);
                player.onCriticalHit(player);
            }
        }

        if(cards.containsKey("cave_spider") && attacker.getCreatureAttribute() == CreatureAttribute.ARTHROPOD) {
            flatDamageIncrease +=  cards.get("cave_spider") * CAVE_SPIDER_DAMAGE_NEG.get();
            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS, CAVE_SPIDER_SLOW_TIME_NEG.get(),4));
        }

        if(cards.containsKey("piglin") && attacker.getCreatureAttribute() == CreatureAttribute.UNDEAD) {
            multiplier *= (1 + PIGLIN_MULTIPLIER_NEG.get() * cards.get("piglin")  );

            if (passCheck(cards.get("piglin"), PIGLIN_CHANCE_NEG.get())) {
                player.addPotionEffect(new EffectInstance(Effects.HUNGER, PIGLIN_TIME_NEG.get()));
            }
        }
        if(cards.containsKey("phantom") && isEnderThing(attacker)){
            multiplier *= 1 + PHANTOM_MULTIPLIER_NEG.get() * cards.get("phantom");
        }

        if(cards.containsKey("ocelot") && player.isInWater()) {
            multiplier *= (1 + OCELOT_MULTIPLIER_NEG.get() * cards.get("ocelot")  );
        }

        if(cards.containsKey("skeleton") && source.isFireDamage() ){
            multiplier *= (1 + SKELETON_MULTIPLIER_NEG.get() * cards.get("skeleton") );
        }

        if(cards.containsKey("zombie") && attacker instanceof LlamaEntity) {
            multiplier *= ZOMBIE_MULTIPLIER_NEG.get() * cards.get("zombie");
        }

        if(cards.containsKey("whiter_skeleton") && source.isExplosion()) {
            multiplier *= (1 + cards.get("whiter_skeleton") * WITHER_SKELETON_MULTIPLIER_NEG.get());
        }
        if(cards.containsKey("wolf")) {
            multiplier *= (1 + cards.get("wolf") * WOLF_MULTIPLIER_NEG.get() );
        }

        if(reduceToMin){
            damage = 1;
        }else{
            damage += flatDamageIncrease;
            damage *= multiplier;
        }
        return damage;
    }
}
