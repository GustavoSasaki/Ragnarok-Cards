package ragnarok_cards.Utils;

import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import ragnarok_cards.Events.EatFood;

import java.util.ArrayList;

public class IDtoEffect {
    public interface ApplyEffect{
        public EffectInstance apply(int t,int p);
    }
    public static ArrayList<IDtoEffect.ApplyEffect> toEffect = new ArrayList<IDtoEffect.ApplyEffect>();
    static{
        toEffect.add( (t,p) -> new EffectInstance(Effects.SPEED, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.SPEED, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.SLOWNESS, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.HASTE, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.MINING_FATIGUE, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.STRENGTH, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.INSTANT_HEALTH, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.INSTANT_DAMAGE, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.JUMP_BOOST, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.NAUSEA, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.REGENERATION, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.RESISTANCE, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.FIRE_RESISTANCE, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.WATER_BREATHING, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.INVISIBILITY, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.BLINDNESS, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.NIGHT_VISION, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.HUNGER, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.WEAKNESS, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.POISON, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.WITHER, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.HEALTH_BOOST, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.ABSORPTION, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.SATURATION, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.GLOWING, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.LEVITATION, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.LUCK, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.UNLUCK, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.SLOW_FALLING, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.CONDUIT_POWER, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.DOLPHINS_GRACE, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.BAD_OMEN, t,p));
        toEffect.add( (t,p) -> new EffectInstance(Effects.HERO_OF_THE_VILLAGE, t,p));
    }
}
