package net.felixoi.felograms.data;

import net.felixoi.felograms.api.data.FelogramsKeys;
import net.felixoi.felograms.api.data.HologramData;
import net.felixoi.felograms.api.data.ImmutableHologramData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractBooleanData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public class FelogramsHologramData extends AbstractBooleanData<HologramData, ImmutableHologramData> implements HologramData {

    FelogramsHologramData() {
        this(false);
    }

    FelogramsHologramData(boolean value) {
        super(value, FelogramsKeys.IS_HOLOGRAM, false);
    }

    @Override
    public Value<Boolean> isHologram() {
        return this.getValueGetter();
    }

    @Override
    protected Value<Boolean> getValueGetter() {
        return Sponge.getRegistry().getValueFactory()
                .createValue(FelogramsKeys.IS_HOLOGRAM, getValue(), false);
    }

    @Override
    public Optional<HologramData> fill(DataHolder dataHolder, MergeFunction overlap) {
        HologramData merged = overlap.merge(this, dataHolder.get(HologramData.class).orElse(null));
        setValue(merged.isHologram().get());

        return Optional.of(this);
    }

    @Override
    public Optional<HologramData> from(DataContainer container) {
        if(container.contains(FelogramsKeys.IS_HOLOGRAM)) {
            boolean isHologram = container.getBoolean(FelogramsKeys.IS_HOLOGRAM.getQuery()).get();
            return Optional.of(setValue(isHologram));
        }

        return Optional.empty();
    }

    @Override
    public HologramData copy() {
        return new FelogramsHologramData(this.getValue());
    }

    @Override
    public ImmutableHologramData asImmutable() {
        return new ImmutableFelogramsHologramData(this.getValue());
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
