package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.hologram.SimpleHologram;
import net.felixoi.felograms.internal.hologram.HologramManager;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationBuilder;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class SimpleCreationBuilder implements HologramCreationBuilder {

    private HologramManager hologramManager;
    private String name;
    private List<Text> lines;
    private Location<World> location;

    protected SimpleCreationBuilder(HologramManager hologramManager, String name) {
        this.hologramManager = checkNotNull(hologramManager, "The variable 'hologramManager' in SimpleCreationBuilder#SimpleCreationBuilder cannot be null.");
        this.name = checkNotNull(name, "The variable 'name' in SimpleCreationBuilder#SimpleCreationBuilder cannot be null.");
        this.lines = new ArrayList<>();
    }

    @Override
    public HologramCreationBuilder setName(String name) {
        this.name = checkNotNull(name, "The variable 'name' in SimpleCreationBuilder#setName cannot be null.");

        return this;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public HologramCreationBuilder setLocation(Location<World> location) {
        this.location = checkNotNull(location, "The variable 'location' in SimpleCreationBuilder#setLocation cannot be null.");

        return this;
    }

    @Override
    public HologramCreationBuilder line(Text line) {
        checkNotNull(line, "The variable 'line' in SimpleCreationBuilder#line cannot be null.");

        this.lines.add(line);

        return this;
    }

    @Override
    public HologramCreationBuilder setLines(List<Text> lines) {
        this.lines = checkNotNull(lines, "The variable 'lines' in SimpleCreationBuilder#setLines cannot be null.");

        return this;
    }

    @Override
    public List<Text> getLines() {
        return this.lines;
    }

    @Override
    public Hologram build() {
        checkNotNull(location, "The variable 'location' in SimpleCreationBuilder#build cannot be null.");
        checkState(!this.lines.isEmpty(), "The list 'lines' in SimpleCreationBuilder cannot be empty.");

        return new SimpleHologram(this.hologramManager, this.name, this.lines, this.location);
    }

}
