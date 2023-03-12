package plus.dragons.respiteful.integration;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import plus.dragons.respiteful.entries.RespitefulMobEffects;
import squeek.appleskin.api.event.FoodValuesEvent;
import squeek.appleskin.api.food.FoodValues;

public class AppleSkinIntegration {
    
    @SubscribeEvent
    public static void onFoodValues(FoodValuesEvent event) {
        if (event.player.hasEffect(RespitefulMobEffects.MATURITY.get()) && event.defaultFoodValues.hunger > 0) {
            var increment = event.player.getEffect(RespitefulMobEffects.MATURITY.get()).getAmplifier() + 1;
            event.modifiedFoodValues = new FoodValues(event.modifiedFoodValues.hunger + increment,
                                                      event.modifiedFoodValues.saturationModifier);
        }
    }
    
}
