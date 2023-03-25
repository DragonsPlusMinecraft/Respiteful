package plus.dragons.respiteful.entries;

import com.farmersrespite.core.registry.FREffects;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.PotionEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import org.jetbrains.annotations.ApiStatus;
import plus.dragons.respiteful.Respiteful;

import static plus.dragons.respiteful.Respiteful.REGISTRATE;

public class RespitefulMobEffects {
    
    public static final RegistryEntry<MobEffect> VITALITY = REGISTRATE.mobEffect("vitality")
        .description("Grants resistance for Wither effect and increases healing amount; " +
            "higher levels grants stronger resistance and gives more additional healing amount.")
        .category(MobEffectCategory.BENEFICIAL)
        .color(0x519641)
        .register();
    
    public static final RegistryEntry<MobEffect> TENACITY = REGISTRATE.mobEffect("tenacity")
        .description("Increases armor toughness and knockback resistance; higher levels gives more armor toughness and knockback resistance.")
        .category(MobEffectCategory.BENEFICIAL)
        .color(0xECCB45)
        .onRegister(effect -> effect
            .addAttributeModifier(Attributes.ARMOR_TOUGHNESS,
            "6624c857-48be-49ff-921b-15172d3c19c1", 2, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(Attributes.KNOCKBACK_RESISTANCE,
            "d673225e-8a54-4362-987f-5e82f7ca99e3", 0.2, AttributeModifier.Operation.ADDITION))
        .register();
    
    public static final RegistryEntry<MobEffect> MATURITY = REGISTRATE.mobEffect("maturity")
        .description("Grants immunity for harmful effects from food and increases food restore when eating; higher levels restores more food.")
        .category(MobEffectCategory.BENEFICIAL)
        .color(0x783E27)
        .register();
    
    public static void register() {}
    
    /**
     * For bring the Caffeinated effect change in Farmer's Respite 1.19+ back to 1.18.2.
     * @param replace Whether replace the modifiers or setting the original modifiers back.
     */
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion = "1.19")
    public static void replaceCaffeinatedModifiers(boolean replace) {
        if (!FREffects.CAFFEINATED.isPresent())
            return;
        if (replace) {
            FREffects.CAFFEINATED.get()
                .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "ca4cd828-53ad-4ce7-93da-92684d75be47", 0.1D, AttributeModifier.Operation.MULTIPLY_TOTAL)
                .addAttributeModifier(Attributes.ATTACK_SPEED,
                    "3e07acfc-7b1d-40a1-af8c-fbe34be88b3a", 0.2D, AttributeModifier.Operation.MULTIPLY_TOTAL);
        } else {
            FREffects.CAFFEINATED.get()
                .addAttributeModifier(Attributes.MOVEMENT_SPEED,
                    "ca4cd828-53ad-4ce7-93da-92684d75be47", 0.01D, AttributeModifier.Operation.MULTIPLY_TOTAL)
                .addAttributeModifier(Attributes.ATTACK_SPEED,
                    "3e07acfc-7b1d-40a1-af8c-fbe34be88b3a", 0.5D, AttributeModifier.Operation.MULTIPLY_TOTAL);
        }
    }
    
    @EventBusSubscriber(modid = Respiteful.ID)
    public static class ForgeEventHandler {
        
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void increaseHealingAmount(LivingHealEvent event) {
            var entity = event.getEntityLiving();
            var sprouting = entity.getEffect(VITALITY.get());
            if (sprouting == null)
                return;
            float amount = event.getAmount();
            int amplifier = sprouting.getAmplifier();
            long maxIncrease = 1L << amplifier;
            float increase = Math.min(amount * 0.5F * (amplifier + 1), maxIncrease);
            event.setAmount(event.getAmount() + increase);
        }
        
        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void witherResistance(PotionEvent.PotionApplicableEvent event) {
            var wither = event.getPotionEffect();
            if (wither.getEffect() != MobEffects.WITHER)
                return;
            var entity = event.getEntityLiving();
            var vitality = entity.getEffect(VITALITY.get());
            if (vitality == null)
                return;
            if (wither.getAmplifier() <= vitality.getAmplifier())
                event.setResult(Event.Result.DENY);
        }
        
    }
    
}
