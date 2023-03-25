package plus.dragons.respiteful.core.mixin;

import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import plus.dragons.respiteful.entries.RespitefulMobEffects;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @WrapWithCondition(method = "addEatEffect", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;addEffect(Lnet/minecraft/world/effect/MobEffectInstance;)Z"))
    private boolean maturityIgnoreHarmfulFoodEffects(LivingEntity entity, MobEffectInstance effect) {
        return !(entity.hasEffect(RespitefulMobEffects.MATURITY.get()) &&
                 effect.getEffect().getCategory() == MobEffectCategory.HARMFUL);
    }

}
