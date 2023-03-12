package plus.dragons.respiteful.common.item;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import plus.dragons.respiteful.RespitefulConfig;
import plus.dragons.respiteful.core.mixin.MobEffectInstanceAccessor;

public class StrengthenEffectDrinkItem extends SnowTopDrinkItem {
    public StrengthenEffectDrinkItem(MobEffectCategory category, Properties properties) {
        super(category, properties);
    }
    
    @Override
    protected boolean canTransformEffect(MobEffectInstance effect) {
        return effect.getDuration() >= RespitefulConfig.COMMON.snowTopDrinkEffectMinDuration.get();
    }
    
    @Override
    protected MobEffectInstance transformEffect(MobEffectInstance effect) {
        var amplifier = effect.getAmplifier();
        return new MobEffectInstance(effect.getEffect(),
            effect.getDuration() / 2, amplifier + 1,
            effect.isAmbient(), effect.isVisible(), effect.showIcon(),
            ((MobEffectInstanceAccessor) effect).getHiddenEffect(), effect.getEffect().createFactorData());
    }
    
}
