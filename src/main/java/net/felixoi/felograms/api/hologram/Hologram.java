package net.felixoi.felograms.api.hologram;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.UUID;

public interface Hologram {

    UUID getUUID();

    List<Text> getLines();

    Location<World> getLocation();

}
