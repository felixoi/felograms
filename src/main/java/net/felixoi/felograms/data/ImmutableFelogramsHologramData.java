package net.felixoi.felograms.data;

import net.felixoi.felograms.api.data.FelogramsKeys;
import net.felixoi.felograms.api.data.HologramData;
import net.felixoi.felograms.api.data.ImmutableHologramData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableSingleData;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableFelogramsHologramData extends AbstractImmutableSingleData<Boolean, ImmutableHologramData, HologramData> implements ImmutableHologramData {

    ImmutableFelogramsHologramData() {
        this(false);
    }

    ImmutableFelogramsHologramData(boolean value) {
        super(value, FelogramsKeys.IS_HOLOGRAM);
    }

    @Override
    protected ImmutableValue<?> getValueGetter() {
        return Sponge.getRegistry().getValueFactory()
                .createValue(FelogramsKeys.IS_HOLOGRAM, getValue(), false).asImmutable();
    }

    @Override
    public HologramData asMutable() {
        return new FelogramsHologramData(this.getValue());
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

    @Override
    public DataContainer toContainer() {
        DataContainer container = super.toContainer();

        container.set(FelogramsKeys.IS_HOLOGRAM.getQuery(), this.getValue());

        return container;
    }

}
