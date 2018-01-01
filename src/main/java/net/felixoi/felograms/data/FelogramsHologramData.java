package net.felixoi.felograms.data;

import net.felixoi.felograms.api.data.FelogramKeys;
import net.felixoi.felograms.api.data.HologramData;
import net.felixoi.felograms.api.data.ImmutableHologramData;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractBooleanData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.Optional;

public final class FelogramsHologramData extends AbstractBooleanData<HologramData, ImmutableHologramData> implements HologramData {

    FelogramsHologramData(boolean value) {
        super(value, FelogramKeys.IS_HOLOGRAM, false);
    }

    FelogramsHologramData() {
        this(false);
    }

    @Override
    public Optional<HologramData> fill(DataHolder dataHolder, MergeFunction overlap) {
        FelogramsHologramData merged = overlap.merge(this, dataHolder.get(FelogramsHologramData.class).orElse(null));
        this.setValue(merged.getValue());

        return Optional.of(this);
    }

    @Override
    public Optional<HologramData> from(DataContainer container) {
        if(container.contains(FelogramKeys.IS_HOLOGRAM)) {
            boolean hologram = container.getBoolean(FelogramKeys.IS_HOLOGRAM.getQuery()).get();
            return Optional.of(setValue(hologram));
        }

        return Optional.empty();
    }

    @Override
    public FelogramsHologramData copy() {
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
    public Value<Boolean> isHologram() {
        return this.getValueGetter();
    }

}
