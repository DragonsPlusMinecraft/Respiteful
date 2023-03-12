package plus.dragons.respiteful.entries;

import com.google.common.collect.ImmutableMap;
import com.teamabnormals.neapolitan.common.block.FlavoredCakeBlock;
import com.teamabnormals.neapolitan.common.block.FlavoredCandleCakeBlock;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MaterialColor;
import plus.dragons.respiteful.common.block.IceCreamBlock;
import plus.dragons.respiteful.data.RespitefulBlockStates;
import plus.dragons.respiteful.data.RespitefulBlockTags;
import plus.dragons.respiteful.data.RespitefulRecipes;
import plus.dragons.respiteful.entries.RespitefulItems.RespitefulFoods;
import umpaz.farmersrespite.common.registry.FRItems;

import java.util.LinkedHashMap;

import static plus.dragons.respiteful.Respiteful.REGISTRATE;

@SuppressWarnings("unused")
public class RespitefulBlocks {
    private static final LinkedHashMap<Block, String> CANDLE_NAMES = new LinkedHashMap<>();
    static {
        CANDLE_NAMES.put(Blocks.CANDLE, "candle");
        CANDLE_NAMES.put(Blocks.WHITE_CANDLE, "white_candle");
        CANDLE_NAMES.put(Blocks.ORANGE_CANDLE, "orange_candle");
        CANDLE_NAMES.put(Blocks.MAGENTA_CANDLE, "magenta_candle");
        CANDLE_NAMES.put(Blocks.LIGHT_BLUE_CANDLE, "light_blue_candle");
        CANDLE_NAMES.put(Blocks.YELLOW_CANDLE, "yellow_candle");
        CANDLE_NAMES.put(Blocks.LIME_CANDLE, "lime_candle");
        CANDLE_NAMES.put(Blocks.PINK_CANDLE, "pink_candle");
        CANDLE_NAMES.put(Blocks.GRAY_CANDLE, "gray_candle");
        CANDLE_NAMES.put(Blocks.LIGHT_GRAY_CANDLE, "light_gray_candle");
        CANDLE_NAMES.put(Blocks.CYAN_CANDLE, "cyan_candle");
        CANDLE_NAMES.put(Blocks.PURPLE_CANDLE, "purple_candle");
        CANDLE_NAMES.put(Blocks.BLUE_CANDLE, "blue_candle");
        CANDLE_NAMES.put(Blocks.BROWN_CANDLE, "brown_candle");
        CANDLE_NAMES.put(Blocks.GREEN_CANDLE, "green_candle");
        CANDLE_NAMES.put(Blocks.RED_CANDLE, "red_candle");
        CANDLE_NAMES.put(Blocks.BLACK_CANDLE, "black_candle");
    }
    
    public static final BlockEntry<FlavoredCakeBlock> GREEN_TEA_CAKE = REGISTRATE.block("green_tea_cake",
        prop -> new FlavoredCakeBlock(RespitefulFoods.GREEN_TEA_CAKE, prop))
        .initialProperties(() -> Blocks.CAKE)
        .properties(prop -> prop.color(MaterialColor.PLANT))
        .blockstate(RespitefulBlockStates::cake)
        .tag(RespitefulBlockTags.DROPS_GREEN_TEA_CAKE_SLICE)
        .loot((loot, block) -> loot.add(block, BlockLoot.noDrop()))
        .item()
        .properties(prop -> prop.stacksTo(1))
        .defaultModel()
        .recipe((ctx, prov) -> RespitefulRecipes.cake(ctx, prov, FRItems.GREEN_TEA_LEAVES::get, RespitefulItems.GREEN_TEA_CAKE_SLICE))
        .build()
        .register();
    
    public static final ImmutableMap<Block, BlockEntry<FlavoredCandleCakeBlock>> GREEN_TEA_CANDLE_CAKES =
        candleBlocks(GREEN_TEA_CAKE, RespitefulBlockTags.DROPS_GREEN_TEA_CAKE_SLICE);
    
    public static final BlockEntry<FlavoredCakeBlock> YELLOW_TEA_CAKE = REGISTRATE.block("yellow_tea_cake",
            prop -> new FlavoredCakeBlock(RespitefulFoods.YELLOW_TEA_CAKE, prop))
        .initialProperties(() -> Blocks.CAKE)
        .properties(prop -> prop.color(MaterialColor.COLOR_GREEN))
        .blockstate(RespitefulBlockStates::cake)
        .tag(RespitefulBlockTags.DROPS_YELLOW_TEA_CAKE_SLICE)
        .loot((loot, block) -> loot.add(block, BlockLoot.noDrop()))
        .item()
        .properties(prop -> prop.stacksTo(1))
        .defaultModel()
        .recipe((ctx, prov) -> RespitefulRecipes.cake(ctx, prov, FRItems.YELLOW_TEA_LEAVES::get, RespitefulItems.YELLOW_TEA_CAKE_SLICE))
        .build()
        .register();
    
    public static final ImmutableMap<Block, BlockEntry<FlavoredCandleCakeBlock>> YELLOW_TEA_CANDLE_CAKES =
        candleBlocks(YELLOW_TEA_CAKE, RespitefulBlockTags.DROPS_YELLOW_TEA_CAKE_SLICE);
    
    public static final BlockEntry<FlavoredCakeBlock> BLACK_TEA_CAKE = REGISTRATE.block("black_tea_cake",
            prop -> new FlavoredCakeBlock(RespitefulFoods.BLACK_TEA_CAKE, prop))
        .initialProperties(() -> Blocks.CAKE)
        .properties(prop -> prop.color(MaterialColor.TERRACOTTA_BLACK))
        .blockstate(RespitefulBlockStates::cake)
        .tag(RespitefulBlockTags.DROPS_BLACK_TEA_CAKE_SLICE)
        .loot((loot, block) -> loot.add(block, BlockLoot.noDrop()))
        .item()
        .properties(prop -> prop.stacksTo(1))
        .defaultModel()
        .recipe((ctx, prov) -> RespitefulRecipes.cake(ctx, prov, FRItems.BLACK_TEA_LEAVES::get, RespitefulItems.BLACK_TEA_CAKE_SLICE))
        .build()
        .register();
    
    public static final ImmutableMap<Block, BlockEntry<FlavoredCandleCakeBlock>> BLACK_TEA_CANDLE_CAKES =
        candleBlocks(BLACK_TEA_CAKE, RespitefulBlockTags.DROPS_BLACK_TEA_CAKE_SLICE);
    
    public static final BlockEntry<IceCreamBlock> GREEN_TEA_ICE_CREAM_BLOCK = REGISTRATE.block("green_tea_ice_cream_block", IceCreamBlock::new)
        .initialProperties(() -> Blocks.SNOW_BLOCK)
        .properties(prop -> prop.color(MaterialColor.COLOR_LIGHT_GREEN))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCreamBlock(ctx, prov, RespitefulItems.GREEN_TEA_ICE_CREAM))
        .item()
        .tab(() -> CreativeModeTab.TAB_BUILDING_BLOCKS)
        .build()
        .register();
    
    public static final BlockEntry<IceCreamBlock> YELLOW_TEA_ICE_CREAM_BLOCK = REGISTRATE.block("yellow_tea_ice_cream_block", IceCreamBlock::new)
        .initialProperties(() -> Blocks.SNOW_BLOCK)
        .properties(prop -> prop.color(MaterialColor.TERRACOTTA_YELLOW))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCreamBlock(ctx, prov, RespitefulItems.YELLOW_TEA_ICE_CREAM))
        .item()
        .tab(() -> CreativeModeTab.TAB_BUILDING_BLOCKS)
        .build()
        .register();
    
    public static final BlockEntry<IceCreamBlock> BLACK_TEA_ICE_CREAM_BLOCK = REGISTRATE.block("black_tea_ice_cream_block", IceCreamBlock::new)
        .initialProperties(() -> Blocks.SNOW_BLOCK)
        .properties(prop -> prop.color(MaterialColor.TERRACOTTA_BROWN))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCreamBlock(ctx, prov, RespitefulItems.BLACK_TEA_ICE_CREAM))
        .item()
        .tab(() -> CreativeModeTab.TAB_BUILDING_BLOCKS)
        .build()
        .register();
    
    public static final BlockEntry<IceCreamBlock> COFFEE_ICE_CREAM_BLOCK = REGISTRATE.block("coffee_ice_cream_block", IceCreamBlock::new)
        .initialProperties(() -> Blocks.SNOW_BLOCK)
        .properties(prop -> prop.color(MaterialColor.COLOR_BROWN))
        .tag(BlockTags.MINEABLE_WITH_SHOVEL)
        .recipe((ctx, prov) -> RespitefulRecipes.iceCreamBlock(ctx, prov, RespitefulItems.COFFEE_ICE_CREAM))
        .item()
        .tab(() -> CreativeModeTab.TAB_BUILDING_BLOCKS)
        .build()
        .register();
    
    private static ImmutableMap<Block, BlockEntry<FlavoredCandleCakeBlock>> candleBlocks(BlockEntry<FlavoredCakeBlock> cake, TagKey<Block> dropSliceTag) {
        ImmutableMap.Builder<Block, BlockEntry<FlavoredCandleCakeBlock>> map = ImmutableMap.builder();
        for (var entry : CANDLE_NAMES.entrySet()) {
            var candle = entry.getKey();
            var candleName = entry.getValue();
            var cakeName = cake.getId().getPath();
            var candleEnName = RegistrateLangProvider.toEnglishName(candleName);
            var cakeEnName = RegistrateLangProvider.toEnglishName(cakeName);
            map.put(candle, REGISTRATE.block(candleName + "_" + cakeName,
                prop -> new FlavoredCandleCakeBlock(cake::get, candle, prop))
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
    
    public static void register() {}
    
}
