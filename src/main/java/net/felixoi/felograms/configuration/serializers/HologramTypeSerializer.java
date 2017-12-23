package net.felixoi.felograms.configuration.serializers;

import com.google.common.reflect.TypeToken;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.hologram.SimpleHologram;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;

public class HologramTypeSerializer implements TypeSerializer<Hologram> {

    @Override
    public Hologram deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        String id = value.getNode("id").getString();
        List<Text> lines = value.getNode("lines").getList(TypeToken.of(Text.class));
        Location<World> location = value.getNode("location").getValue(new TypeToken<Location<World>>() {});
        boolean disabled = value.getNode("disabled").getBoolean();

        return SimpleHologram.builder().setID(id).setLines(lines).setLocation(location).setDisabled(disabled).build();
    }

    @Override
    public void serialize(TypeToken<?> type, Hologram hologram, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("id").setValue(hologram.getID());
        value.getNode("lines").setValue(new TypeToken<List<Text>>() {}, hologram.getLines());
        value.getNode("location").setValue(new TypeToken<Location<World>>() {}, hologram.getLocation());
        value.getNode("disabled").setValue(hologram.isDisabled());
    }

}

