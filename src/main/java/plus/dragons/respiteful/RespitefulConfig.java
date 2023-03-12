package plus.dragons.respiteful;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.ApiStatus;
import plus.dragons.respiteful.entries.RespitefulItems;
import plus.dragons.respiteful.entries.RespitefulMobEffects;

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
    
    @SuppressWarnings("deprecation")
    public static void onConfigUpdate(ModConfigEvent event) {
        var config = event.getConfig();
        if (config.getSpec() == COMMON_SPEC) {
            RespitefulItems.RespitefulFoods.setDrinksCanAlwaysEat(COMMON.canFarmersRespiteDrinksAlwaysEat.get());
            RespitefulItems.RespitefulFoods.replaceFoodEffects(COMMON.replaceFarmersRespiteFoodEffects.get());
            RespitefulMobEffects.replaceCaffeinatedModifiers(COMMON.replaceCaffeinatedEffect.get());
        }
    }
    
    public static class Common {
        public final ConfigValue<Boolean> enableSnowTopDrinkEffects;
        public final ConfigValue<Integer> snowTopDrinkEffectMinDuration;
        public final ConfigValue<Integer> snowTopDrinkEffectMaxDuration;
        public final ConfigValue<Boolean> canFarmersRespiteDrinksAlwaysEat;
        public final ConfigValue<Boolean> replaceFarmersRespiteFoodEffects;
        @Deprecated
        @ApiStatus.ScheduledForRemoval
        public final ConfigValue<Boolean> replaceCaffeinatedEffect;
        
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
            this.canFarmersRespiteDrinksAlwaysEat = builder
                .comment("If the drinks from Farmer's Respite can be drunk even when the player is full")
                .define("canFarmersRespiteDrinksAlwaysEat", true);
            this.replaceFarmersRespiteFoodEffects = builder
                .comment("If the foods made from tea in Farmer's Respite should use Respiteful's effects instead")
                .define("replaceFarmersRespiteFoodEffects", true);
            this.replaceCaffeinatedEffect = builder
                .comment("If the Caffeinated effect should be using the new attribute as in 1.19+",
                         "The new attribute would be + 10% Speed & + 20% Attack Speed per level",
                         "@ApiStatus.ScheduledForRemoval: this config will be removed in 1.19+ since it would be meaningless by then")
                .define("replaceCaffeinatedEffect", true);
            builder.pop();
        }
        
    }
    
}
