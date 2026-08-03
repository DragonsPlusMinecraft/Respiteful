package plus.dragons.respiteful.entries;

import com.google.common.collect.ImmutableMap;
import com.teamabnormals.neapolitan.common.block.FlavoredCakeBlock;
import com.teamabnormals.neapolitan.common.block.FlavoredCandleCakeBlock;
import com.teamabnormals.neapolitan.common.block.MilkshakeCauldronBlock;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.Util;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import plus.dragons.respiteful.data.RespitefulBlockStates;
import plus.dragons.respiteful.data.RespitefulBlockTags;
import plus.dragons.respiteful.data.RespitefulRecipes;
import plus.dragons.respiteful.common.RespitefulCauldronInteractions;
import plus.dragons.respiteful.common.block.RespitefulFlavoredCakeBlock;
import plus.dragons.respiteful.common.block.RespitefulFlavoredCandleCakeBlock;
import plus.dragons.respiteful.entries.RespitefulItems.Foods;
import umpaz.farmersrespite.common.registry.FRItems;

import java.util.LinkedHashMap;
import java.util.function.Supplier;

import static plus.dragons.respiteful.Respiteful.REGISTRATE;

public class RespitefulBlocks {
    public static final BlockEntry<FlavoredCakeBlock> GREEN_TEA_CAKE = REGISTRATE.<FlavoredCakeBlock>block("green_tea_cake",
            prop -> new RespitefulFlavoredCakeBlock(Foods.GREEN_TEA_CAKE, RespitefulItems.GREEN_TEA_CAKE_SLICE, prop))
        .initialProperties(() -> Blocks.CAKE)
        .properties(prop -> prop.mapColor(MapColor.PLANT))
        .blockstate(RespitefulBlockStates::cake)
        .tag(RespitefulBlockTags.DROPS_GREEN_TEA_CAKE_SLICE)
        .loot((loot, block) -> loot.add(block, BlockLootSubProvider.noDrop()))
        .item()
        .properties(prop -> prop.stacksTo(1))
        .defaultModel()
        .recipe((ctx, prov) -> RespitefulRecipes.cake(ctx, prov, FRItems.GREEN_TEA_LEAVES::get, RespitefulItems.GREEN_TEA_CAKE_SLICE))
        .build()
        .register();
    public static final BlockEntry<FlavoredCakeBlock> YELLOW_TEA_CAKE = REGISTRATE.<FlavoredCakeBlock>block("yellow_tea_cake",
            prop -> new RespitefulFlavoredCakeBlock(Foods.YELLOW_TEA_CAKE, RespitefulItems.YELLOW_TEA_CAKE_SLICE, prop))
        .initialProperties(() -> Blocks.CAKE)
        .properties(prop -> prop.mapColor(MapColor.COLOR_GREEN))
        .blockstate(RespitefulBlockStates::cake)
        .tag(RespitefulBlockTags.DROPS_YELLOW_TEA_CAKE_SLICE)
        .loot((loot, block) -> loot.add(block, BlockLootSubProvider.noDrop()))
        .item()
        .properties(prop -> prop.stacksTo(1))
        .defaultModel()
        .recipe((ctx, prov) -> RespitefulRecipes.cake(ctx, prov, FRItems.YELLOW_TEA_LEAVES::get, RespitefulItems.YELLOW_TEA_CAKE_SLICE))
        .build()
        .register();
    public static final BlockEntry<FlavoredCakeBlock> BLACK_TEA_CAKE = REGISTRATE.<FlavoredCakeBlock>block("black_tea_cake",
            prop -> new RespitefulFlavoredCakeBlock(Foods.BLACK_TEA_CAKE, RespitefulItems.BLACK_TEA_CAKE_SLICE, prop))
        .initialProperties(() -> Blocks.CAKE)
        .properties(prop -> prop.mapColor(MapColor.TERRACOTTA_BLACK))
        .blockstate(RespitefulBlockStates::cake)
        .tag(RespitefulBlockTags.DROPS_BLACK_TEA_CAKE_SLICE)
        .loot((loot, block) -> loot.add(block, BlockLootSubProvider.noDrop()))
        .item()
        .properties(prop -> prop.stacksTo(1))
        .defaultModel()
        .recipe((ctx, prov) -> RespitefulRecipes.cake(ctx, prov, FRItems.BLACK_TEA_LEAVES::get, RespitefulItems.BLACK_TEA_CAKE_SLICE))
        .build()
        .register();
    public static final BlockEntry<Block> GREEN_TEA_ICE_CREAM_BLOCK = REGISTRATE.block("green_tea_ice_cream_block", Block::new)
        .initialProperties(() -> Blocks.SNOW_BLOCK)
        .properties(prop -> prop.mapColor(MapColor.COLOR_LIGHT_GREEN))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCreamBlock(ctx, prov, RespitefulItems.GREEN_TEA_ICE_CREAM))
        .item()
        .tab(CreativeModeTabs.BUILDING_BLOCKS)
        .build()
        .register();
    public static final BlockEntry<Block> YELLOW_TEA_ICE_CREAM_BLOCK = REGISTRATE.block("yellow_tea_ice_cream_block", Block::new)
        .initialProperties(() -> Blocks.SNOW_BLOCK)
        .properties(prop -> prop.mapColor(MapColor.TERRACOTTA_YELLOW))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCreamBlock(ctx, prov, RespitefulItems.YELLOW_TEA_ICE_CREAM))
        .item()
        .tab(CreativeModeTabs.BUILDING_BLOCKS)
        .build()
        .register();
    public static final BlockEntry<Block> BLACK_TEA_ICE_CREAM_BLOCK = REGISTRATE.block("black_tea_ice_cream_block", Block::new)
        .initialProperties(() -> Blocks.SNOW_BLOCK)
        .properties(prop -> prop.mapColor(MapColor.TERRACOTTA_BROWN))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCreamBlock(ctx, prov, RespitefulItems.BLACK_TEA_ICE_CREAM))
        .item()
        .tab(CreativeModeTabs.BUILDING_BLOCKS)
        .build()
        .register();
    public static final BlockEntry<Block> COFFEE_ICE_CREAM_BLOCK = REGISTRATE.block("coffee_ice_cream_block", Block::new)
        .initialProperties(() -> Blocks.SNOW_BLOCK)
        .properties(prop -> prop.mapColor(MapColor.COLOR_BROWN))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCreamBlock(ctx, prov, RespitefulItems.COFFEE_ICE_CREAM))
        .item()
        .tab(CreativeModeTabs.BUILDING_BLOCKS)
        .build()
        .register();
    public static final BlockEntry<MilkshakeCauldronBlock> GREEN_TEA_MILKSHAKE_CAULDRON =
        milkshakeCauldron("green_tea_milkshake_cauldron", RespitefulCauldronInteractions.GREEN_TEA_MILKSHAKE,
            new net.minecraft.resources.ResourceLocation("neapolitan", "block/mint_milkshake"));
    public static final BlockEntry<MilkshakeCauldronBlock> YELLOW_TEA_MILKSHAKE_CAULDRON =
        milkshakeCauldron("yellow_tea_milkshake_cauldron", RespitefulCauldronInteractions.YELLOW_TEA_MILKSHAKE,
            new net.minecraft.resources.ResourceLocation("neapolitan", "block/banana_milkshake"));
    public static final BlockEntry<MilkshakeCauldronBlock> BLACK_TEA_MILKSHAKE_CAULDRON =
        milkshakeCauldron("black_tea_milkshake_cauldron", RespitefulCauldronInteractions.BLACK_TEA_MILKSHAKE,
            new net.minecraft.resources.ResourceLocation("neapolitan", "block/adzuki_milkshake"));
    public static final BlockEntry<MilkshakeCauldronBlock> COFFEE_MILKSHAKE_CAULDRON =
        milkshakeCauldron("coffee_milkshake_cauldron", RespitefulCauldronInteractions.COFFEE_MILKSHAKE,
            new net.minecraft.resources.ResourceLocation("neapolitan", "block/chocolate_milkshake"));
    private static final LinkedHashMap<Block, String> CANDLE_NAMES = Util.make(new LinkedHashMap<>(), map -> {
        map.put(Blocks.CANDLE, "candle");
        map.put(Blocks.WHITE_CANDLE, "white_candle");
        map.put(Blocks.ORANGE_CANDLE, "orange_candle");
        map.put(Blocks.MAGENTA_CANDLE, "magenta_candle");
        map.put(Blocks.LIGHT_BLUE_CANDLE, "light_blue_candle");
        map.put(Blocks.YELLOW_CANDLE, "yellow_candle");
        map.put(Blocks.LIME_CANDLE, "lime_candle");
        map.put(Blocks.PINK_CANDLE, "pink_candle");
        map.put(Blocks.GRAY_CANDLE, "gray_candle");
        map.put(Blocks.LIGHT_GRAY_CANDLE, "light_gray_candle");
        map.put(Blocks.CYAN_CANDLE, "cyan_candle");
        map.put(Blocks.PURPLE_CANDLE, "purple_candle");
        map.put(Blocks.BLUE_CANDLE, "blue_candle");
        map.put(Blocks.BROWN_CANDLE, "brown_candle");
        map.put(Blocks.GREEN_CANDLE, "green_candle");
        map.put(Blocks.RED_CANDLE, "red_candle");
        map.put(Blocks.BLACK_CANDLE, "black_candle");
    });
    public static final ImmutableMap<Block, BlockEntry<FlavoredCandleCakeBlock>> GREEN_TEA_CANDLE_CAKES =
        candleBlocks(GREEN_TEA_CAKE, RespitefulBlockTags.DROPS_GREEN_TEA_CAKE_SLICE);
    public static final ImmutableMap<Block, BlockEntry<FlavoredCandleCakeBlock>> YELLOW_TEA_CANDLE_CAKES =
        candleBlocks(YELLOW_TEA_CAKE, RespitefulBlockTags.DROPS_YELLOW_TEA_CAKE_SLICE);
    public static final ImmutableMap<Block, BlockEntry<FlavoredCandleCakeBlock>> BLACK_TEA_CANDLE_CAKES =
        candleBlocks(BLACK_TEA_CAKE, RespitefulBlockTags.DROPS_BLACK_TEA_CAKE_SLICE);

    private static ImmutableMap<Block, BlockEntry<FlavoredCandleCakeBlock>> candleBlocks(BlockEntry<FlavoredCakeBlock> cake, TagKey<Block> dropSliceTag) {
        ImmutableMap.Builder<Block, BlockEntry<FlavoredCandleCakeBlock>> map = ImmutableMap.builder();
        for (var entry : CANDLE_NAMES.entrySet()) {
            var candle = entry.getKey();
            var candleName = entry.getValue();
            var cakeName = cake.getId().getPath();
            var candleEnName = RegistrateLangProvider.toEnglishName(candleName);
            var cakeEnName = RegistrateLangProvider.toEnglishName(cakeName);
            Supplier<? extends ItemLike> slice = cake == GREEN_TEA_CAKE ? RespitefulItems.GREEN_TEA_CAKE_SLICE
                : cake == YELLOW_TEA_CAKE ? RespitefulItems.YELLOW_TEA_CAKE_SLICE
                : RespitefulItems.BLACK_TEA_CAKE_SLICE;
            map.put(candle, REGISTRATE.<FlavoredCandleCakeBlock>block(candleName + "_" + cakeName,
                    prop -> new RespitefulFlavoredCandleCakeBlock(cake::get, candle, slice, prop))
                .initialProperties(cake)
                .lang(cakeEnName + " with " + candleEnName)
                .blockstate(RespitefulBlockStates::candleCake)
                .tag(BlockTags.CANDLE_CAKES, dropSliceTag)
                .loot((loot, block) -> loot.add(block, RegistrateBlockLootTables.createCandleCakeDrops(candle)))
                .register()
            );
        }
        return map.build();
    }

    private static BlockEntry<MilkshakeCauldronBlock> milkshakeCauldron(
        String name, com.teamabnormals.blueprint.core.api.BlueprintCauldronInteraction interactions,
        net.minecraft.resources.ResourceLocation contentTexture) {
        return REGISTRATE.block(name, properties -> new MilkshakeCauldronBlock(interactions.map()))
            .blockstate((ctx, prov) -> RespitefulBlockStates.milkshakeCauldron(ctx, prov, contentTexture))
            .tag(BlockTags.CAULDRONS)
            .loot((loot, block) -> loot.dropOther(block, Blocks.CAULDRON))
            .register();
    }

    public static void register(IEventBus bus) {
    }

}
