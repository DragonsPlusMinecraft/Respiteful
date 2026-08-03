package plus.dragons.respiteful.common;

import com.teamabnormals.blueprint.core.api.BlueprintCauldronInteraction;
import com.teamabnormals.neapolitan.core.other.NeapolitanCauldronInteractions;
import net.minecraft.core.cauldron.CauldronInteraction;
import plus.dragons.respiteful.Respiteful;
import plus.dragons.respiteful.entries.RespitefulBlocks;
import plus.dragons.respiteful.entries.RespitefulItems;

/** Interaction maps for Respiteful's item-backed milkshake cauldrons. */
public final class RespitefulCauldronInteractions {
    public static final BlueprintCauldronInteraction GREEN_TEA_MILKSHAKE = create("green_tea_milkshake");
    public static final BlueprintCauldronInteraction YELLOW_TEA_MILKSHAKE = create("yellow_tea_milkshake");
    public static final BlueprintCauldronInteraction BLACK_TEA_MILKSHAKE = create("black_tea_milkshake");
    public static final BlueprintCauldronInteraction COFFEE_MILKSHAKE = create("coffee_milkshake");

    private RespitefulCauldronInteractions() {
    }

    private static BlueprintCauldronInteraction create(String name) {
        return BlueprintCauldronInteraction.register(Respiteful.location(name),
            CauldronInteraction.newInteractionMap());
    }

    public static void registerInteractions() {
        NeapolitanCauldronInteractions.addMilkshakeInteractions(
            RespitefulItems.GREEN_TEA_MILKSHAKE.get(),
            RespitefulBlocks.GREEN_TEA_MILKSHAKE_CAULDRON.get(),
            RespitefulItems.GREEN_TEA_ICE_CREAM.get(),
            GREEN_TEA_MILKSHAKE.map());
        NeapolitanCauldronInteractions.addMilkshakeInteractions(
            RespitefulItems.YELLOW_TEA_MILKSHAKE.get(),
            RespitefulBlocks.YELLOW_TEA_MILKSHAKE_CAULDRON.get(),
            RespitefulItems.YELLOW_TEA_ICE_CREAM.get(),
            YELLOW_TEA_MILKSHAKE.map());
        NeapolitanCauldronInteractions.addMilkshakeInteractions(
            RespitefulItems.BLACK_TEA_MILKSHAKE.get(),
            RespitefulBlocks.BLACK_TEA_MILKSHAKE_CAULDRON.get(),
            RespitefulItems.BLACK_TEA_ICE_CREAM.get(),
            BLACK_TEA_MILKSHAKE.map());
        NeapolitanCauldronInteractions.addMilkshakeInteractions(
            RespitefulItems.COFFEE_MILKSHAKE.get(),
            RespitefulBlocks.COFFEE_MILKSHAKE_CAULDRON.get(),
            RespitefulItems.COFFEE_ICE_CREAM.get(),
            COFFEE_MILKSHAKE.map());
    }
}
