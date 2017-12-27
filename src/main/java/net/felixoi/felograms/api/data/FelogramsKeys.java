package net.felixoi.felograms.api.data;

import com.google.common.reflect.TypeToken;
import net.felixoi.felograms.api.hologram.Hologram;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.Entity;

/**
 * An enumeration of known {@link Key}s provided by this API.
 */
public class FelogramsKeys {

    /**
     * Represents the {@link Key} for whether an {@link Entity} represents a {@link Hologram}.
     *
     * @see HologramData#isHologram()
     */
    public static final Key<Value<Boolean>> IS_HOLOGRAM = KeyFactory.makeSingleKey(
            TypeToken.of(Boolean.class),
            new TypeToken<Value<Boolean>>() {
            },
            DataQuery.of("Hologram"), "felograms:is_hologram", "Hologram");

}
