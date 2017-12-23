package net.felixoi.felograms.data;

import net.felixoi.felograms.api.data.FelogramsKeys;
import net.felixoi.felograms.api.data.HologramData;
import net.felixoi.felograms.api.data.ImmutableHologramData;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableBooleanData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableFelogramsHologramData extends AbstractImmutableBooleanData<ImmutableHologramData, HologramData> implements ImmutableHologramData {


    ImmutableFelogramsHologramData(boolean value) {
        super(value, FelogramsKeys.IS_HOLOGRAM, false);
    }

    @Override
    public ImmutableValue<Boolean> isHologram() {
        return this.getValueGetter();
    }

    @Override
    public HologramData asMutable() {
        return new FelogramsHologramData(this.getValue());
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

}
