package plus.dragons.respiteful.integration.create;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;
import plus.dragons.respiteful.entries.RespitefulFluids;
import plus.dragons.respiteful.entries.RespitefulItems;
import umpaz.farmersrespite.data.builder.KettlePouringRecipeBuilder;

import java.util.function.Consumer;

public final class CreateCompatRecipe {
    private CreateCompatRecipe() {
    }

    public static void buildRecipes(Consumer<FinishedRecipe> consumer) {
        KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, RespitefulFluids.ADZUKI_MILK_TEA.getSource(), 250, RespitefulItems.ADZUKI_MILK_TEA.get(), consumer);
        KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, RespitefulFluids.MINT_GREEN_TEA.getSource(), 250, RespitefulItems.MINT_GREEN_TEA.get(), consumer);
        KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, RespitefulFluids.MOCHA_COFFEE.getSource(), 250, RespitefulItems.MOCHA_COFFEE.get(), consumer);
        KettlePouringRecipeBuilder.kettlePouringRecipe(Items.GLASS_BOTTLE, RespitefulFluids.VANILLA_MILK_TEA.getSource(), 250, RespitefulItems.VANILLA_MILK_TEA.get(), consumer);
    }

}
