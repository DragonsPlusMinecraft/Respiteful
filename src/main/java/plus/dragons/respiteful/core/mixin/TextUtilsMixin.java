package plus.dragons.respiteful.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.utility.TextUtils;

@Mixin(value = TextUtils.class, remap = false)
public class TextUtilsMixin {
    
    @ModifyExpressionValue(method = "addFoodEffectTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/attributes/AttributeModifier;getAmount()D", remap = true, ordinal = 1))
    private static double fixKnockbackResistanceTooltip(double original, @Local(ordinal = 0) Pair<Attribute, AttributeModifier> pair) {
        return pair.getFirst() == Attributes.KNOCKBACK_RESISTANCE ? original * 10.0D : original;
    }
    
}
