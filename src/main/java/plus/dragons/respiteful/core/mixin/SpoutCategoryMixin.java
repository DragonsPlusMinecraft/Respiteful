package plus.dragons.respiteful.core.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.fluids.FluidStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

// TODO a PR to fix this on Create's side
// In Create:
@Mixin(targets = "com.simibubi.create.compat.jei.category.SpoutCategory", remap = false)
public class SpoutCategoryMixin {
    @WrapOperation(
            method = "consumeRecipes", remap = false,
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraftforge/fluids/FluidStack;isEmpty()Z", ordinal = 0, remap = false))
    private static boolean patch(FluidStack instance, Operation<Boolean> original, @Local(name = "stack") ItemStack stack) {
        if (stack.is(Items.BUCKET)){
            if(instance.getFluid().getBucket() == Items.AIR) return false;
        }
        return original.call(instance);
    }
}
