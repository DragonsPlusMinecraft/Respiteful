package plus.dragons.respiteful.core.extension;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

import java.util.List;
import java.util.function.Supplier;

public interface ModifiableFoodProperties {
    
    void respiteful$setCanAlwaysEat(boolean canAlwaysEat);
    
    void respiteful$setEffects(List<Pair<Supplier<MobEffectInstance>, Float>> effects);
    
    List<Pair<Supplier<MobEffectInstance>, Float>> respiteful$getEffects();
    
    FoodProperties.Builder respiteful$copy();
    
}
