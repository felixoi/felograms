package net.felixoi.felograms.data;

import net.felixoi.felograms.api.data.FelogramKeys;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractBooleanData;
import org.spongepowered.api.data.merge.MergeFunction;

import java.util.Optional;

public final class HologramData extends AbstractBooleanData<HologramData, ImmutableHologramData> {

    HologramData(boolean value) {
        super(value, FelogramKeys.IS_HOLOGRAM, false);
    }

    HologramData() {
        this(false);
    }

    @Override
    public Optional<HologramData> fill(DataHolder dataHolder, MergeFunction overlap) {
        HologramData merged = overlap.merge(this, dataHolder.get(HologramData.class).orElse(null));
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
    public HologramData copy() {
        return new HologramData(this.getValue());
    }

    @Override
    public ImmutableHologramData asImmutable() {
        return new ImmutableHologramData(this.getValue());
    }

    @Override
    public int getContentVersion() {
        return 0;
    }

}
