package net.felixoi.felograms.hologram;

import net.felixoi.felograms.api.hologram.Hologram;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleHologram implements Hologram {

    private UUID uuid;
    private List<Text> lines;
    private Location<World> location;

    public SimpleHologram(List<Text> lines, Location<World> location) {
        checkNotNull(lines, "The List<Text> object in SimpleHologram#SimpleHologram(List<Text>, Location<World>) cannot be null.");
        checkNotNull(location, "The Location<World> object in SimpleHologram#SimpleHologram(List<Text>, Location<World>) cannot be null.");

        this.uuid = UUID.randomUUID();
        this.lines = lines;
        this.location = location;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public List<Text> getLines() {
        return this.lines;
    }

    @Override
    public Location<World> getLocation() {
        return this.location;
    }

}
