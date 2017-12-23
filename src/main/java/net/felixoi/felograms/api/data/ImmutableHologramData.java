package net.felixoi.felograms.api.data;

import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public interface ImmutableHologramData extends ImmutableDataManipulator<ImmutableHologramData, HologramData> {

    ImmutableValue<Boolean> isHologram();

}
