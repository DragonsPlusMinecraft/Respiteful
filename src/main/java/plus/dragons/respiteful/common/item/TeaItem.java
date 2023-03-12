package plus.dragons.respiteful.common.item;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import umpaz.farmersrespite.common.registry.FRItems;
import vectorwing.farmersdelight.common.item.DrinkableItem;

/**
 * A simple {@link Item} extension that fills itself after the {@link FRItems#BLACK_TEA}.
 */
public class TeaItem extends DrinkableItem implements TargetedItemCategoryFilling {
    public static final TargetedItemCategoryFiller FILLER = TargetedItemCategoryFilling.getFiller(FRItems.BLACK_TEA);
    
    public TeaItem(Properties properties) {
        super(properties);
    }
    
    public TeaItem(Properties properties, boolean hasFoodEffectTooltip) {
        super(properties, hasFoodEffectTooltip);
    }
    
    public TeaItem(Properties properties, boolean hasPotionEffectTooltip, boolean hasCustomTooltip) {
        super(properties, hasPotionEffectTooltip, hasCustomTooltip);
    }
    
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        FILLER.fillItem(this, tab, items);
    }
    
}
