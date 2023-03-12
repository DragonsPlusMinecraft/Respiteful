package plus.dragons.respiteful;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import plus.dragons.respiteful.entries.RespitefulBlocks;
import plus.dragons.respiteful.entries.RespitefulItems;
import plus.dragons.respiteful.entries.RespitefulMobEffects;
import plus.dragons.respiteful.integration.AppleSkinIntegration;
import umpaz.farmersrespite.FarmersRespite;

@Mod(Respiteful.ID)
public class Respiteful {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String ID = "respiteful";
    public static final RespitefulRegistrate REGISTRATE = new RespitefulRegistrate(ID)
        .creativeModeTab(() -> FarmersRespite.CREATIVE_TAB);

    public Respiteful() {
        var modCtx = ModLoadingContext.get();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        RespitefulConfig.register(modCtx, modBus);
        REGISTRATE.registerEventListeners(modBus);
        registerEntries();
        registerIntegration();
    }
    
    private void registerEntries() {
        RespitefulBlocks.register();
        RespitefulItems.register();
        RespitefulMobEffects.register();
    }
    
    private void registerIntegration() {
        ModList mods = ModList.get();
        if (mods.isLoaded("appleskin"))
            MinecraftForge.EVENT_BUS.register(AppleSkinIntegration.class);
    }
    
    public static ResourceLocation genRL(String path) {
        return new ResourceLocation(ID, path);
    }

}
