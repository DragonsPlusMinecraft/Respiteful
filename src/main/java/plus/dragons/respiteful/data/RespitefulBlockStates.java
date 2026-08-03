package plus.dragons.respiteful.data;

import com.teamabnormals.neapolitan.common.block.FlavoredCandleCakeBlock;
import com.teamabnormals.neapolitan.common.block.MilkshakeCauldronBlock;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.AbstractCandleBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CakeBlock;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.registries.ForgeRegistries;

public class RespitefulBlockStates {

    public static void cake(DataGenContext<Block, ? extends CakeBlock> ctx, RegistrateBlockstateProvider prov) {
        prov.getVariantBuilder(ctx.getEntry())
            .forAllStates(state -> {
                int bites = state.getValue(CakeBlock.BITES);
                String name = bites == 0 ? ctx.getName() : ctx.getName() + "_slice" + bites;
                String parent = bites == 0 ? "block/cake" : "block/cake_slice" + bites;
                String textureBaseName = "block/" + ctx.getName();
                return ConfiguredModel.builder().modelFile(
                    prov.models().withExistingParent(name, parent)
                        .texture("particle", textureBaseName + "_side")
                        .texture("bottom", textureBaseName + "_bottom")
                        .texture("top", textureBaseName + "_top")
                        .texture("side", textureBaseName + "_side")
                        .texture("inside", textureBaseName + "_inner")
                ).build();
            });
    }

    public static void candleCake(DataGenContext<Block, FlavoredCandleCakeBlock> ctx, RegistrateBlockstateProvider prov) {
        prov.getVariantBuilder(ctx.getEntry())
            .forAllStates(state -> {
                boolean lit = state.getValue(AbstractCandleBlock.LIT);
                String name = lit ? ctx.getName() + "_lit" : ctx.getName();
                String candle = "minecraft:block/" + ForgeRegistries.BLOCKS.getKey(ctx.getEntry().getCandle()).getPath() + (lit ? "_lit" : "");
                String textureBaseName = "block/" + ForgeRegistries.BLOCKS.getKey(ctx.get().getCake()).getPath();
                return ConfiguredModel.builder().modelFile(
                    prov.models().withExistingParent(name, new ResourceLocation("block/template_cake_with_candle"))
                        .texture("particle", textureBaseName + "_side")
                        .texture("bottom", textureBaseName + "_bottom")
                        .texture("top", textureBaseName + "_top")
                        .texture("side", textureBaseName + "_side")
                        .texture("candle", candle)
                ).build();
            });
    }

    public static void milkshakeCauldron(DataGenContext<Block, MilkshakeCauldronBlock> ctx,
                                          RegistrateBlockstateProvider prov, ResourceLocation contentTexture) {
        prov.getVariantBuilder(ctx.getEntry()).forAllStates(state -> {
            int level = state.getValue(LayeredCauldronBlock.LEVEL);
            String suffix = level == 3 ? "_full" : "_level" + level;
            String parent = level == 3 ? "block/template_cauldron_full" : "block/template_cauldron_level" + level;
            return ConfiguredModel.builder().modelFile(
                prov.models().withExistingParent(ctx.getName() + suffix, parent)
                    .texture("content", contentTexture)
                    .texture("inside", "minecraft:block/cauldron_inner")
                    .texture("particle", "minecraft:block/cauldron_side")
                    .texture("top", "minecraft:block/cauldron_top")
                    .texture("bottom", "minecraft:block/cauldron_bottom")
                    .texture("side", "minecraft:block/cauldron_side")
            ).build();
        });
    }

}
