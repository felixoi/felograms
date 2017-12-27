package net.felixoi.felograms.internal.configuration.serializer;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.hologram.SimpleHologram;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.UUID;

public class HologramTypeSerializer implements TypeSerializer<Hologram> {

    @Override
    public Hologram deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        UUID uuid = value.getNode("uuid").getValue(TypeToken.of(UUID.class));
        String name = value.getNode("name").getString();
        List<Text> lines = value.getNode("lines").getList(new TypeToken<Text>() {
        });
        UUID worldUUID = value.getNode("world").getValue(TypeToken.of(UUID.class));
        Vector3i position = value.getNode("position").getValue(TypeToken.of(Vector3i.class));
        boolean disabled = value.getNode("disabled").getBoolean();

        return new SimpleHologram(Felograms.getInstance().getHologramManager(), uuid, name, lines, worldUUID, position, disabled);
    }

    @Override
    public void serialize(TypeToken<?> type, Hologram hologram, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("uuid").setValue(TypeToken.of(UUID.class), hologram.getWorldUniqueID());
        value.getNode("name").setValue(hologram.getName());
        value.getNode("lines").setValue(new TypeToken<List<Text>>() {
        }, hologram.getLines());
        value.getNode("world").setValue(TypeToken.of(UUID.class), hologram.getWorldUniqueID());
        value.getNode("position").setValue(TypeToken.of(Vector3d.class), hologram.getPosition());
        value.getNode("disabled").setValue(hologram.isDisabled());
    }

}
