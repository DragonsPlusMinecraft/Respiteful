package plus.dragons.respiteful.common.block;

import com.teamabnormals.neapolitan.common.block.FlavoredCandleCakeBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.function.Supplier;

/** A flavored candle cake with Farmer's Respite-style knife slicing. */
public class RespitefulFlavoredCandleCakeBlock extends FlavoredCandleCakeBlock {
    private final Supplier<? extends ItemLike> slice;

    public RespitefulFlavoredCandleCakeBlock(Supplier<Block> baseCake, Block candle,
                                             Supplier<? extends ItemLike> slice, Properties properties) {
        super(baseCake, candle, properties);
        this.slice = slice;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (player.getItemInHand(hand).is(ModTags.Items.KNIVES)) {
            if (!level.isClientSide) {
                Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(),
                    new ItemStack(this.slice.get()));
                Block.dropResources(state, level, pos);
                level.setBlock(pos, this.getCake().defaultBlockState().setValue(CakeBlock.BITES, 1), 3);
                level.playSound(null, pos, SoundEvents.WOOL_BREAK, SoundSource.PLAYERS, 0.8F, 0.8F);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        return super.use(state, level, pos, player, hand, hit);
    }
}
