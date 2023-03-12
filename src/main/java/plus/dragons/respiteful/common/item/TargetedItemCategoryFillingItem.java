package plus.dragons.respiteful.common.item;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * A simple {@link Item} extension that fills itself after the target item.
 */
public class TargetedItemCategoryFillingItem extends Item implements TargetedItemCategoryFilling {
    protected final TargetedItemCategoryFiller filler;

    public TargetedItemCategoryFillingItem(ResourceLocation targetId, Supplier<Item> target, Properties properties) {
        super(properties);
        this.filler = TargetedItemCategoryFilling.getFiller(targetId, target);
    }
    
    public TargetedItemCategoryFillingItem(RegistryObject<? extends Item> target, Properties properties) {
        super(properties);
        this.filler = TargetedItemCategoryFilling.getFiller(target);
    }
    
    public TargetedItemCategoryFillingItem(ItemProviderEntry<?> target, Properties properties) {
        super(properties);
        this.filler = TargetedItemCategoryFilling.getFiller(target);
    }
    
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        filler.fillItem(this, tab, items);
    }
    
}
