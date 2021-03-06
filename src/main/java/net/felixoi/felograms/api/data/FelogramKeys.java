package net.felixoi.felograms.api.data;

import com.google.common.reflect.TypeToken;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.data.FelogramsHologramData;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.Entity;

/**
 * An enumeration of known {@link Key}s provided by this API.
 */
public final class FelogramKeys {

    /**
     * Represents the {@link Key} for whether an {@link Entity} represents a {@link Hologram}.
     *
     * @see FelogramsHologramData
     */
    public static final Key<Value<Boolean>> IS_HOLOGRAM = Key.builder()
            .type(new TypeToken<Value<Boolean>>(){})
            .id("hologram")
            .name("Hologram")
            .query(DataQuery.of("hologram"))
            .build();

}
