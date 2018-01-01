package net.felixoi.felograms.api.data;

import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.Entity;

/**
 * An {@link DataManipulator} to identify whether an {@link Entity} which is used to represent a hologram.
 */
public interface HologramData extends DataManipulator<HologramData, ImmutableHologramData> {

    /**
     * Returns whether a {@link Entity} represents a hologram.
     *
     * @return true if the entity represents a hologram
     * @see FelogramKeys#IS_HOLOGRAM
     */
    Value<Boolean> isHologram();

}
