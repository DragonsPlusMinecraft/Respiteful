package plus.dragons.respiteful.integration.create;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import plus.dragons.respiteful.entries.RespitefulFluids;
import plus.dragons.respiteful.entries.RespitefulItems;
import umpaz.farmersrespite.data.builder.KettlePouringRecipeBuilder;

import java.util.function.Consumer;

public class CreateCompatRecipe extends RecipeProvider {
    public CreateCompatRecipe(PackOutput output) {
        super(output);
    }

    private static void pouringRecipes(Consumer<FinishedRecipe> consumer) {
        KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, RespitefulFluids.ADZUKI_MILK_TEA.get(), 250, RespitefulItems.ADZUKI_MILK_TEA.get(), consumer);
        KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, RespitefulFluids.MINT_GREEN_TEA.get(), 250, RespitefulItems.MINT_GREEN_TEA.get(), consumer);
        KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, RespitefulFluids.MOCHA_COFFEE.get(), 250, RespitefulItems.MOCHA_COFFEE.get(), consumer);
        KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, RespitefulFluids.VANILLA_MILK_TEA.get(), 250, RespitefulItems.VANILLA_MILK_TEA.get(), consumer);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        pouringRecipes(consumer);
    }

    public static class Data{

        @SubscribeEvent
        public static void gatherData(GatherDataEvent event) {
            PackOutput output = event.getGenerator().getPackOutput();
            event.getGenerator().addProvider(event.includeServer(), new CreateCompatRecipe(output));
        }
    }
}
