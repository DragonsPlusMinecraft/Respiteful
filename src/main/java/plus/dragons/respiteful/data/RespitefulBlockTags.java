package plus.dragons.respiteful.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;
import plus.dragons.respiteful.Respiteful;

public class RespitefulBlockTags {
    public static final TagKey<Block> DROPS_GREEN_TEA_CAKE_SLICE = create("drops_green_tea_cake_slice");
    public static final TagKey<Block> DROPS_YELLOW_TEA_CAKE_SLICE = create("drops_yellow_tea_cake_slice");
    public static final TagKey<Block> DROPS_BLACK_TEA_CAKE_SLICE = create("drops_black_tea_cake_slice");
    
    public static TagKey<Block> create(ResourceLocation id) {
        return TagKey.create(ForgeRegistries.Keys.BLOCKS, id);
    }
    
    public static TagKey<Block> create(String namespace, String path) {
        return create(new ResourceLocation(namespace, path));
    }
    
    public static TagKey<Block> create(String path) {
        return create(Respiteful.genRL(path));
    }
    
}
