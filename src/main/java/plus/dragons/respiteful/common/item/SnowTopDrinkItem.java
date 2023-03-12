package plus.dragons.respiteful.common.item;

import com.teamabnormals.blueprint.core.util.item.filling.TargetedItemCategoryFiller;
import com.teamabnormals.neapolitan.common.item.DrinkItem;
import com.teamabnormals.neapolitan.core.registry.NeapolitanMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import plus.dragons.respiteful.RespitefulConfig;
import umpaz.farmersrespite.common.registry.FRItems;

import java.util.ArrayList;
import java.util.List;

public abstract class SnowTopDrinkItem extends DrinkItem implements TargetedItemCategoryFilling {
    private static final TargetedItemCategoryFiller FILLER = TargetedItemCategoryFilling.getFiller(FRItems.COFFEE);
    
    protected final MobEffectCategory category;
    @Nullable
    protected String tooltipKey;
    
    public SnowTopDrinkItem(MobEffectCategory category, Properties properties) {
        super(properties);
        this.category = category;
    }
    
    protected abstract boolean canTransformEffect(MobEffectInstance effect);
    
    protected abstract MobEffectInstance transformEffect(MobEffectInstance effect);
    
    protected void handleEffects(LivingEntity user) {
        var milk = Items.MILK_BUCKET.getDefaultInstance();
        var effects = new ArrayList<MobEffectInstance>();
        for (var effect : user.getActiveEffects()) {
            if (effect.getEffect().getCategory() == this.category &&
                effect.isCurativeItem(milk))
                effects.add(effect);
        }
        var newEffects = new ArrayList<MobEffectInstance>();
        for (var effect : effects) {
            if (this.canTransformEffect(effect) && user.removeEffect(effect.getEffect()))
                newEffects.add(this.transformEffect(effect));
        }
        for (var effect : newEffects)
            user.addEffect(effect);
    }
    
    protected String getTooltipKey() {
        if (tooltipKey == null) {
            tooltipKey = Util.makeDescriptionId("tooltip", ForgeRegistries.ITEMS.getKey(this));
        }
        return tooltipKey;
    }
    
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable(this.getTooltipKey()).withStyle(ChatFormatting.BLUE));
    }
    
    @Override
    public void fillItemCategory(CreativeModeTab tab, NonNullList<ItemStack> items) {
        FILLER.fillItem(this, tab, items);
    }
    
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level worldIn, LivingEntity entity) {
        if (RespitefulConfig.COMMON.enableSnowTopDrinkEffects.get()) {
            if (!entity.hasEffect(NeapolitanMobEffects.VANILLA_SCENT.get()))
                this.handleEffects(entity);
        }
        return super.finishUsingItem(stack, worldIn, entity);
    }
    
    @Override
    public int getUseDuration(ItemStack stack) {
        return 40;
    }
    
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
    
    @Override
    public SoundEvent getDrinkingSound() {
        return SoundEvents.HONEY_DRINK;
    }
    
    @Override
    public SoundEvent getEatingSound() {
        return SoundEvents.HONEY_DRINK;
    }
    
}
