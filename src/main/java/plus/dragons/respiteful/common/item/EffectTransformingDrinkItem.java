package plus.dragons.respiteful.common.item;

import com.teamabnormals.neapolitan.common.item.DrinkItem;
import com.teamabnormals.neapolitan.core.registry.NeapolitanMobEffects;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;
import plus.dragons.respiteful.RespitefulConfig;

import java.util.List;

public abstract class EffectTransformingDrinkItem extends DrinkItem {
    protected final MobEffectCategory category;
    @Nullable
    protected String tooltipKey;

    public EffectTransformingDrinkItem(MobEffectCategory category, Properties properties) {
        super(properties);
        this.category = category;
    }

    protected abstract boolean canTransformEffect(MobEffectInstance effect);

    protected abstract MobEffectInstance transformEffect(MobEffectInstance effect);

    protected void handleEffects(LivingEntity user) {
        var milk = Items.MILK_BUCKET.getDefaultInstance();
        var effectsToTransform = user.getActiveEffects()
            .stream()
            .filter(instance -> instance.getEffect().getCategory() == this.category)
            .filter(instance -> instance.isCurativeItem(milk))
            .filter(this::canTransformEffect)
            .toList();
        for (MobEffectInstance effect : effectsToTransform) {
            var transformedEffect = this.transformEffect(effect);
            if (user.removeEffect(effect.getEffect())) {
                user.addEffect(transformedEffect);
            }
        }
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
