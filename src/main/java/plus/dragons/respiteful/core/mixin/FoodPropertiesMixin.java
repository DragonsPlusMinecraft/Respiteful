package plus.dragons.respiteful.core.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import plus.dragons.respiteful.core.extension.ModifiableFoodProperties;

import java.util.List;
import java.util.function.Supplier;

@Mixin(FoodProperties.class)
public class FoodPropertiesMixin implements ModifiableFoodProperties {
    
    @Shadow
    @Final
    @Mutable
    private List<Pair<Supplier<MobEffectInstance>, Float>> effects;
    
    @Shadow @Final private int nutrition;
    
    @Shadow @Final private float saturationModifier;
    
    @Shadow @Final private boolean isMeat;
    
    @Shadow
    @Final
    @Mutable
    private boolean canAlwaysEat;
    
    @Shadow @Final private boolean fastFood;
    
    @Override
    public void respiteful$setCanAlwaysEat(boolean canAlwaysEat) {
        this.canAlwaysEat = canAlwaysEat;
    }
    
    @Override
    public void respiteful$setEffects(List<Pair<Supplier<MobEffectInstance>, Float>> effects) {
        this.effects = effects;
    }
    
    @Override
    public List<Pair<Supplier<MobEffectInstance>, Float>> respiteful$getEffects() {
        return this.effects;
    }
    
    @Override
    public FoodProperties.Builder respiteful$copy() {
        var builder = new FoodProperties.Builder()
            .nutrition(this.nutrition)
            .saturationMod(this.saturationModifier);
        if (this.isMeat) builder.meat();
        if (this.canAlwaysEat) builder.alwaysEat();
        if (this.fastFood) builder.fast();
        for (var effect : this.effects)
            builder.effect(effect.getFirst(), effect.getSecond());
        return builder;
    }
    
}
