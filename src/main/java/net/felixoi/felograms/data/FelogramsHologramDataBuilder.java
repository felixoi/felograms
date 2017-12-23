package net.felixoi.felograms.data;

import net.felixoi.felograms.api.data.FelogramsKeys;
import net.felixoi.felograms.api.data.HologramData;
import net.felixoi.felograms.api.data.ImmutableHologramData;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;

import java.util.Optional;

public class FelogramsHologramDataBuilder extends AbstractDataBuilder<HologramData> implements DataManipulatorBuilder<HologramData, ImmutableHologramData> {

    public FelogramsHologramDataBuilder() {
        super(HologramData.class, 0);
    }

    @Override
    public HologramData create() {
        return new FelogramsHologramData();
    }

    @Override
    public Optional<HologramData> createFrom(DataHolder dataHolder) {
        if(dataHolder.supports(FelogramsKeys.IS_HOLOGRAM)) {
            return this.create().fill(dataHolder);
        }

        return Optional.empty();
    }

    @Override
    protected Optional<HologramData> buildContent(DataView container) throws InvalidDataException {
        return this.create().from(container.getContainer());
    }

}
