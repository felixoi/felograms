package net.felixoi.felograms.impl;

import net.felixoi.felograms.api.hologram.Hologram;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SimpleHologram implements Hologram {

    private UUID uuid;
    private List<Text> lines;
    private Location<World> location;

    public SimpleHologram(List<Text> lines, Location<World> location) {
        Objects.requireNonNull(lines, "'lines' in net.felixoi.felograms cannot be null.");
        Objects.requireNonNull(location, "'location' in net.felixoi.felograms cannot be null.");

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
