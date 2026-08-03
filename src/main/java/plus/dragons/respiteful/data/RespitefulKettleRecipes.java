package plus.dragons.respiteful.data;

import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.fml.ModList;
import plus.dragons.respiteful.entries.RespitefulFluids;
import plus.dragons.respiteful.integration.create.CreateCompatRecipe;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.data.builder.KettleRecipeBuilder;

import java.util.Objects;
import java.util.function.Consumer;

/** Server data for the four Farmer's Respite kettle brews. */
public final class RespitefulKettleRecipes extends RecipeProvider {
    public RespitefulKettleRecipes(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        KettleRecipeBuilder.kettleRecipe(
                new FluidStack(Fluids.WATER, 1000),
                new FluidStack(RespitefulFluids.MINT_GREEN_TEA.getSource(), 1000),
                2400,
                0.35F)
            .addIngredient(NeapolitanItems.MINT_LEAVES.get())
            .addIngredient(FRItems.GREEN_TEA_LEAVES.get())
            .build(consumer, brewingId(RespitefulFluids.MINT_GREEN_TEA.getSource()));

        KettleRecipeBuilder.kettleRecipe(
                new FluidStack(ForgeMod.MILK.get(), 1000),
                new FluidStack(RespitefulFluids.VANILLA_MILK_TEA.getSource(), 1000),
                2400,
                0.35F)
            .addIngredient(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .addIngredient(FRItems.YELLOW_TEA_LEAVES.get())
            .build(consumer, brewingId(RespitefulFluids.VANILLA_MILK_TEA.getSource()));

        KettleRecipeBuilder.kettleRecipe(
                new FluidStack(ForgeMod.MILK.get(), 1000),
                new FluidStack(RespitefulFluids.ADZUKI_MILK_TEA.getSource(), 1000),
                2400,
                0.35F)
            .addIngredient(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .addIngredient(FRItems.BLACK_TEA_LEAVES.get())
            .build(consumer, brewingId(RespitefulFluids.ADZUKI_MILK_TEA.getSource()));

        KettleRecipeBuilder.kettleRecipe(
                new FluidStack(ForgeMod.MILK.get(), 1000),
                new FluidStack(RespitefulFluids.MOCHA_COFFEE.getSource(), 1000),
                2400,
                0.35F)
            .addIngredient(NeapolitanItems.CHOCOLATE_BAR.get())
            .addIngredient(FRItems.COFFEE_BEANS.get())
            .build(consumer, brewingId(RespitefulFluids.MOCHA_COFFEE.getSource()));

        if (ModList.get().isLoaded("create")) {
            CreateCompatRecipe.buildRecipes(consumer);
        }
    }

    private static net.minecraft.resources.ResourceLocation brewingId(net.minecraft.world.level.material.Fluid fluid) {
        return Objects.requireNonNull(ForgeRegistries.FLUIDS.getKey(fluid)).withPrefix("brewing/");
    }
}
