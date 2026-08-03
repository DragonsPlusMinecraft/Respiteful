package plus.dragons.respiteful.entries;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.teamabnormals.blueprint.core.util.TradeUtil.BlueprintTrade;
import com.teamabnormals.neapolitan.common.block.FlavoredCakeBlock;
import com.teamabnormals.neapolitan.common.item.IceCreamItem;
import com.teamabnormals.neapolitan.common.item.MilkshakeItem;
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
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import plus.dragons.respiteful.Respiteful;
import plus.dragons.respiteful.RespitefulRegistrate;
import plus.dragons.respiteful.common.item.EffectExtendingDrinkItem;
import plus.dragons.respiteful.common.item.EffectStrengtheningDrinkItem;
import plus.dragons.respiteful.core.mixin.FoodPropertiesAccessor;
import plus.dragons.respiteful.data.RespitefulRecipes;
import umpaz.farmersrespite.common.FRFoodValues;
import umpaz.farmersrespite.common.registry.FREffects;
import umpaz.farmersrespite.common.registry.FRItems;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.registry.ModEffects;
import vectorwing.farmersdelight.common.tag.CommonTags;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static plus.dragons.respiteful.Respiteful.REGISTRATE;

public class RespitefulItems {
    public static final ItemEntry<DrinkableItem> MINT_GREEN_TEA = REGISTRATE.item("mint_green_tea", prop -> new DrinkableItem(prop, true))
        .properties(prop -> prop.food(Foods.MINT_GREEN_TEA).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .register();

    public static final ItemEntry<DrinkableItem> VANILLA_MILK_TEA = REGISTRATE.item("vanilla_milk_tea", prop -> new DrinkableItem(prop, true))
        .properties(prop -> prop.food(Foods.VANILLA_MILK_TEA).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .register();

    public static final ItemEntry<DrinkableItem> ADZUKI_MILK_TEA = REGISTRATE.item("adzuki_milk_tea", prop -> new DrinkableItem(prop, true))
        .properties(prop -> prop.food(Foods.ADZUKI_MILK_TEA).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .register();

    public static final ItemEntry<DrinkableItem> MOCHA_COFFEE = REGISTRATE.item("mocha_coffee", prop -> new DrinkableItem(prop, true))
        .properties(prop -> prop.food(Foods.MOCHA_COFFEE).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .register();

    public static final ItemEntry<EffectStrengtheningDrinkItem> SNOW_TOP_GREEN_TEA = REGISTRATE.item("snow_top_green_tea",
            prop -> new EffectStrengtheningDrinkItem(MobEffectCategory.BENEFICIAL, prop))
        .properties(prop -> prop.food(Foods.SNOW_TOP_DRINK).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> RespitefulRecipes.snowTopDrink(ctx, prov, FRItems.GREEN_TEA::get))
        .transform(tooltip("Strengthen all beneficial effects and reduce their duration"))
        .register();

    public static final ItemEntry<EffectExtendingDrinkItem> SNOW_TOP_YELLOW_TEA = REGISTRATE.item("snow_top_yellow_tea",
            prop -> new EffectExtendingDrinkItem(MobEffectCategory.HARMFUL, prop))
        .properties(prop -> prop.food(Foods.SNOW_TOP_DRINK).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> RespitefulRecipes.snowTopDrink(ctx, prov, FRItems.YELLOW_TEA::get))
        .transform(tooltip("Weaken all harmful effects and extend their duration"))
        .register();

    public static final ItemEntry<EffectExtendingDrinkItem> SNOW_TOP_BLACK_TEA = REGISTRATE.item("snow_top_black_tea",
            prop -> new EffectExtendingDrinkItem(MobEffectCategory.BENEFICIAL, prop))
        .properties(prop -> prop.food(Foods.SNOW_TOP_DRINK).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> RespitefulRecipes.snowTopDrink(ctx, prov, FRItems.BLACK_TEA::get))
        .transform(tooltip("Extend all beneficial effects' duration and weaken them"))
        .register();

    public static final ItemEntry<EffectStrengtheningDrinkItem> SNOW_TOP_COFFEE = REGISTRATE.item("snow_top_coffee",
            prop -> new EffectStrengtheningDrinkItem(MobEffectCategory.HARMFUL, prop))
        .properties(prop -> prop.food(Foods.SNOW_TOP_DRINK).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
        .recipe((ctx, prov) -> RespitefulRecipes.snowTopDrink(ctx, prov, FRItems.COFFEE::get))
        .transform(tooltip("Reduce all harmful effects' duration and strengthen them"))
        .register();

    public static final ItemEntry<ConsumableItem> GREEN_TEA_CAKE_SLICE = cakeSlice(RespitefulBlocks.GREEN_TEA_CAKE,
        Foods.GREEN_TEA_CAKE_SLICE).lang("Slice of Green Tea Cake").register();

    public static final ItemEntry<ConsumableItem> YELLOW_TEA_CAKE_SLICE = cakeSlice(RespitefulBlocks.YELLOW_TEA_CAKE,
        Foods.YELLOW_TEA_CAKE_SLICE).lang("Slice of Yellow Tea Cake").register();

    public static final ItemEntry<ConsumableItem> BLACK_TEA_CAKE_SLICE = cakeSlice(RespitefulBlocks.BLACK_TEA_CAKE,
        Foods.BLACK_TEA_CAKE_SLICE).lang("Slice of Black Tea Cake").register();

    public static final ItemEntry<IceCreamItem> GREEN_TEA_ICE_CREAM = iceCream("green_tea_ice_cream",
        Foods.GREEN_TEA_ICE_CREAM)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCream(ctx, prov, FRItems.GREEN_TEA_LEAVES::get))
        .register();

    public static final ItemEntry<IceCreamItem> YELLOW_TEA_ICE_CREAM = iceCream("yellow_tea_ice_cream",
        Foods.YELLOW_TEA_ICE_CREAM)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCream(ctx, prov, FRItems.YELLOW_TEA_LEAVES::get))
        .register();

    public static final ItemEntry<IceCreamItem> BLACK_TEA_ICE_CREAM = iceCream("black_tea_ice_cream",
        Foods.BLACK_TEA_ICE_CREAM)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCream(ctx, prov, FRItems.BLACK_TEA_LEAVES::get))
        .register();

    public static final ItemEntry<IceCreamItem> COFFEE_ICE_CREAM = iceCream("coffee_ice_cream",
        Foods.COFFEE_ICE_CREAM)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCream(ctx, prov, FRItems.COFFEE_BEANS::get))
        .register();

    public static final ItemEntry<IceCreamItem> RESPITEFUL_ICE_CREAM = iceCream("respiteful_ice_cream",
        Foods.RESPITEFUL_ICE_CREAM)
        .recipe((ctx, prov) -> {
            var greenTea = DataIngredient.items(FRItems.GREEN_TEA_LEAVES.get());
            var yellowTea = DataIngredient.items(FRItems.YELLOW_TEA_LEAVES.get());
            var blackTea = DataIngredient.items(FRItems.BLACK_TEA_LEAVES.get());
            ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ctx.getEntry())
                .requires(Items.BOWL)
                .requires(greenTea)
                .requires(yellowTea)
                .requires(blackTea)
                .requires(CommonTags.Items.MILK)
                .requires(NeapolitanItems.ICE_CUBES.get())
                .requires(Items.SUGAR)
                .unlockedBy("has_" + prov.safeName(greenTea), greenTea.getCritereon(prov))
                .unlockedBy("has_" + prov.safeName(yellowTea), yellowTea.getCritereon(prov))
                .unlockedBy("has_" + prov.safeName(blackTea), blackTea.getCritereon(prov))
                .save(prov);
        })
        .register();

    public static final ItemEntry<MilkshakeItem> GREEN_TEA_MILKSHAKE = milkshake("green_tea_milkshake",
        Foods.GREEN_TEA_MILKSHAKE, new ResourceLocation("neapolitan", "item/mint_milkshake"))
        .recipe((ctx, prov) -> RespitefulRecipes.milkshake(ctx, prov, GREEN_TEA_ICE_CREAM))
        .register();

    public static final ItemEntry<MilkshakeItem> YELLOW_TEA_MILKSHAKE = milkshake("yellow_tea_milkshake",
        Foods.YELLOW_TEA_MILKSHAKE, new ResourceLocation("neapolitan", "item/banana_milkshake"))
        .recipe((ctx, prov) -> RespitefulRecipes.milkshake(ctx, prov, YELLOW_TEA_ICE_CREAM))
        .register();

    public static final ItemEntry<MilkshakeItem> BLACK_TEA_MILKSHAKE = milkshake("black_tea_milkshake",
        Foods.BLACK_TEA_MILKSHAKE, new ResourceLocation("neapolitan", "item/adzuki_milkshake"))
        .recipe((ctx, prov) -> RespitefulRecipes.milkshake(ctx, prov, BLACK_TEA_ICE_CREAM))
        .register();

    public static final ItemEntry<MilkshakeItem> COFFEE_MILKSHAKE = milkshake("coffee_milkshake",
        Foods.COFFEE_MILKSHAKE, new ResourceLocation("neapolitan", "item/chocolate_milkshake"))
        .recipe((ctx, prov) -> RespitefulRecipes.milkshake(ctx, prov, COFFEE_ICE_CREAM))
        .register();

    private static <T extends Item> NonNullUnaryOperator<ItemBuilder<T, RespitefulRegistrate>> tooltip(String tooltip) {
        return builder -> {
            ResourceLocation id = new ResourceLocation(builder.getOwner().getModid(), builder.getName());
            return builder.addMiscData(ProviderType.LANG, prov -> prov.add(Util.makeDescriptionId("tooltip", id), tooltip));
        };
    }

    private static ItemBuilder<ConsumableItem, RespitefulRegistrate> cakeSlice(BlockEntry<? extends FlavoredCakeBlock> cake, FoodProperties food) {
        return REGISTRATE.item(cake.getId().getPath() + "_slice",
                prop -> new ConsumableItem(prop, true))
            .properties(prop -> prop.food(food));
    }

    private static ItemBuilder<IceCreamItem, RespitefulRegistrate> iceCream(String name, FoodProperties food) {
        return REGISTRATE.item(name, IceCreamItem::new)
            .properties(prop -> prop.food(food).stacksTo(1).craftRemainder(Items.BOWL))
            .tag(NeapolitanItemTags.ICE_CREAM);
    }

    private static ItemBuilder<MilkshakeItem, RespitefulRegistrate> milkshake(
        String name, FoodProperties food, ResourceLocation texture) {
        return REGISTRATE.item(name, MilkshakeItem::new)
            .properties(prop -> prop.food(food).stacksTo(16).craftRemainder(Items.GLASS_BOTTLE))
            .model((ctx, prov) -> prov.generated(ctx, texture));
    }

    public static void register(IEventBus modBus) {
    }

    public static class Foods {
        public static final FoodProperties MINT_GREEN_TEA = new FoodProperties.Builder().alwaysEat()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 1200), 1F)
            .effect(() -> new MobEffectInstance(NeapolitanMobEffects.BERSERKING.get(), 1200), 1F)
            .build();
        public static final FoodProperties VANILLA_MILK_TEA = new FoodProperties.Builder().alwaysEat()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 1200), 1F)
            .effect(() -> new MobEffectInstance(NeapolitanMobEffects.VANILLA_SCENT.get(), 1200), 1F)
            .build();
        public static final FoodProperties ADZUKI_MILK_TEA = new FoodProperties.Builder().alwaysEat()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 1200), 1F)
            .effect(() -> new MobEffectInstance(NeapolitanMobEffects.HARMONY.get(), 1200), 1F)
            .build();
        public static final FoodProperties MOCHA_COFFEE = new FoodProperties.Builder().alwaysEat()
            .effect(() -> new MobEffectInstance(NeapolitanMobEffects.SUGAR_RUSH.get(), 3600), 1F)
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 6000), 1F)
            .build();
        public static final FoodProperties SNOW_TOP_DRINK = new FoodProperties.Builder()
            .nutrition(3).saturationMod(0.6F).build();
        public static final FoodProperties GREEN_TEA_MILKSHAKE = new FoodProperties.Builder().alwaysEat()
            .nutrition(2).saturationMod(1.5F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 1200), 1F)
            .build();
        public static final FoodProperties YELLOW_TEA_MILKSHAKE = new FoodProperties.Builder().alwaysEat()
            .nutrition(2).saturationMod(1.5F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 1200), 1F)
            .build();
        public static final FoodProperties BLACK_TEA_MILKSHAKE = new FoodProperties.Builder().alwaysEat()
            .nutrition(2).saturationMod(1.5F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 1200), 1F)
            .build();
        public static final FoodProperties COFFEE_MILKSHAKE = new FoodProperties.Builder().alwaysEat()
            .nutrition(2).saturationMod(1.5F)
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 1200), 1F)
            .build();
        public static final FoodProperties GREEN_TEA_CAKE = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.1F).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 400), 1F)
            .build();
        public static final FoodProperties GREEN_TEA_CAKE_SLICE = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.1F).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 400), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400), 1F)
            .build();
        public static final FoodProperties YELLOW_TEA_CAKE = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.1F).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 400), 1F)
            .build();
        public static final FoodProperties YELLOW_TEA_CAKE_SLICE = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.1F).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 400), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400), 1F)
            .build();
        public static final FoodProperties BLACK_TEA_CAKE = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.1F).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 400), 1F)
            .build();
        public static final FoodProperties BLACK_TEA_CAKE_SLICE = new FoodProperties.Builder()
            .nutrition(2).saturationMod(0.1F).fast().alwaysEat()
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 400), 1F)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400), 1F)
            .build();
        public static final FoodProperties GREEN_TEA_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 600, 1), 1F)
            .build();
        public static final FoodProperties YELLOW_TEA_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 600, 1), 1F)
            .build();
        public static final FoodProperties BLACK_TEA_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 600, 1), 1F)
            .build();
        public static final FoodProperties COFFEE_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(6).saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(FREffects.CAFFEINATED.get(), 1200, 2), 1F)
            .build();
        public static final FoodProperties RESPITEFUL_ICE_CREAM = new FoodProperties.Builder()
            .nutrition(12).saturationMod(0.3F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 900), 1F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 900), 1F)
            .effect(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 900), 1F)
            .build();

        private static final Map<FoodProperties, Pair<
            List<Pair<Supplier<MobEffectInstance>, Float>>,
            List<Pair<Supplier<MobEffectInstance>, Float>>
            >> FOOD_EFFECTS_REPLACEMENTS = new HashMap<>();

        static {
            addReplacement(FRFoodValues.GREEN_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 1800), 1F));
            addReplacement(FRFoodValues.LONG_GREEN_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 3600), 1F));
            addReplacement(FRFoodValues.STRONG_GREEN_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 1200, 1), 1F));
            addReplacement(FRFoodValues.GREEN_TEA_COOKIE,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.VITALITY.get(), 100), 1F));
            addReplacement(FRFoodValues.YELLOW_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 1800), 1F));
            addReplacement(FRFoodValues.LONG_YELLOW_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 3600), 1F));
            addReplacement(FRFoodValues.STRONG_YELLOW_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 1200, 1), 1F));
            addReplacement(FRFoodValues.TEA_CURRY,
                new Pair<>(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1F),
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.TENACITY.get(), 600), 1F));
            addReplacement(FRFoodValues.BLACK_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 1800), 1F));
            addReplacement(FRFoodValues.LONG_BLACK_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 3600), 1F));
            addReplacement(FRFoodValues.STRONG_BLACK_TEA,
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 1200, 1), 1F));
            addReplacement(FRFoodValues.BLACK_COD,
                new Pair<>(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 3600, 0), 1F),
                new Pair<>(() -> new MobEffectInstance(RespitefulMobEffects.MATURITY.get(), 600), 1F));
        }

        @SafeVarargs
        private static void addReplacement(FoodProperties food, Pair<Supplier<MobEffectInstance>, Float>... effects) {
            FOOD_EFFECTS_REPLACEMENTS.put(food, new Pair<>(((FoodPropertiesAccessor) food).getEffectSuppliers(), Lists.newArrayList(effects)));
        }

        /**
         * For replacing Farmer's Respite food effects with Respiteful's effects.
         *
         * @param replace Whether replace the effects or setting the original effects back.
         */
        public static void replaceFoodEffects(boolean replace) {
            FOOD_EFFECTS_REPLACEMENTS.forEach((food, pair) -> ((FoodPropertiesAccessor) food).setEffects(replace ? pair.getSecond() : pair.getFirst()));
        }

    }

    @EventBusSubscriber(modid = Respiteful.ID)
    public static class ForgeEventHandler {

        @SubscribeEvent
        public static void onWandererTrades(WandererTradesEvent event) {
            if (Configuration.ENABLE_FARMERS_BUY_FD_CROPS.get()) {
                List<VillagerTrades.ItemListing> trades = event.getGenericTrades();
                trades.add(new BlueprintTrade(RespitefulBlocks.GREEN_TEA_CAKE.get().asItem(), 1, 13, 12, 30));
                trades.add(new BlueprintTrade(RespitefulBlocks.YELLOW_TEA_CAKE.get().asItem(), 1, 13, 12, 30));
                trades.add(new BlueprintTrade(RespitefulBlocks.BLACK_TEA_CAKE.get().asItem(), 1, 13, 12, 30));
            }
        }

    }

}
