package net.felixoi.felograms.hologram;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.data.FelogramsKeys;
import net.felixoi.felograms.api.data.HologramData;
import net.felixoi.felograms.api.data.ImmutableHologramData;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramManager;
import net.felixoi.felograms.util.LocationUtil;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleHologram implements Hologram {

    private static double SPACE_BETWEEN_LINES = 0.25;

    private String id;
    private List<Text> lines;
    private Location<World> location;
    private boolean disabled;
    private List<UUID> entities;

    private SimpleHologram(String id, List<Text> lines, Location<World> location, boolean disabled) {
        checkNotNull(id, "The variable 'id' in SimpleHologram#SimpleHologram(name, lines, location) cannot be null.");
        checkNotNull(lines, "The variable 'lines' in SimpleHologram#SimpleHologram(name, lines, location) cannot be null.");
        checkNotNull(location, "The variable 'location' in SimpleHologram#SimpleHologram(name, lines, location) cannot be null.");

        this.id = id;
        this.lines = lines;
        this.location = location;
        this.disabled = disabled;
        this.entities = new ArrayList<>();

        if (!this.disabled) {
            this.spawnAssociatedEntities();
        }
    }

    public static Hologram.Builder builder() {
        return new Builder();
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public List<Text> getLines() {
        return this.lines;
    }

    @Override
    public Location<World> getLocation() {
        return this.location;
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }

    @Override
    public List<UUID> getAssociatedEntities() {
        return this.entities;
    }

    @Override
    public void spawnAssociatedEntities() {
        Optional<DataManipulatorBuilder<HologramData, ImmutableHologramData>> hologramDataBuilder =
                Sponge.getDataManager().getManipulatorBuilder(HologramData.class);

        if (hologramDataBuilder.isPresent()) {
            for (int index = 0; index < this.lines.size(); index++) {
                Text currentLine = this.lines.get(index);

                double y = this.lines.size() * SPACE_BETWEEN_LINES - index * SPACE_BETWEEN_LINES - 0.5;
                Entity armorStand = this.getLocation().getExtent().createEntity(EntityTypes.ARMOR_STAND,
                        LocationUtil.getBlockMiddle(this.location.getBlockPosition()).add(0, y, 0));

                // offer data to armor stand
                armorStand.offer(Keys.DISPLAY_NAME, currentLine);
                armorStand.offer(Keys.CUSTOM_NAME_VISIBLE, true);
                armorStand.offer(Keys.ARMOR_STAND_MARKER, true);
                armorStand.offer(Keys.INVISIBLE, true);
                armorStand.offer(Keys.HAS_GRAVITY, false);

                // offer custom data to identify hologram entities

                HologramData hologramData = hologramDataBuilder.get().create();
                hologramData.set(FelogramsKeys.IS_HOLOGRAM, true);
                armorStand.offer(hologramData);

                this.entities.add(armorStand.getUniqueId());
                this.location.getExtent().spawnEntity(armorStand);
            }

            this.disabled = false; // if all entities are present the hologram is showed and as result the hologram is enabled.
        } else {
            Felograms.getInstance().getLogger().error("Failed to retrieve the DataManipulatorBuilder for HologramData!");
        }
    }

    @Override
    public void removeAssociatedEntities() {
        Iterator<UUID> iterator = this.entities.iterator();

        while (iterator.hasNext()) {
            UUID uuid = iterator.next();

            Optional<Entity> entity = this.location.getExtent().getEntity(uuid);
            if (entity.isPresent()) {
                entity.get().remove();
                iterator.remove();
            }
        }

        this.disabled = true; // if no entity is present the hologram is automatically disabled because it's not showed anymore
    }

    @Override
    public void forceRespawnAssociatedEntities() {
        this.removeAssociatedEntities();
        this.spawnAssociatedEntities();
    }

    protected static class Builder implements Hologram.Builder {

        private HologramManager hologramManager;
        private String id;
        private List<Text> lines;
        private Location<World> location;
        private boolean disabled = false;

        private Builder() {
            this.lines = new ArrayList<>();
        }

        @Override
        public Hologram.Builder setManager(HologramManager hologramManager) {
            this.hologramManager = checkNotNull(hologramManager, "The variable 'hologramManager' in Builder#setManager(hologramManager) cannot be null.");

            return this;
        }

        @Override
        public Hologram.Builder setID(String id) {
            this.id = checkNotNull(id, "The variable 'id' in Builder#id(id) cannot be null.");

            return this;
        }

        @Override
        public Optional<String> getID() {
            return Optional.ofNullable(this.id);
        }

        @Override
        public Hologram.Builder line(Text line) {
            checkNotNull(line, "The variable 'line' in Builder#line(line) cannot be null.");

            this.lines.add(line);

            return this;
        }

        @Override
        public Hologram.Builder setLines(List<Text> lines) {
            this.lines = checkNotNull(lines, "The variable 'lines' in Builder#lines(lines) cannot be null.");
            ;

            return this;
        }

        @Override
        public List<Text> getLines() {
            return this.lines;
        }

        @Override
        public Hologram.Builder setLocation(Location<World> location) {
            this.location = checkNotNull(location, "The variable 'location' in Builder#location(location) cannot be null.");
            ;

            return this;
        }

        @Override
        public Hologram.Builder setDisabled(boolean disabled) {
            this.disabled = disabled;

            return this;
        }

        @Override
        public Hologram build() {
            checkNotNull(this.id, "The variable 'id' in Builder#build() cannot be null.");
            checkNotNull(this.lines, "The variable 'lines' in Builder#build() cannot be null.");
            checkNotNull(this.location, "The variable 'location' in Builder#build() cannot be null.");

            return new SimpleHologram(this.id, this.lines, this.location, this.disabled);
        }

        @Override
        public Hologram buildAndRegister() {
            checkNotNull(this.hologramManager, "The variable 'this.hologramManager' in Builder#buildAndRegister() cannot be null.");

            Hologram hologram = this.build();
            this.hologramManager.addHologram(hologram);

            return hologram;
        }

        @Override
        public Hologram.Builder from(Hologram hologram) {
            this.id = hologram.getID();
            this.lines = hologram.getLines();
            this.location = hologram.getLocation();

            return this;
        }

        @Override
        public Hologram.Builder reset() {
            this.id = null;
            this.lines = null;
            this.location = null;
            this.disabled = false;

            return this;
        }

    }

}
