package plus.dragons.respiteful;

import com.teamabnormals.blueprint.common.effect.BlueprintMobEffect;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import plus.dragons.respiteful.entries.builders.MobEffectBuilder;

public class RespitefulRegistrate extends AbstractRegistrate<RespitefulRegistrate> {
    
    protected RespitefulRegistrate(String modid) {
        super(modid);
    }
    
    @Override
    protected RespitefulRegistrate registerEventListeners(IEventBus bus) {
        return super.registerEventListeners(bus);
    }
    
    public MobEffectBuilder<MobEffect, RespitefulRegistrate> mobEffect(String name) {
        return mobEffect(name, BlueprintMobEffect::new);
    }
    
    public <T extends MobEffect> MobEffectBuilder<T, RespitefulRegistrate> mobEffect(String name, NonNullBiFunction<MobEffectCategory, Integer, T> factory) {
        return mobEffect(this, name, factory);
    }
    
    public <T extends MobEffect, P> MobEffectBuilder<T, P> mobEffect(P parent, String name, NonNullBiFunction<MobEffectCategory, Integer, T> factory) {
        return entry(name, callback -> MobEffectBuilder.create(this, parent, name, callback, factory));
    }
    
}
