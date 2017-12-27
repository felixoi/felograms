package net.felixoi.felograms.internal.configuration.serializer;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.reflect.TypeToken;
import net.felixoi.felograms.api.exception.WorldNotFoundException;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.UUID;

public class LocationTypeSerializer implements TypeSerializer<Location<World>> {

    @Override
    public Location<World> deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        UUID uuid = value.getNode("world").getValue(TypeToken.of(UUID.class));
        Optional<World> world = Sponge.getGame().getServer().getWorld(uuid);

        if (world.isPresent()) {
            return new Location<>(world.get(), value.getNode("position").getValue(TypeToken.of(Vector3d.class)));
        } else {
            throw new WorldNotFoundException(uuid.toString());
        }
    }

    @Override
    public void serialize(TypeToken<?> type, Location<World> obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("position").setValue(TypeToken.of(Vector3d.class), obj.getPosition());
        value.getNode("world").setValue(TypeToken.of(UUID.class), obj.getExtent().getUniqueId());
    }

}
