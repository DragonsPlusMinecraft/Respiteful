package plus.dragons.respiteful;

import com.mojang.logging.LogUtils;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import plus.dragons.respiteful.entries.RespitefulBlocks;
import plus.dragons.respiteful.entries.RespitefulFluids;
import plus.dragons.respiteful.entries.RespitefulItems;
import plus.dragons.respiteful.entries.RespitefulMobEffects;
import plus.dragons.respiteful.integration.appleskin.AppleSkinIntegration;
import plus.dragons.respiteful.integration.create.CreateCompatRecipe;

@Mod(Respiteful.ID)
public class Respiteful {
    public static final String ID = "respiteful";
    public static final RespitefulRegistrate REGISTRATE = new RespitefulRegistrate(ID)
        .defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    private static final Logger LOGGER = LogUtils.getLogger();

    public Respiteful() {
        var modCtx = ModLoadingContext.get();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        RespitefulConfig.register(modCtx, modBus);
        REGISTRATE.registerEventListeners(modBus);
        this.registerEntries(modBus);
        this.registerIntegration();
    }

    public static ResourceLocation location(String path) {
        return new ResourceLocation(ID, path);
    }

    private void registerEntries(IEventBus modBus) {
        RespitefulFluids.register(modBus);
        RespitefulBlocks.register(modBus);
        RespitefulItems.register(modBus);
        RespitefulMobEffects.register(modBus);
    }

    private void registerIntegration() {
        ModList mods = ModList.get();
        if (mods.isLoaded("appleskin")) {
            MinecraftForge.EVENT_BUS.register(AppleSkinIntegration.class);
        }
        if (mods.isLoaded("create")) {
            FMLJavaModLoadingContext.get().getModEventBus().register(CreateCompatRecipe.Data.class);
        }
    }

}
