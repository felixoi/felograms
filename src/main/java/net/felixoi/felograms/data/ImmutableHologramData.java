package net.felixoi.felograms.data;

import net.felixoi.felograms.api.data.FelogramKeys;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableBooleanData;

public final class ImmutableHologramData extends AbstractImmutableBooleanData<ImmutableHologramData, HologramData> {

    ImmutableHologramData(boolean value) {
        super(value, FelogramKeys.IS_HOLOGRAM, false);
    }

    @Override
    public HologramData asMutable() {
        return new HologramData();
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

}
