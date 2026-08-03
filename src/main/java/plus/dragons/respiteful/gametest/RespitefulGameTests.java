package plus.dragons.respiteful.gametest;

import com.teamabnormals.blueprint.core.api.BlueprintCauldronInteraction;
import com.teamabnormals.neapolitan.common.block.MilkshakeCauldronBlock;
import com.teamabnormals.neapolitan.common.item.IceCreamItem;
import com.teamabnormals.neapolitan.common.item.MilkshakeItem;
import com.teamabnormals.neapolitan.core.other.NeapolitanCauldronInteractions;
import com.teamabnormals.neapolitan.core.registry.NeapolitanBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.gametest.PrefixGameTestTemplate;
import net.minecraftforge.registries.ForgeRegistries;
import plus.dragons.respiteful.Respiteful;
import plus.dragons.respiteful.RespitefulConfig;
import plus.dragons.respiteful.common.RespitefulCauldronInteractions;
import plus.dragons.respiteful.core.mixin.FoodPropertiesAccessor;
import plus.dragons.respiteful.entries.RespitefulBlocks;
import plus.dragons.respiteful.entries.RespitefulFluids;
import plus.dragons.respiteful.entries.RespitefulItems;
import plus.dragons.respiteful.entries.RespitefulMobEffects;
import umpaz.farmersrespite.common.FRFoodValues;
import umpaz.farmersrespite.common.registry.FREffects;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.List;
import java.util.Map;

@GameTestHolder(Respiteful.ID)
@PrefixGameTestTemplate(false)
public final class RespitefulGameTests {
    private static final String EMPTY_TEMPLATE = "empty";

    private RespitefulGameTests() {
    }

    @GameTest(template = EMPTY_TEMPLATE)
    public static void machineFluidsHaveStableIdsAndNoBuckets(GameTestHelper helper) {
        List<FluidExpectation> fluids = List.of(
            new FluidExpectation("mint_green_tea", RespitefulFluids.MINT_GREEN_TEA.get()),
            new FluidExpectation("vanilla_milk_tea", RespitefulFluids.VANILLA_MILK_TEA.get()),
            new FluidExpectation("adzuki_milk_tea", RespitefulFluids.ADZUKI_MILK_TEA.get()),
            new FluidExpectation("mocha_coffee", RespitefulFluids.MOCHA_COFFEE.get())
        );

        for (FluidExpectation expectation : fluids) {
            var flowing = expectation.flowing();
            var source = flowing.getSource();
            helper.assertTrue(Items.AIR == flowing.getBucket(), expectation.name() + " flowing fluid has a bucket");
            helper.assertTrue(Items.AIR == source.getBucket(), expectation.name() + " source fluid has a bucket");
            helper.assertTrue(Respiteful.location("flowing_" + expectation.name()).equals(
                ForgeRegistries.FLUIDS.getKey(flowing)), expectation.name() + " flowing ID changed");
            helper.assertTrue(Respiteful.location(expectation.name()).equals(
                ForgeRegistries.FLUIDS.getKey(source)), expectation.name() + " source ID changed");
            helper.assertTrue(("fluid.respiteful." + expectation.name()).equals(source.getFluidType().getDescriptionId()),
                expectation.name() + " translation key changed");
            helper.assertFalse(ForgeRegistries.ITEMS.containsKey(Respiteful.location(expectation.name() + "_bucket")),
                expectation.name() + " unexpectedly registered a bucket item");
        }
        helper.succeed();
    }

    @GameTest(template = EMPTY_TEMPLATE)
    public static void allPre140RegistryAndRecipeIdsRemainAvailable(GameTestHelper helper) {
        List<String> itemIds = List.of(
            "mint_green_tea", "vanilla_milk_tea", "adzuki_milk_tea", "mocha_coffee",
            "snow_top_green_tea", "snow_top_yellow_tea", "snow_top_black_tea", "snow_top_coffee",
            "green_tea_cake", "yellow_tea_cake", "black_tea_cake",
            "green_tea_cake_slice", "yellow_tea_cake_slice", "black_tea_cake_slice",
            "green_tea_ice_cream", "yellow_tea_ice_cream", "black_tea_ice_cream", "coffee_ice_cream",
            "respiteful_ice_cream", "green_tea_ice_cream_block", "yellow_tea_ice_cream_block",
            "black_tea_ice_cream_block", "coffee_ice_cream_block");
        for (String id : itemIds) {
            helper.assertTrue(ForgeRegistries.ITEMS.containsKey(Respiteful.location(id)),
                "missing pre-1.4.0 item ID " + id);
        }

        List<String> baseBlockIds = List.of(
            "green_tea_cake", "yellow_tea_cake", "black_tea_cake",
            "green_tea_ice_cream_block", "yellow_tea_ice_cream_block",
            "black_tea_ice_cream_block", "coffee_ice_cream_block");
        for (String id : baseBlockIds) {
            helper.assertTrue(ForgeRegistries.BLOCKS.containsKey(Respiteful.location(id)),
                "missing pre-1.4.0 block ID " + id);
        }
        for (String candle : List.of(
            "candle", "white_candle", "orange_candle", "magenta_candle", "light_blue_candle",
            "yellow_candle", "lime_candle", "pink_candle", "gray_candle", "light_gray_candle",
            "cyan_candle", "purple_candle", "blue_candle", "brown_candle", "green_candle",
            "red_candle", "black_candle")) {
            for (String cake : List.of("green_tea_cake", "yellow_tea_cake", "black_tea_cake")) {
                String id = candle + "_" + cake;
                helper.assertTrue(ForgeRegistries.BLOCKS.containsKey(Respiteful.location(id)),
                    "missing pre-1.4.0 candle-cake ID " + id);
            }
        }
        for (String effect : List.of("vitality", "tenacity", "maturity")) {
            helper.assertTrue(ForgeRegistries.MOB_EFFECTS.containsKey(Respiteful.location(effect)),
                "missing pre-1.4.0 effect ID " + effect);
        }
        for (String fluid : List.of("mint_green_tea", "vanilla_milk_tea", "adzuki_milk_tea", "mocha_coffee")) {
            ResourceLocation id = Respiteful.location("brewing/" + fluid);
            helper.assertTrue(helper.getLevel().getRecipeManager().byKey(id).isPresent(),
                "missing pre-1.4.0 recipe ID " + id);
        }
        for (String tea : List.of("green_tea", "yellow_tea", "black_tea")) {
            ResourceLocation id = Respiteful.location("cutting/" + tea + "_cake");
            helper.assertTrue(helper.getLevel().getRecipeManager().byKey(id).isPresent(),
                "missing pre-1.4.0 cutting recipe ID " + id);
        }
        helper.succeed();
    }

    @GameTest(template = EMPTY_TEMPLATE)
    public static void iceCreamMatchesNeapolitanBehavior(GameTestHelper helper) {
        List<IceCreamExpectation> iceCreams = List.of(
            new IceCreamExpectation(RespitefulItems.GREEN_TEA_ICE_CREAM.get(), 6,
                List.of(new EffectExpectation(RespitefulMobEffects.VITALITY.get(), 600, 1))),
            new IceCreamExpectation(RespitefulItems.YELLOW_TEA_ICE_CREAM.get(), 6,
                List.of(new EffectExpectation(RespitefulMobEffects.TENACITY.get(), 600, 1))),
            new IceCreamExpectation(RespitefulItems.BLACK_TEA_ICE_CREAM.get(), 6,
                List.of(new EffectExpectation(RespitefulMobEffects.MATURITY.get(), 600, 1))),
            new IceCreamExpectation(RespitefulItems.COFFEE_ICE_CREAM.get(), 6,
                List.of(new EffectExpectation(FREffects.CAFFEINATED.get(), 1200, 2))),
            new IceCreamExpectation(RespitefulItems.RESPITEFUL_ICE_CREAM.get(), 12, List.of(
                new EffectExpectation(RespitefulMobEffects.VITALITY.get(), 900, 0),
                new EffectExpectation(RespitefulMobEffects.TENACITY.get(), 900, 0),
                new EffectExpectation(RespitefulMobEffects.MATURITY.get(), 900, 0)))
        );

        for (IceCreamExpectation expectation : iceCreams) {
            Item item = expectation.item();
            helper.assertTrue(item instanceof IceCreamItem, item + " is not a Neapolitan IceCreamItem");
            verifyFood(helper, item, expectation.nutrition(), 0.3F, false);
            helper.assertTrue(item.getMaxStackSize() == 1, item + " must stack to one");

            Player player = helper.makeMockPlayer();
            player.setTicksFrozen(7);
            ItemStack remainder = item.finishUsingItem(new ItemStack(item), helper.getLevel(), player);
            helper.assertTrue(remainder.is(Items.BOWL), item + " did not return a bowl");
            helper.assertTrue(player.getTicksFrozen() == 207, item + " did not add exactly 200 frozen ticks");
            helper.assertFalse(player.hasEffect(MobEffects.MOVEMENT_SLOWDOWN), item + " still applies Slowness");
            verifyEffects(helper, player, expectation.effects(), item.toString());
        }
        helper.succeed();
    }

    @GameTest(template = EMPTY_TEMPLATE)
    public static void milkshakesMatchNeapolitanBehavior(GameTestHelper helper) {
        List<MilkshakeExpectation> milkshakes = List.of(
            new MilkshakeExpectation(RespitefulItems.GREEN_TEA_MILKSHAKE.get(),
                new EffectExpectation(RespitefulMobEffects.VITALITY.get(), 1200, 0)),
            new MilkshakeExpectation(RespitefulItems.YELLOW_TEA_MILKSHAKE.get(),
                new EffectExpectation(RespitefulMobEffects.TENACITY.get(), 1200, 0)),
            new MilkshakeExpectation(RespitefulItems.BLACK_TEA_MILKSHAKE.get(),
                new EffectExpectation(RespitefulMobEffects.MATURITY.get(), 1200, 0)),
            new MilkshakeExpectation(RespitefulItems.COFFEE_MILKSHAKE.get(),
                new EffectExpectation(FREffects.CAFFEINATED.get(), 1200, 0))
        );

        for (MilkshakeExpectation expectation : milkshakes) {
            Item item = expectation.item();
            helper.assertTrue(item instanceof MilkshakeItem, item + " is not a Neapolitan MilkshakeItem");
            verifyFood(helper, item, 2, 1.5F, true);
            helper.assertTrue(item.getMaxStackSize() == 16, item + " must stack to 16");
            helper.assertTrue(item.getUseDuration(new ItemStack(item)) == 40, item + " must drink for 40 ticks");

            Player player = helper.makeMockPlayer();
            player.getFoodData().setFoodLevel(20);
            helper.assertTrue(player.canEat(item.getFoodProperties().canAlwaysEat()),
                item + " cannot be consumed at full hunger");
            ItemStack remainder = item.finishUsingItem(new ItemStack(item), helper.getLevel(), player);
            helper.assertTrue(remainder.is(Items.GLASS_BOTTLE), item + " did not return a glass bottle");
            verifyEffects(helper, player, List.of(expectation.effect()), item.toString());
        }
        helper.succeed();
    }

    @GameTest(template = EMPTY_TEMPLATE, timeoutTicks = 200)
    public static void milkshakeCauldronsCompleteTheirThreeLevelCycle(GameTestHelper helper) {
        List<CauldronExpectation> cauldrons = List.of(
            new CauldronExpectation(RespitefulItems.GREEN_TEA_ICE_CREAM.get(),
                RespitefulItems.GREEN_TEA_MILKSHAKE.get(), RespitefulBlocks.GREEN_TEA_MILKSHAKE_CAULDRON.get(),
                RespitefulCauldronInteractions.GREEN_TEA_MILKSHAKE),
            new CauldronExpectation(RespitefulItems.YELLOW_TEA_ICE_CREAM.get(),
                RespitefulItems.YELLOW_TEA_MILKSHAKE.get(), RespitefulBlocks.YELLOW_TEA_MILKSHAKE_CAULDRON.get(),
                RespitefulCauldronInteractions.YELLOW_TEA_MILKSHAKE),
            new CauldronExpectation(RespitefulItems.BLACK_TEA_ICE_CREAM.get(),
                RespitefulItems.BLACK_TEA_MILKSHAKE.get(), RespitefulBlocks.BLACK_TEA_MILKSHAKE_CAULDRON.get(),
                RespitefulCauldronInteractions.BLACK_TEA_MILKSHAKE),
            new CauldronExpectation(RespitefulItems.COFFEE_ICE_CREAM.get(),
                RespitefulItems.COFFEE_MILKSHAKE.get(), RespitefulBlocks.COFFEE_MILKSHAKE_CAULDRON.get(),
                RespitefulCauldronInteractions.COFFEE_MILKSHAKE)
        );

        BlockPos relativePos = new BlockPos(2, 1, 2);
        BlockPos absolutePos = helper.absolutePos(relativePos);
        Player player = helper.makeMockPlayer();
        for (CauldronExpectation expectation : cauldrons) {
            helper.assertTrue(expectation.block() instanceof MilkshakeCauldronBlock,
                expectation.block() + " is not a MilkshakeCauldronBlock");
            ResourceLocation blockId = ForgeRegistries.BLOCKS.getKey(expectation.block());
            helper.assertTrue(blockId != null && !ForgeRegistries.ITEMS.containsKey(blockId),
                expectation.block() + " unexpectedly has a block item");
            Map<Item, CauldronInteraction> local = expectation.interactions().map();
            helper.assertTrue(local.containsKey(Items.GLASS_BOTTLE), "missing bottle extraction interaction");
            helper.assertTrue(local.containsKey(expectation.milkshake()), "missing milkshake refill interaction");

            CauldronInteraction conversion = NeapolitanCauldronInteractions.MILK.map().get(expectation.iceCream());
            helper.assertTrue(conversion != null, "missing milk-to-milkshake conversion");
            BlockState milk = NeapolitanBlocks.MILK_CAULDRON.get().defaultBlockState()
                .setValue(LayeredCauldronBlock.LEVEL, 3);
            helper.setBlock(relativePos, milk);
            interact(helper, absolutePos, player, expectation.iceCream(), conversion);
            assertCauldron(helper, relativePos, expectation.block(), 3);
            helper.assertTrue(player.getMainHandItem().is(Items.BOWL), "conversion did not return a bowl");

            CauldronInteraction extract = local.get(Items.GLASS_BOTTLE);
            for (int expectedLevel = 2; expectedLevel >= 0; expectedLevel--) {
                interact(helper, absolutePos, player, Items.GLASS_BOTTLE, extract);
                helper.assertTrue(player.getMainHandItem().is(expectation.milkshake()),
                    "bottling returned the wrong milkshake");
                if (expectedLevel == 0) {
                    helper.assertBlockPresent(Blocks.CAULDRON, relativePos);
                } else {
                    assertCauldron(helper, relativePos, expectation.block(), expectedLevel);
                }
            }

            CauldronInteraction fillEmpty = CauldronInteraction.EMPTY.get(expectation.milkshake());
            helper.assertTrue(fillEmpty != null, "missing empty-cauldron fill interaction");
            interact(helper, absolutePos, player, expectation.milkshake(), fillEmpty);
            assertCauldron(helper, relativePos, expectation.block(), 1);

            CauldronInteraction refill = local.get(expectation.milkshake());
            for (int expectedLevel = 2; expectedLevel <= 3; expectedLevel++) {
                interact(helper, absolutePos, player, expectation.milkshake(), refill);
                assertCauldron(helper, relativePos, expectation.block(), expectedLevel);
                helper.assertTrue(player.getMainHandItem().is(Items.GLASS_BOTTLE),
                    "refilling did not return a glass bottle");
            }
        }
        helper.succeed();
    }

    @GameTest(template = EMPTY_TEMPLATE)
    public static void knivesSlicePlainAndCandleCakesWithoutDamage(GameTestHelper helper) {
        BlockPos plainPos = new BlockPos(2, 1, 2);
        Player player = helper.makeMockPlayer();
        ItemStack knife = new ItemStack(ModItems.IRON_KNIFE.get());
        player.setItemInHand(InteractionHand.MAIN_HAND, knife);
        helper.setBlock(plainPos, RespitefulBlocks.GREEN_TEA_CAKE.get());

        for (int slice = 1; slice <= 7; slice++) {
            helper.useBlock(plainPos, player);
            helper.assertTrue(knife.getDamageValue() == 0, "knife durability was consumed");
            if (slice < 7) {
                helper.assertBlockProperty(plainPos, CakeBlock.BITES, slice);
            } else {
                helper.assertBlockPresent(Blocks.AIR, plainPos);
            }
        }
        helper.assertItemEntityPresent(RespitefulItems.GREEN_TEA_CAKE_SLICE.get(), plainPos, 2.0D);

        BlockPos candlePos = new BlockPos(5, 1, 2);
        Block candleCake = RespitefulBlocks.YELLOW_TEA_CANDLE_CAKES.get(Blocks.CANDLE).get();
        helper.setBlock(candlePos, candleCake);
        helper.useBlock(candlePos, player);
        helper.assertBlockPresent(RespitefulBlocks.YELLOW_TEA_CAKE.get(), candlePos);
        helper.assertBlockProperty(candlePos, CakeBlock.BITES, 1);
        helper.assertItemEntityPresent(RespitefulItems.YELLOW_TEA_CAKE_SLICE.get(), candlePos, 2.0D);
        helper.assertItemEntityPresent(Items.CANDLE, candlePos, 2.0D);
        helper.assertTrue(knife.getDamageValue() == 0, "candle-cake slicing damaged the knife");
        helper.succeed();
    }

    @GameTest(template = EMPTY_TEMPLATE)
    public static void farmersRespiteEffectReplacementIsIdempotent(GameTestHelper helper) {
        boolean configured = RespitefulConfig.COMMON.replaceFarmersRespiteFoodEffects.get();
        FoodPropertiesAccessor greenTea = (FoodPropertiesAccessor) (Object) FRFoodValues.GREEN_TEA;
        try {
            RespitefulItems.Foods.replaceFoodEffects(false);
            int originalSize = greenTea.getEffectSuppliers().size();
            RespitefulItems.Foods.replaceFoodEffects(false);
            helper.assertTrue(greenTea.getEffectSuppliers().size() == originalSize,
                "disabling replacement twice changed the effect count");

            RespitefulItems.Foods.replaceFoodEffects(true);
            int replacementSize = greenTea.getEffectSuppliers().size();
            RespitefulItems.Foods.replaceFoodEffects(true);
            helper.assertTrue(greenTea.getEffectSuppliers().size() == replacementSize,
                "enabling replacement twice duplicated effects");
            helper.assertTrue(replacementSize == 1, "green tea replacement has an unexpected effect count");
            MobEffectInstance effect = greenTea.getEffectSuppliers().get(0).getFirst().get();
            helper.assertTrue(effect.getEffect() == RespitefulMobEffects.VITALITY.get(),
                "green tea replacement uses the wrong effect");

            RespitefulItems.Foods.replaceFoodEffects(false);
            helper.assertTrue(greenTea.getEffectSuppliers().size() == originalSize,
                "restoring original effects changed the effect count");
        } finally {
            RespitefulItems.Foods.replaceFoodEffects(configured);
        }
        helper.succeed();
    }

    private static void verifyFood(GameTestHelper helper, Item item, int nutrition, float saturation,
                                   boolean alwaysEat) {
        FoodProperties food = item.getFoodProperties();
        helper.assertTrue(food != null, item + " has no food properties");
        helper.assertTrue(food.getNutrition() == nutrition, item + " has wrong nutrition");
        helper.assertTrue(Math.abs(food.getSaturationModifier() - saturation) < 0.0001F,
            item + " has wrong saturation");
        helper.assertTrue(food.canAlwaysEat() == alwaysEat, item + " has wrong alwaysEat behavior");
    }

    private static void verifyEffects(GameTestHelper helper, Player player,
                                      List<EffectExpectation> expectations, String itemName) {
        for (EffectExpectation expectation : expectations) {
            MobEffectInstance actual = player.getEffect(expectation.effect());
            helper.assertTrue(actual != null, itemName + " did not apply " + expectation.effect());
            helper.assertTrue(actual.getDuration() == expectation.duration(), itemName + " has wrong effect duration");
            helper.assertTrue(actual.getAmplifier() == expectation.amplifier(), itemName + " has wrong effect amplifier");
        }
    }

    private static void interact(GameTestHelper helper, BlockPos absolutePos, Player player, Item item,
                                 CauldronInteraction interaction) {
        ItemStack stack = new ItemStack(item);
        player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        BlockState state = helper.getLevel().getBlockState(absolutePos);
        interaction.interact(state, helper.getLevel(), absolutePos, player, InteractionHand.MAIN_HAND, stack);
    }

    private static void assertCauldron(GameTestHelper helper, BlockPos pos, Block block, int level) {
        helper.assertBlockPresent(block, pos);
        helper.assertBlockProperty(pos, LayeredCauldronBlock.LEVEL, level);
    }

    private record FluidExpectation(String name, net.minecraft.world.level.material.FlowingFluid flowing) {
    }

    private record EffectExpectation(MobEffect effect, int duration, int amplifier) {
    }

    private record IceCreamExpectation(Item item, int nutrition, List<EffectExpectation> effects) {
    }

    private record MilkshakeExpectation(Item item, EffectExpectation effect) {
    }

    private record CauldronExpectation(Item iceCream, Item milkshake, Block block,
                                       BlueprintCauldronInteraction interactions) {
    }
}
