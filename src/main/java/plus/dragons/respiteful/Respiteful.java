package plus.dragons.respiteful;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import plus.dragons.respiteful.entries.RespitefulBlocks;
import plus.dragons.respiteful.entries.RespitefulFluids;
import plus.dragons.respiteful.entries.RespitefulItems;
import plus.dragons.respiteful.entries.RespitefulMobEffects;
import plus.dragons.respiteful.data.RespitefulKettleRecipes;
import plus.dragons.respiteful.common.RespitefulCauldronInteractions;
import plus.dragons.respiteful.integration.appleskin.AppleSkinIntegration;

@Mod(Respiteful.ID)
public class Respiteful {
    public static final String ID = "respiteful";
    public static final RespitefulRegistrate REGISTRATE = new RespitefulRegistrate(ID)
        .defaultCreativeTab((ResourceKey<CreativeModeTab>) null);
    public Respiteful() {
        var modCtx = ModLoadingContext.get();
        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        RespitefulConfig.register(modCtx, modBus);
        REGISTRATE.registerEventListeners(modBus);
        this.registerEntries(modBus);
        modBus.addListener(this::commonSetup);
        modBus.addListener(this::gatherData);
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
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(RespitefulCauldronInteractions::registerInteractions);
    }

    private void gatherData(GatherDataEvent event) {
        PackOutput output = event.getGenerator().getPackOutput();
        event.getGenerator().addProvider(event.includeServer(), new RespitefulKettleRecipes(output));
    }

}
