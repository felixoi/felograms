package net.felixoi.felograms.internal.hologram.creation;

import net.felixoi.felograms.api.hologram.Hologram;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;

public interface HologramCreationBuilder {

    String getName();

    HologramCreationBuilder setName(String name);

    HologramCreationBuilder setLocation(Location<World> location);

    HologramCreationBuilder line(Text line);

    List<Text> getLines();

    HologramCreationBuilder setLines(List<Text> lines);

    Hologram build();

}
