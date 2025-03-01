package plus.dragons.respiteful.entries;

import com.teamabnormals.neapolitan.core.registry.NeapolitanItems;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.FluidEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ForgeRegistries;
import plus.dragons.respiteful.RespitefulRegistrate;
import umpaz.farmersrespite.common.fluid.TeaFluidType;
import umpaz.farmersrespite.common.registry.FRItems;
import umpaz.farmersrespite.data.builder.KettlePouringRecipeBuilder;
import umpaz.farmersrespite.data.builder.KettleRecipeBuilder;

import static plus.dragons.respiteful.Respiteful.REGISTRATE;

public class RespitefulFluids {
    public static final FluidEntry<ForgeFlowingFluid.Flowing> MINT_GREEN_TEA = tea("mint_green_tea", 0x3CA874)
            .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> VANILLA_MILK_TEA = tea("vanilla_milk_tea", 0xD6AA73)
            .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> ADZUKI_MILK_TEA = tea("adzuki_milk_tea", 0xC16E6B)
            .register();

    public static final FluidEntry<ForgeFlowingFluid.Flowing> MOCHA_COFFEE = tea("mocha_coffee", 0x492B1B)
            .register();

    public static void register(IEventBus modBus) {
    }

    @SuppressWarnings("UnstableApiUsage")
    private static FluidBuilder<ForgeFlowingFluid.Flowing, RespitefulRegistrate> tea(String name, int tint) {
        return REGISTRATE.fluid(
                name,
                TeaFluidType.FLUID_STILL_TEXTURE,
                TeaFluidType.FLUID_FLOWING_TEXTURE,
                (prop, still, flow) -> new TeaFluidType(tint)
        ).source(ForgeFlowingFluid.Source::new);
    }

    private static KettlePouringRecipeBuilder.Result kettlePouring(ResourceLocation id, Fluid fluid, int amount, ItemLike container, ItemLike result) {
        return new KettlePouringRecipeBuilder.Result(id.withPrefix("pouring/"), new ItemStack(container), fluid, amount, new ItemStack(result));
    }

}