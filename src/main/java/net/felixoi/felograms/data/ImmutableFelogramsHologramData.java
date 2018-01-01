package net.felixoi.felograms.data;

import net.felixoi.felograms.api.data.FelogramKeys;
import net.felixoi.felograms.api.data.HologramData;
import net.felixoi.felograms.api.data.ImmutableHologramData;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableBooleanData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public final class ImmutableFelogramsHologramData extends AbstractImmutableBooleanData<ImmutableHologramData, HologramData> implements ImmutableHologramData {

    ImmutableFelogramsHologramData(boolean value) {
        super(value, FelogramKeys.IS_HOLOGRAM, false);
    }

    @Override
    public FelogramsHologramData asMutable() {
        return new FelogramsHologramData();
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public ImmutableValue<Boolean> isHologram() {
        return this.getValueGetter();
    }
}
