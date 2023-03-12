package plus.dragons.respiteful.data;

import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.Tags;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

public class RespitefulRecipes {
    
    public static void iceCreamBlock(DataGenContext<Block, ? extends Block> ctx, RegistrateRecipeProvider prov,
                                     NonNullSupplier<? extends Item> iceCreamIn) {
        var iceCream = DataIngredient.items(iceCreamIn);
        var snowBlock = DataIngredient.items(Items.SNOW_BLOCK);
        ShapedRecipeBuilder.shaped(ctx.getEntry())
            .define('#', Items.SNOW_BLOCK)
            .define('i', iceCream)
            .pattern("###").pattern("#i#").pattern("###")
            .unlockedBy("has_snow_block", snowBlock.getCritereon(prov))
            .unlockedBy("has_ice_cream", iceCream.getCritereon(prov))
            .save(prov);
    }
    
    public static void iceCream(DataGenContext<Item, ? extends Item> ctx, RegistrateRecipeProvider prov,
                                NonNullSupplier<? extends Item> ingredientIn) {
        var ingredient = DataIngredient.items(ingredientIn);
        ShapelessRecipeBuilder.shapeless(ctx.getEntry())
            .requires(Items.BOWL)
            .requires(ingredient)
            .requires(ForgeTags.MILK)
            .requires(NeapolitanItems.ICE_CUBES.get())
            .requires(Items.SUGAR)
            .unlockedBy("has_" + prov.safeName(ingredient), ingredient.getCritereon(prov))
            .save(prov);
    }
    
    public static void cake(DataGenContext<Item, ? extends Item> ctx, RegistrateRecipeProvider prov,
                            NonNullSupplier<? extends Item> ingredientIn,
                            NonNullSupplier<? extends Item> sliceIn) {
        var ingredient = DataIngredient.items(ingredientIn);
        var slice = DataIngredient.items(sliceIn);
        var cake = DataIngredient.items(ctx);
        ShapelessRecipeBuilder.shapeless(ctx.getEntry())
            .requires(slice, 7)
            .unlockedBy("has_" + prov.safeName(slice), slice.getCritereon(prov))
            .save(prov, ctx.getId() + "_from_slices");
        ShapedRecipeBuilder.shaped(ctx.getEntry())
            .define('m', ForgeTags.MILK)
            .define('s', Items.SUGAR)
            .define('t', ingredient)
            .define('e', Tags.Items.EGGS)
            .define('w', Items.WHEAT)
            .pattern("msm").pattern("tet").pattern("www")
            .unlockedBy("has_" + prov.safeName(ingredient), ingredient.getCritereon(prov))
            .save(prov);
        CuttingBoardRecipeBuilder.cuttingRecipe(cake, Ingredient.of(ForgeTags.TOOLS_KNIVES), sliceIn.get(), 7)
            .build(prov, new ResourceLocation(ctx.getId().getNamespace(), "cutting/" + ctx.getName()));
    }
    
    public static void snowTopDrink(DataGenContext<Item, ? extends Item> ctx, RegistrateRecipeProvider prov,
                                    NonNullSupplier<? extends Item> ingredientIn) {
        var ingredient = DataIngredient.items(ingredientIn);
        ShapelessRecipeBuilder.shapeless(ctx.getEntry())
            .requires(Items.GLASS_BOTTLE, 3)
            .requires(NeapolitanItems.VANILLA_ICE_CREAM.get())
            .requires(ingredient)
            .requires(ForgeTags.MILK)
            .unlockedBy("has_" + prov.safeName(ingredient), ingredient.getCritereon(prov))
            .save(prov);
    }
    
}
