package plus.dragons.respiteful.entries;

import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import plus.dragons.respiteful.Respiteful;
import plus.dragons.respiteful.common.fluid.RespitefulTeaFluidType;

import static plus.dragons.respiteful.Respiteful.REGISTRATE;

/**
 * Registers Respiteful's machine-only fluids without blocks or buckets.
 *
 * <p>The public {@link FluidEntry} fields are intentionally retained as a compatibility facade for
 * add-ons compiled against Respiteful 1.3.x. Registration itself is handled by Forge so no code ever
 * attempts to resolve a synthetic {@code *_bucket} item.</p>
 */
public final class RespitefulFluids {
    private static final DeferredRegister<FluidType> FLUID_TYPES =
        DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, Respiteful.ID);
    private static final DeferredRegister<Fluid> FLUIDS =
        DeferredRegister.create(ForgeRegistries.FLUIDS, Respiteful.ID);

    private static final TeaFluidRegistration MINT_GREEN_TEA_REGISTRATION =
        tea("mint_green_tea", -3975284);
    private static final TeaFluidRegistration VANILLA_MILK_TEA_REGISTRATION =
        tea("vanilla_milk_tea", -14068339);
    private static final TeaFluidRegistration ADZUKI_MILK_TEA_REGISTRATION =
        tea("adzuki_milk_tea", -12676715);
    private static final TeaFluidRegistration MOCHA_COFFEE_REGISTRATION =
        tea("mocha_coffee", -4795163);

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MINT_GREEN_TEA =
        MINT_GREEN_TEA_REGISTRATION.asFluidEntry();
    public static final FluidEntry<ForgeFlowingFluid.Flowing> VANILLA_MILK_TEA =
        VANILLA_MILK_TEA_REGISTRATION.asFluidEntry();
    public static final FluidEntry<ForgeFlowingFluid.Flowing> ADZUKI_MILK_TEA =
        ADZUKI_MILK_TEA_REGISTRATION.asFluidEntry();
    public static final FluidEntry<ForgeFlowingFluid.Flowing> MOCHA_COFFEE =
        MOCHA_COFFEE_REGISTRATION.asFluidEntry();

    static {
        REGISTRATE.addRawLang("fluid.respiteful.mint_green_tea", "Mint Green Tea");
        REGISTRATE.addRawLang("fluid.respiteful.vanilla_milk_tea", "Vanilla Milk Tea");
        REGISTRATE.addRawLang("fluid.respiteful.adzuki_milk_tea", "Adzuki Milk Tea");
        REGISTRATE.addRawLang("fluid.respiteful.mocha_coffee", "Mocha Coffee");
    }

    private RespitefulFluids() {
    }

    public static void register(IEventBus modBus) {
        FLUID_TYPES.register(modBus);
        FLUIDS.register(modBus);
    }

    private static TeaFluidRegistration tea(String name, int tint) {
        return new TeaFluidRegistration(name, tint);
    }

    private static final class TeaFluidRegistration {
        private final RegistryObject<FluidType> type;
        private final RegistryObject<ForgeFlowingFluid.Source> source;
        private final RegistryObject<ForgeFlowingFluid.Flowing> flowing;

        private TeaFluidRegistration(String name, int tint) {
            this.type = FLUID_TYPES.register(name, () -> new RespitefulTeaFluidType(name, tint));
            this.source = FLUIDS.register(name, () -> new ForgeFlowingFluid.Source(this.properties()));
            this.flowing = FLUIDS.register("flowing_" + name,
                () -> new ForgeFlowingFluid.Flowing(this.properties()));
        }

        private ForgeFlowingFluid.Properties properties() {
            // Deliberately omit bucket and block suppliers. Forge then returns Items.AIR for the
            // bucket and never performs a registry lookup for a non-existent *_bucket entry.
            return new ForgeFlowingFluid.Properties(this.type, this.source, this.flowing);
        }

        private FluidEntry<ForgeFlowingFluid.Flowing> asFluidEntry() {
            return new FluidEntry<>(REGISTRATE, this.flowing);
        }
    }
}
