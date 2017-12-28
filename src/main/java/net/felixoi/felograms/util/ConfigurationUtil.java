package net.felixoi.felograms.util;

import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.hologram.SimpleHologram;
import net.felixoi.felograms.internal.configuration.serializer.HologramTypeSerializer;
import net.felixoi.felograms.internal.configuration.serializer.LocationTypeSerializer;
import net.felixoi.felograms.internal.configuration.serializer.TextTypeSerializer;
import net.felixoi.felograms.internal.configuration.serializer.UUIDTypeSerializer;
import net.felixoi.felograms.internal.configuration.serializer.Vector3iTypeSerializer;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.UUID;

public final class ConfigurationUtil {

    public static TypeSerializerCollection getStandardSerializers() {
        return TypeSerializers.getDefaultSerializers().newChild()
                .registerType(TypeToken.of(UUID.class), new UUIDTypeSerializer())
                .registerType(TypeToken.of(Vector3i.class), new Vector3iTypeSerializer())
                .registerType(new TypeToken<Location<World>>() {
                }, new LocationTypeSerializer())
                .registerType(TypeToken.of(Text.class), new TextTypeSerializer())
                .registerPredicate(typeToken -> typeToken.equals(TypeToken.of(Hologram.class)) || typeToken.isSupertypeOf(SimpleHologram.class), new HologramTypeSerializer());
    }

}
