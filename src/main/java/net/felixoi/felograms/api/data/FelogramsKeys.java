package net.felixoi.felograms.api.data;

import com.google.common.reflect.TypeToken;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.key.KeyFactory;
import org.spongepowered.api.data.value.mutable.Value;

public class FelogramsKeys {

    public static final Key<Value<Boolean>> IS_HOLOGRAM = KeyFactory.makeSingleKey(
            TypeToken.of(Boolean.class),
            new TypeToken<Value<Boolean>>() {
            },
            DataQuery.of("Hologram"), "felograms:is_hologram", "Hologram");

}
