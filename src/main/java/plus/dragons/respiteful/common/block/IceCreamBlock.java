package plus.dragons.respiteful.common.block;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import com.teamabnormals.neapolitan.core.registry.NeapolitanBlocks;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import plus.dragons.respiteful.common.item.TargetedItemCategoryFilling;

/**
 * A {@link Block} extension that fills its item after the latest neapolitan ice cream block item.
 */
public class IceCreamBlock extends Block implements TargetedItemCategoryFilling {
    public static final TargetedItemCategoryFiller FILLER = TargetedItemCategoryFilling.getFiller(NeapolitanBlocks.ADZUKI_ICE_CREAM_BLOCK);
    
    public IceCreamBlock(Properties properties) {
        super(properties);
    }
    
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        FILLER.fillItem(this.asItem(), tab, items);
    }
    
}
