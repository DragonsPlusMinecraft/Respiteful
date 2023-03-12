package plus.dragons.respiteful.entries;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
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
        .description("Increases armor toughness; higher levels gives more armor toughness.")
        .category(MobEffectCategory.BENEFICIAL)
        .color(0xECCB45)
        .onRegister(effect -> effect.addAttributeModifier(Attributes.ARMOR_TOUGHNESS,
            "6624c857-48be-49ff-921b-15172d3c19c1", 2, AttributeModifier.Operation.ADDITION))
        .register();
    
    public static final RegistryEntry<MobEffect> MATURITY = REGISTRATE.mobEffect("maturity")
        .description("Increases food restore when eating; higher levels restores more food.")
        .category(MobEffectCategory.BENEFICIAL)
        .color(0x783E27)
        .register();
    
    public static void register() {}
    
    @EventBusSubscriber(modid = Respiteful.ID)
    public static class ForgeEventHandler {
        
        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void increaseHealingAmount(LivingHealEvent event) {
            var entity = event.getEntity();
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
        public static void witherResistance(MobEffectEvent.Applicable event) {
            var wither = event.getEffectInstance();
            if (wither.getEffect() != MobEffects.WITHER)
                return;
            var entity = event.getEntity();
            var vitality = entity.getEffect(VITALITY.get());
            if (vitality == null)
                return;
            if (wither.getAmplifier() <= vitality.getAmplifier())
                event.setResult(Event.Result.DENY);
        }
        
    }
    
}
