package plus.dragons.respiteful;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;
import plus.dragons.respiteful.entries.RespitefulItems;

public class RespitefulConfig {
    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    
    static {
        Pair<Common, ForgeConfigSpec> common = new Builder().configure(Common::new);
        COMMON = common.getLeft();
        COMMON_SPEC = common.getRight();
    }
    
    static void register(ModLoadingContext context, IEventBus modBus) {
        context.registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
        modBus.addListener(RespitefulConfig::onConfigUpdate);
    }
    
    public static void onConfigUpdate(ModConfigEvent event) {
        var config = event.getConfig();
        if (config.getSpec() == COMMON_SPEC) {
            RespitefulItems.RespitefulFoods.replaceFoodEffects(COMMON.replaceFarmersRespiteFoodEffects.get());
        }
    }
    
    public static class Common {
        public final ConfigValue<Boolean> enableSnowTopDrinkEffects;
        public final ConfigValue<Integer> snowTopDrinkEffectMinDuration;
        public final ConfigValue<Integer> snowTopDrinkEffectMaxDuration;
        public final ConfigValue<Boolean> replaceFarmersRespiteFoodEffects;
        
        Common(Builder builder) {
            builder.push("effects");
            this.enableSnowTopDrinkEffects = builder
                .comment("If the Snow Top drinks' special effects should be used")
                .define("enableSnowTopDrinkEffects", true);
            this.snowTopDrinkEffectMinDuration = builder
                .comment("The minimum duration requirement for an effect to be upgraded by Snow Top Green Tea / Snow Top Coffee")
                .defineInRange("snowTopDrinkEffectMinDuration", 400, 1, Integer.MAX_VALUE);
            this.snowTopDrinkEffectMaxDuration = builder
                .comment("The maximum duration of effects transformed by Snow Top Yellow Tea / Snow Top Black Tea")
                .defineInRange("snowTopDrinkEffectMaxDuration", 32766, 1, Integer.MAX_VALUE);
            this.replaceFarmersRespiteFoodEffects = builder
                .comment("If the foods made from tea in Farmer's Respite should use Respiteful's effects instead")
                .define("replaceFarmersRespiteFoodEffects", true);
            builder.pop();
        }
        
    }
    
}
