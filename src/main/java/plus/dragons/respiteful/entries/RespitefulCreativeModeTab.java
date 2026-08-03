package plus.dragons.respiteful.entries;

import com.teamabnormals.neapolitan.core.registry.NeapolitanBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.util.MutableHashedLinkedMap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import plus.dragons.respiteful.Respiteful;
import umpaz.farmersrespite.common.registry.FRCreativeTab;
import umpaz.farmersrespite.common.registry.FRItems;

import java.util.function.Supplier;

@EventBusSubscriber(bus = Bus.MOD, modid = Respiteful.ID)
public class RespitefulCreativeModeTab {
    @SafeVarargs
    private static void addBefore(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries, Supplier<? extends ItemLike> target, Supplier<? extends ItemLike>... values) {
        var stack = new ItemStack(target.get());
        for (int i = 0; i < values.length; i++) {
            var value = values[i];
            entries.putBefore(stack, new ItemStack(value.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    @SafeVarargs
    private static void addAfter(MutableHashedLinkedMap<ItemStack, CreativeModeTab.TabVisibility> entries, Supplier<? extends ItemLike> target, Supplier<? extends ItemLike>... values) {
        var stack = new ItemStack(target.get());
        // Reverse iterate the values, so they end up in the correct order
        for (int i = values.length - 1; i >= 0; i--) {
            var value = values[i];
            entries.putAfter(stack, new ItemStack(value.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private static void foodAndDrinks(BuildCreativeModeTabContentsEvent event) {
        var entries = event.getEntries();
        addBefore(entries, FRItems.COFFEE,
            RespitefulItems.MINT_GREEN_TEA,
            RespitefulItems.VANILLA_MILK_TEA,
            RespitefulItems.ADZUKI_MILK_TEA
        );
        addAfter(entries, FRItems.COFFEE, RespitefulItems.MOCHA_COFFEE);
        addBefore(entries, FRItems.GREEN_TEA_COOKIE,
            RespitefulItems.SNOW_TOP_GREEN_TEA,
            RespitefulItems.SNOW_TOP_YELLOW_TEA,
            RespitefulItems.SNOW_TOP_BLACK_TEA,
            RespitefulItems.SNOW_TOP_COFFEE,
            RespitefulItems.GREEN_TEA_MILKSHAKE,
            RespitefulItems.YELLOW_TEA_MILKSHAKE,
            RespitefulItems.BLACK_TEA_MILKSHAKE,
            RespitefulItems.COFFEE_MILKSHAKE,
            RespitefulItems.GREEN_TEA_ICE_CREAM,
            RespitefulItems.YELLOW_TEA_ICE_CREAM,
            RespitefulItems.BLACK_TEA_ICE_CREAM,
            RespitefulItems.COFFEE_ICE_CREAM,
            RespitefulItems.RESPITEFUL_ICE_CREAM);
        addBefore(entries, FRItems.COFFEE_CAKE,
            RespitefulBlocks.GREEN_TEA_CAKE,
            RespitefulBlocks.YELLOW_TEA_CAKE,
            RespitefulBlocks.BLACK_TEA_CAKE);
        addBefore(entries, FRItems.COFFEE_CAKE_SLICE,
            RespitefulItems.GREEN_TEA_CAKE_SLICE,
            RespitefulItems.YELLOW_TEA_CAKE_SLICE,
            RespitefulItems.BLACK_TEA_CAKE_SLICE);
    }

    private static void buildingBlocks(BuildCreativeModeTabContentsEvent event) {
        var entries = event.getEntries();
        addAfter(entries, NeapolitanBlocks.ADZUKI_ICE_CREAM_BLOCK,
            RespitefulBlocks.GREEN_TEA_ICE_CREAM_BLOCK,
            RespitefulBlocks.YELLOW_TEA_ICE_CREAM_BLOCK,
            RespitefulBlocks.BLACK_TEA_ICE_CREAM_BLOCK,
            RespitefulBlocks.COFFEE_ICE_CREAM_BLOCK);
    }

    @SubscribeEvent
    public static void buildCreativeModeTab(BuildCreativeModeTabContentsEvent event) {
        var tab = event.getTabKey();
        if (tab == FRCreativeTab.FARMERS_RESPITE_TAB.getKey() || tab == CreativeModeTabs.FOOD_AND_DRINKS) {
            foodAndDrinks(event);
        } else if (tab == CreativeModeTabs.BUILDING_BLOCKS) {
            buildingBlocks(event);
        }
    }
}
