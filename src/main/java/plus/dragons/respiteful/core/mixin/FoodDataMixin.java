package plus.dragons.respiteful.core.mixin;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import plus.dragons.respiteful.entries.RespitefulMobEffects;

@Mixin(value = FoodData.class, priority = 900)
public class FoodDataMixin {
    
    @Unique
    private int respiteful$maturity = 0;
    
    @ModifyVariable(method = "eat(IF)V", at = @At("HEAD"), argsOnly = true)
    private int respiteful$addMaturityNutrition(int nutrition) {
        return nutrition == 0 ? 0 : nutrition + respiteful$maturity;
    }
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void respiteful$updateMaturity(Player player, CallbackInfo ci) {
        this.respiteful$maturity = player.hasEffect(RespitefulMobEffects.MATURITY.get())
            ? player.getEffect(RespitefulMobEffects.MATURITY.get()).getAmplifier() + 1 : 0;
    }
    
}
