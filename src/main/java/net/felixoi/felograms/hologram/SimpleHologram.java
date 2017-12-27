package net.felixoi.felograms.hologram;

import com.flowpowered.math.vector.Vector3d;
import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.data.FelogramsKeys;
import net.felixoi.felograms.api.data.HologramData;
import net.felixoi.felograms.api.data.ImmutableHologramData;
import net.felixoi.felograms.api.exception.WorldNotFoundException;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.internal.hologram.HologramManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleHologram implements Hologram {

    private static double SPACE_BETWEEN_LINES = 0.25;

    private HologramManager hologramManager;

    private UUID uuid;
    private String name;
    private List<Text> lines;
    private UUID worldUUID;
    private Vector3d position;
    private boolean disabled;
    private List<UUID> entities;
    private boolean removed;

    public SimpleHologram(HologramManager hologramManager, UUID uuid, String name, List<Text> lines, UUID worldUUID, Vector3d position, boolean disabled) {
        this.hologramManager = checkNotNull(hologramManager, "The variable 'hologramManager' in SimpleHologram#SimpleHologram cannot be null.");
        this.worldUUID = checkNotNull(worldUUID, "The variable 'worldUUID' in SimpleHologram#SimpleHologram cannot be null.");
        this.position = checkNotNull(position, "The variable 'position' in SimpleHologram#SimpleHologram cannot be null.");
        this.lines = checkNotNull(lines, "The variable 'lines' in SimpleHologram#SimpleHologram cannot be null.");
        this.uuid = checkNotNull(uuid, "The variable 'uuid' in SimpleHologram#SimpleHologram cannot be null.");
        this.disabled = disabled;

        this.removed = false;
        this.entities = new ArrayList<>();

        this.name = name != null ? name : this.uuid.toString();

        this.hologramManager.addHologram(this);
    }

    public SimpleHologram(HologramManager hologramManager, String name, List<Text> lines, UUID worldUUID, Vector3d position) {
        this(hologramManager, UUID.randomUUID(), name, lines, worldUUID, position, true);
    }

    public SimpleHologram(HologramManager hologramManager, String name, List<Text> lines, Location<World> location) {
        this(hologramManager, name, lines, location.getExtent().getUniqueId(), location.getPosition());
    }

    public SimpleHologram(HologramManager hologramManager, List<Text> lines, Location<World> location) {
        this(hologramManager, null, lines, location);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = checkNotNull(name, "The variable 'name' in SimpleHologram#setName cannot be null.");
    }

    @Override
    public List<Text> getLines() {
        return this.lines;
    }

    @Override
    public void setLines(List<Text> lines) {
        this.lines = checkNotNull(lines, "The variable 'lines' in SimpleHologram#setLines cannot be null.");
    }

    @Override
    public UUID getWorldUniqueID() {
        return this.worldUUID;
    }

    @Override
    public Vector3d getPosition() {
        return this.position;
    }

    @Override
    public void setLocation(Location<World> location) {
        checkNotNull(location, "The variable 'location' in SimpleHologram#setLocation cannot be null.");

        this.removeAssociatedEntities();

        this.worldUUID = location.getExtent().getUniqueId();
        this.position = location.getPosition();

        this.spawnAssociatedEntities();
    }

    @Override
    public boolean isDisabled() {
        return this.disabled;
    }

    @Override
    public void setDisabled(boolean disabled) {
        if (disabled) {
            this.removeAssociatedEntities();
            this.disabled = true;
        } else {
            this.spawnAssociatedEntities();
            this.disabled = false;
        }
    }

    @Override
    public boolean isRemoved() {
        return this.removed;
    }

    @Override
    public void remove() {
        if (!this.disabled) {
            this.setDisabled(true);
        }

        this.hologramManager.removeHologram(this.uuid);
    }

    @Override
    public List<UUID> getAssociatedEntities() {
        return this.entities;
    }

    @Override
    public void spawnAssociatedEntities() throws WorldNotFoundException {
        Optional<DataManipulatorBuilder<HologramData, ImmutableHologramData>> hologramDataBuilder =
                Sponge.getDataManager().getManipulatorBuilder(HologramData.class);

        if (hologramDataBuilder.isPresent()) {
            Optional<World> world = Sponge.getServer().getWorld(this.worldUUID);

            if (world.isPresent()) {
                Location<World> location = new Location<>(world.get(), this.position);

                for (int index = 0; index < this.lines.size(); index++) {
                    Text currentLine = this.lines.get(index);

                    double y = this.lines.size() * SPACE_BETWEEN_LINES - index * SPACE_BETWEEN_LINES - 0.5;
                    Entity armorStand = location.getExtent().createEntity(EntityTypes.ARMOR_STAND,
                            this.position.add(0, y, 0));

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
                    location.getExtent().spawnEntity(armorStand);
                }

                this.disabled = false;
            } else {
                throw new WorldNotFoundException(this.worldUUID.toString());
            }
        } else {
            Felograms.getInstance().getLogger().error("Failed to retrieve the DataManipulatorBuilder for HologramData." +
                    "\nAbandoned to spawnAssociatedEntities entities for hologram " + this.uuid + "!");
        }
    }

    @Override
    public void removeAssociatedEntities() throws WorldNotFoundException {
        Optional<World> world = Sponge.getServer().getWorld(this.worldUUID);

        if (world.isPresent()) {
            Location<World> location = new Location<>(world.get(), this.position);
            Iterator<UUID> iterator = this.entities.iterator();

            while (iterator.hasNext()) {
                UUID uuid = iterator.next();

                Optional<Entity> entity = location.getExtent().getEntity(uuid);
                if (entity.isPresent()) {
                    entity.get().remove();
                    iterator.remove();
                }
            }

            this.disabled = true;
        } else {
            throw new WorldNotFoundException(this.worldUUID.toString());
        }
    }

    @Override
    public boolean areAssociatedEntitiesRemoved() {
        return this.disabled;
    }

    @Override
    public UUID getUniqueId() {
        return this.uuid;
    }

}
