package net.felixoi.felograms.util;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.reflect.TypeToken;
import net.felixoi.felograms.internal.configuration.serializer.*;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.hologram.SimpleHologram;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.UUID;

public class ConfigurationUtil {

    public static TypeSerializerCollection getStandardSerializers() {
        return TypeSerializers.getDefaultSerializers().newChild()
                .registerType(TypeToken.of(UUID.class), new UUIDTypeSerializer())
                .registerType(TypeToken.of(Vector3d.class), new Vector3dTypeSerializer())
                .registerType(new TypeToken<Location<World>>() {
                }, new LocationTypeSerializer())
                .registerType(TypeToken.of(Text.class), new TextTypeSerializer())
                .registerPredicate(typeToken -> typeToken.equals(TypeToken.of(Hologram.class)) || typeToken.isSupertypeOf(SimpleHologram.class),
                        new HologramTypeSerializer(SimpleHologram.builder()));
    }

}
