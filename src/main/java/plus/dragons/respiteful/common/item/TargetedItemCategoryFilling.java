package plus.dragons.respiteful.common.item;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public interface TargetedItemCategoryFilling extends ItemLike {
    Map<ResourceLocation, TargetedItemCategoryFiller> FILLERS = new HashMap<>();
    
    static TargetedItemCategoryFiller getFiller(ResourceLocation targetId, Supplier<Item> target) {
        synchronized (FILLERS) {
            return FILLERS.computeIfAbsent(targetId, id -> new TargetedItemCategoryFiller(target));
        }
    }
    
    static TargetedItemCategoryFiller getFiller(RegistryObject<? extends ItemLike> target) {
        return getFiller(target.getId(), () -> target.get().asItem());
    }
    
    static TargetedItemCategoryFiller getFiller(ItemProviderEntry<?> target) {
        return getFiller(target.getId(), () -> target.get().asItem());
    }
    
    void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items);
    
}
