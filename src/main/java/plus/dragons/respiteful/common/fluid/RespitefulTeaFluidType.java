package plus.dragons.respiteful.common.fluid;

import plus.dragons.respiteful.Respiteful;
import umpaz.farmersrespite.common.fluid.TeaFluidType;

/** Preserves the translation keys used by the former Registrate fluid registrations. */
public final class RespitefulTeaFluidType extends TeaFluidType {
    private final String descriptionId;

    public RespitefulTeaFluidType(String name, int tint) {
        super(tint);
        this.descriptionId = "fluid." + Respiteful.ID + "." + name;
    }

    @Override
    public String getDescriptionId() {
        return this.descriptionId;
    }
}
