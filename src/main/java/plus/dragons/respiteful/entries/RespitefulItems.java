package plus.dragons.respiteful.entries;

import com.farmersrespite.core.registry.FREffects;
import com.farmersrespite.core.registry.FRItems;
import com.farmersrespite.core.utility.FRFoods;
import com.farmersrespite.data.builder.KettleRecipeBuilder;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.core.util.TradeUtil.BlueprintTrade;
import com.teamabnormals.neapolitan.common.block.FlavoredCakeBlock;
import com.teamabnormals.neapolitan.core.other.tags.NeapolitanItemTags;
import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import com.teamabnormals.neapolitan.core.registry.NeapolitanMobEffects;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.DataIngredient;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.Util;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import plus.dragons.respiteful.Respiteful;
import plus.dragons.respiteful.RespitefulRegistrate;
import plus.dragons.respiteful.common.item.*;
import plus.dragons.respiteful.core.extension.ModifiableFoodProperties;
import plus.dragons.respiteful.data.RespitefulRecipes;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.registry.ModEffects;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static plus.dragons.respiteful.Respiteful.REGISTRATE;

@SuppressWarnings("unused")
public class RespitefulItems {
    
    public static final ItemEntry<TeaItem> MINT_GREEN_TEA = REGISTRATE.item("mint_green_tea",
        prop -> new TeaItem(prop, true))
        .properties(prop -> prop.food(RespitefulFoods.MINT_GREEN_TEA).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> KettleRecipeBuilder
            .kettleRecipe(ctx.getEntry())
            .addIngredient(NeapolitanItems.MINT_LEAVES.get())
            .addIngredient(FRItems.GREEN_TEA_LEAVES.get())
            .build(prov, new ResourceLocation(ctx.getId().getNamespace(), "brewing/" + ctx.getName())))
        .register();
    
    public static final ItemEntry<TeaItem> VANILLA_MILK_TEA = REGISTRATE.item("vanilla_milk_tea",
            prop -> new TeaItem(prop, true))
        .properties(prop -> prop.food(RespitefulFoods.VANILLA_MILK_TEA).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> KettleRecipeBuilder
            .kettleRecipe(ctx.getEntry(), 1, 2400, 0.35F, false, ModItems.MILK_BOTTLE.get())
            .addIngredient(NeapolitanItems.DRIED_VANILLA_PODS.get())
            .addIngredient(FRItems.YELLOW_TEA_LEAVES.get())
            .build(prov, new ResourceLocation(ctx.getId().getNamespace(), "brewing/" + ctx.getName())))
        .register();
    
    public static final ItemEntry<TeaItem> ADZUKI_MILK_TEA = REGISTRATE.item("adzuki_milk_tea",
            prop -> new TeaItem(prop, true))
        .properties(prop -> prop.food(RespitefulFoods.ADZUKI_MILK_TEA).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> KettleRecipeBuilder
            .kettleRecipe(ctx.getEntry(), 1, 2400, 0.35F, false, ModItems.MILK_BOTTLE.get())
            .addIngredient(NeapolitanItems.ROASTED_ADZUKI_BEANS.get())
            .addIngredient(FRItems.BLACK_TEA_LEAVES.get())
            .build(prov, new ResourceLocation(ctx.getId().getNamespace(), "brewing/" + ctx.getName())))
        .register();
    
    public static final ItemEntry<CoffeeItem> MOCHA_COFFEE = REGISTRATE.item("mocha_coffee",
            prop -> new CoffeeItem(prop, true))
        .properties(prop -> prop.food(RespitefulFoods.MOCHA_COFFEE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> KettleRecipeBuilder
            .kettleRecipe(ctx.getEntry(), 1, 2400, 0.35F, false, ModItems.MILK_BOTTLE.get())
            .addIngredient(NeapolitanItems.CHOCOLATE_BAR.get())
            .addIngredient(FRItems.COFFEE_BEANS.get())
            .build(prov, new ResourceLocation(ctx.getId().getNamespace(), "brewing/" + ctx.getName())))
        .register();
    
    public static final ItemEntry<StrengthenEffectDrinkItem> SNOW_TOP_GREEN_TEA = REGISTRATE.item("snow_top_green_tea",
            prop -> new StrengthenEffectDrinkItem(MobEffectCategory.BENEFICIAL, prop))
        .properties(prop -> prop.food(RespitefulFoods.SNOW_TOP_DRINK).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> RespitefulRecipes.snowTopDrink(ctx, prov, FRItems.GREEN_TEA::get))
        .transform(tooltip("Strengthen all beneficial effects and reduce their duration"))
        .register();
    
    public static final ItemEntry<ExtendEffectDrinkItem> SNOW_TOP_YELLOW_TEA = REGISTRATE.item("snow_top_yellow_tea",
            prop -> new ExtendEffectDrinkItem(MobEffectCategory.HARMFUL, prop))
        .properties(prop -> prop.food(RespitefulFoods.SNOW_TOP_DRINK).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> RespitefulRecipes.snowTopDrink(ctx, prov, FRItems.YELLOW_TEA::get))
        .transform(tooltip("Weaken all harmful effects and extend their duration"))
        .register();
    
    public static final ItemEntry<ExtendEffectDrinkItem> SNOW_TOP_BLACK_TEA = REGISTRATE.item("snow_top_black_tea",
            prop -> new ExtendEffectDrinkItem(MobEffectCategory.BENEFICIAL, prop))
        .properties(prop -> prop.food(RespitefulFoods.SNOW_TOP_DRINK).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> RespitefulRecipes.snowTopDrink(ctx, prov, FRItems.BLACK_TEA::get))
        .transform(tooltip("Extend all beneficial effects' duration and weaken them"))
        .register();
    
    public static final ItemEntry<StrengthenEffectDrinkItem> SNOW_TOP_COFFEE = REGISTRATE.item("snow_top_coffee",
            prop -> new StrengthenEffectDrinkItem(MobEffectCategory.HARMFUL, prop))
        .properties(prop -> prop.food(RespitefulFoods.SNOW_TOP_DRINK).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> RespitefulRecipes.snowTopDrink(ctx, prov, FRItems.COFFEE::get))
        .transform(tooltip("Reduce all harmful effects' duration and strengthen them"))
        .register();
    
    public static final ItemEntry<Item> GREEN_TEA_CAKE_SLICE = cakeSlice(RespitefulBlocks.GREEN_TEA_CAKE,
        RespitefulFoods.GREEN_TEA_CAKE_SLICE).register();
    
    public static final ItemEntry<Item> YELLOW_TEA_CAKE_SLICE = cakeSlice(RespitefulBlocks.YELLOW_TEA_CAKE,
        RespitefulFoods.YELLOW_TEA_CAKE_SLICE).register();
    
    public static final ItemEntry<Item> BLACK_TEA_CAKE_SLICE = cakeSlice(RespitefulBlocks.BLACK_TEA_CAKE,
        RespitefulFoods.BLACK_TEA_CAKE_SLICE).register();
    
    public static final ItemEntry<BowlFoodItem> GREEN_TEA_ICE_CREAM = iceCream("green_tea_ice_cream",
        RespitefulFoods.GREEN_TEA_ICE_CREAM)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCream(ctx, prov, FRItems.GREEN_TEA_LEAVES::get))
        .register();
    
    public static final ItemEntry<BowlFoodItem> YELLOW_TEA_ICE_CREAM = iceCream("yellow_tea_ice_cream",
        RespitefulFoods.YELLOW_TEA_ICE_CREAM)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCream(ctx, prov, FRItems.YELLOW_TEA_LEAVES::get))
        .register();
    
    public static final ItemEntry<BowlFoodItem> BLACK_TEA_ICE_CREAM = iceCream("black_tea_ice_cream",
        RespitefulFoods.BLACK_TEA_ICE_CREAM)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCream(ctx, prov, FRItems.BLACK_TEA_LEAVES::get))
        .register();
    
    public static final ItemEntry<BowlFoodItem> COFFEE_ICE_CREAM = iceCream("coffee_ice_cream",
        RespitefulFoods.COFFEE_ICE_CREAM)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCream(ctx, prov, FRItems.COFFEE_BEANS::get))
        .register();
    
    public static final ItemEntry<BowlFoodItem> RESPITEFUL_ICE_CREAM = iceCream("respiteful_ice_cream",
        RespitefulFoods.RESPITEFUL_ICE_CREAM)
        .recipe((ctx, prov) -> {
            var greenTea = DataIngredient.items(FRItems.GREEN_TEA_LEAVES.get());
            var yellowTea = DataIngredient.items(FRItems.YELLOW_TEA_LEAVES.get());
            var blackTea = DataIngredient.items(FRItems.BLACK_TEA_LEAVES.get());
            ShapelessRecipeBuilder.shapeless(ctx.getEntry())
                .requires(Items.BOWL)
                .requires(greenTea)
                .requires(yellowTea)
                .requires(blackTea)
                .requires(ForgeTags.MILK)
                .requires(NeapolitanItems.ICE_CUBES.get())
                .requires(Items.SUGAR)
                .unlockedBy("has_" + prov.safeName(greenTea), greenTea.getCritereon(prov))
                .unlockedBy("has_" + prov.safeName(yellowTea), yellowTea.getCritereon(prov))
                .unlockedBy("has_" + prov.safeName(blackTea), blackTea.getCritereon(prov))
                .save(prov);
        })
        .register();
    
    private static <T extends Item> NonNullUnaryOperator<ItemBuilder<T, RespitefulRegistrate>> tooltip(String tooltip) {
        return builder -> {
            ResourceLocation id = new ResourceLocation(builder.getOwner().getModid(), builder.getName());
            return builder.addMiscData(ProviderType.LANG, prov -> prov.add(Util.makeDescriptionId("tooltip", id), tooltip));
        };
    }
    
    private static ItemBuilder<Item, RespitefulRegistrate> cakeSlice(BlockEntry<FlavoredCakeBlock> cake, FoodProperties food) {
        return REGISTRATE.item(cake.getId().getPath() + "_slice",
                prop -> (Item) new TargetedItemCategoryFillingItem(cake, prop))
            .properties(prop -> prop.food(food));
    }
    
    private static ItemBuilder<BowlFoodItem, RespitefulRegistrate> iceCream(String name, FoodProperties food) {
        return REGISTRATE.item(name, BowlFoodItem::new)
            .properties(prop -> prop.food(food).stacksTo(1).craftRemainder(Items.BOWL))
            .tag(NeapolitanItemTags.ICE_CREAM);
    }
    
    public static void register() {}
    
    
    public static class RespitefulFoods {
        public static final FoodProperties MINT_GREEN_TEA = new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 1200), 1F)
            .effect(() -> new MobEffectInstance(NeapolitanMobEffects.BERSERKING.get(), 1200), 1F)
            .build();
        public static final FoodProperties VANILLA_MILK_TEA = new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 1200), 1F)
            .effect(() -> new MobEffectInstance(NeapolitanMobEffects.VANILLA_SCENT.get(), 1200), 1F)
            .build();
        public static final FoodProperties ADZUKI_MILK_TEA = new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 1200), 1F)
            .effect(() -> new MobEffectInstance(NeapolitanMobEffects.HARMONY.get(), 1200), 1F)
            .build();
        public static final FoodProperties MOCHA_COFFEE = new FoodProperties.Builder()
            .effect(() -> new MobEffectInstance(NeapolitanMobEffects.SUGAR_RUSH.get(), 3600), 1F)
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 6000), 1F)
            .build();
        public static final FoodProperties SNOW_TOP_DRINK = new FoodProperties.Builder()
            .nutrition(3).saturationMod(0.6F).build();
        public static final FoodProperties GREEN_TEA_CAKE = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.1F).fast()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 400), 1F)
            .build();
        public static final FoodProperties GREEN_TEA_CAKE_SLICE = copy(GREEN_TEA_CAKE)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400), 1F)
            .build();
        public static final FoodProperties YELLOW_TEA_CAKE = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.1F).fast()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 400), 1F)
            .build();
        public static final FoodProperties YELLOW_TEA_CAKE_SLICE = copy(YELLOW_TEA_CAKE)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400), 1F)
            .build();
        public static final FoodProperties BLACK_TEA_CAKE = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.1F).fast()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 400), 1F)
            .build();
        public static final FoodProperties BLACK_TEA_CAKE_SLICE = copy(BLACK_TEA_CAKE)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400), 1F)
            .build();
        public static final FoodProperties GREEN_TEA_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2), 1F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 600, 1), 1F)
            .build();
        public static final FoodProperties YELLOW_TEA_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2), 1F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 600, 1), 1F)
            .build();
        public static final FoodProperties BLACK_TEA_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2), 1F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 600, 1), 1F)
            .build();
        public static final FoodProperties COFFEE_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2), 1F)
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 1200, 2), 1F)
            .build();
        public static final FoodProperties RESPITEFUL_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(12).saturationMod(0.4F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2), 1F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 900), 1F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 900), 1F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 900), 1F)
            .build();
        
        private static FoodProperties.Builder copy(FoodProperties food) {
            return ((ModifiableFoodProperties)food).respiteful$copy();
        }
        
        private static final List<FoodProperties> FOODS_CAN_ALWAYS_EAT = ImmutableList.<FoodProperties>builder()
            .add(MINT_GREEN_TEA)
            .add(VANILLA_MILK_TEA)
            .add(ADZUKI_MILK_TEA)
            .add(MOCHA_COFFEE)
            .add(FRFoods.GREEN_TEA)
            .add(FRFoods.YELLOW_TEA)
            .add(FRFoods.BLACK_TEA)
            .add(FRFoods.ROSE_HIP_TEA)
            .add(FRFoods.DANDELION_TEA)
            .add(FRFoods.PURULENT_TEA)
            .add(FRFoods.COFFEE)
            .add(FRFoods.LONG_GREEN_TEA)
            .add(FRFoods.LONG_YELLOW_TEA)
            .add(FRFoods.LONG_BLACK_TEA)
            .add(FRFoods.LONG_ROSE_HIP_TEA)
            .add(FRFoods.LONG_DANDELION_TEA)
            .add(FRFoods.LONG_COFFEE)
            .add(FRFoods.LONG_APPLE_CIDER)
            .add(FRFoods.STRONG_GREEN_TEA)
            .add(FRFoods.STRONG_YELLOW_TEA)
            .add(FRFoods.STRONG_BLACK_TEA)
            .add(FRFoods.STRONG_ROSE_HIP_TEA)
            .add(FRFoods.STRONG_PURULENT_TEA)
            .add(FRFoods.STRONG_COFFEE)
            .add(FRFoods.STRONG_APPLE_CIDER)
            .build();
    
        /**
         * For setting Farmer's Respite drinks drinkable even when the player if full.
         * @param canAlwaysEat Whether this method is called for setting the original effects back.
         */
        public static void setDrinksCanAlwaysEat(boolean canAlwaysEat) {
            for (var food : FOODS_CAN_ALWAYS_EAT) {
                ((ModifiableFoodProperties)food).respiteful$setCanAlwaysEat(canAlwaysEat);
            }
        }
        
        private static final Map<
            FoodProperties,
            Pair<List<Pair<Supplier<MobEffectInstance>, Float>>, List<Pair<Supplier<MobEffectInstance>, Float>>>
        > FOOD_EFFECTS_REPLACEMENTS = new HashMap<>();
        static {
            addReplacement(FRFoods.GREEN_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 1800), 1F));
            addReplacement(FRFoods.LONG_GREEN_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 3600), 1F));
            addReplacement(FRFoods.STRONG_GREEN_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 1200, 1), 1F));
            addReplacement(FRFoods.GREEN_TEA_COOKIE,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 100), 1F));
            addReplacement(FRFoods.YELLOW_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 1800), 1F));
            addReplacement(FRFoods.LONG_YELLOW_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 3600), 1F));
            addReplacement(FRFoods.STRONG_YELLOW_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 1200, 1), 1F));
            addReplacement(FRFoods.TEA_CURRY,
                new Pair<>(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1F),
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 600), 1F));
            addReplacement(FRFoods.BLACK_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 1800), 1F));
            addReplacement(FRFoods.LONG_BLACK_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 3600), 1F));
            addReplacement(FRFoods.STRONG_BLACK_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 1200, 1), 1F));
            addReplacement(FRFoods.BLACK_COD,
                new Pair<>(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1F),
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 600), 1F));
        }
        
        @SafeVarargs
        private static void addReplacement(FoodProperties food, Pair<Supplier<MobEffectInstance>, Float>... effects) {
            FOOD_EFFECTS_REPLACEMENTS.put(food, new Pair<>(
                ((ModifiableFoodProperties)(food)).respiteful$getEffects(),
                Lists.newArrayList(effects)));
        }
    
        /**
         * For replacing Farmer's Respite food effects with Respiteful's effects.
         * @param replace Whether replace the effects or setting the original effects back.
         */
        public static void replaceFoodEffects(boolean replace) {
            FOOD_EFFECTS_REPLACEMENTS.forEach((food, pair) -> ((ModifiableFoodProperties)food)
                .respiteful$setEffects(replace ? pair.getSecond() : pair.getFirst()));
        }
        
    }
    
    
    @EventBusSubscriber(modid = Respiteful.ID)
    public static class ForgeEventHandler {
    
        @SubscribeEvent
        public static void onWandererTrades(WandererTradesEvent event) {
            if (Configuration.FARMERS_BUY_FD_CROPS.get()) {
                List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
                trades.add(new BlueprintTrade(RespitefulBlocks.GREEN_TEA_CAKE.get().asItem(), 1, 13, 12, 30));
                trades.add(new BlueprintTrade(RespitefulBlocks.YELLOW_TEA_CAKE.get().asItem(), 1, 13, 12, 30));
                trades.add(new BlueprintTrade(RespitefulBlocks.BLACK_TEA_CAKE.get().asItem(), 1, 13, 12, 30));
            }
        }
        
    }
    
}
