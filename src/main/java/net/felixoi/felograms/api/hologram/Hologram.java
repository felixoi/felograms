package net.felixoi.felograms.api.hologram;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.ResettableBuilder;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;

public interface Hologram extends EntityRepresentable {

    String getID();

    List<Text> getLines();

    Location<World> getLocation();

    boolean isDisabled();

    interface Builder extends ResettableBuilder<Hologram, Builder> {

        Builder setManager(HologramManager hologramManager);

        Builder setID(String id);

        Optional<String> getID();

        Builder line(Text line);

        List<Text> getLines();

        Builder setLines(List<Text> lines);

        Builder setLocation(Location<World> location);

        Builder setDisabled(boolean disabled);

        Hologram build();

        Hologram buildAndRegister();

    }

}
