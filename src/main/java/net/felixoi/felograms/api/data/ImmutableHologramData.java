package net.felixoi.felograms.api.data;

import net.felixoi.felograms.api.hologram.Hologram;
import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.entity.Entity;

/**
 * An {@link ImmutableDataManipulator} to identify whether an {@link Entity} which is used to represent a {@link Hologram}.
 */
public interface ImmutableHologramData extends ImmutableDataManipulator<ImmutableHologramData, HologramData> {

    /**
     * Returns an {@link ImmutableValue<Boolean>} containing whether a {@link Entity} represents a {@link Hologram}.
     *
     * @return the {@link ImmutableValue<Boolean>}
     */
    ImmutableValue<Boolean> isHologram();

}
