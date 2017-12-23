package net.felixoi.felograms.api.data;

import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.value.mutable.Value;

public interface HologramData extends DataManipulator<HologramData, ImmutableHologramData> {

    Value<Boolean> isHologram();

}
