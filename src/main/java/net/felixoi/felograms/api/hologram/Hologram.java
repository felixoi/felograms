package net.felixoi.felograms.api.hologram;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.Identifiable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.UUID;

/**
 * Represents a three-dimensional text (or image) which is represented by entities.
 * <p>
 * <p>
 * Every line of the text is represented by one entity (usually {@link ArmorStand}s).
 * </p>
 */
public interface Hologram extends Identifiable, EntityRepresentable {

    /**
     * Gets an human-readable name for this hologram if set.
     * <p>
     * <p>
     *     The hologram name should be unique,
     *     if no name is set the uuid will be returned.
     * </p>
     *
     * @return the name if present, otherwise the uuid
     */
    String getName();

    /**
     * Sets the name of the hologram.
     *
     * @param name the new name
     */
    void setName(String name);

    /**
     * Gets all lines of the hologram.
     *
     * @return the lines
     */
    List<Text> getLines();

    /**
     * Sets the lines of the hologram.
     *
     * @param lines the new lines
     */
    void setLines(List<Text> lines);

    /**
     * Gets the unique id of the world the hologram is located in.
     *
     * @return the unique id of the world
     */
    UUID getWorldUniqueID();

    /**
     * Gets the position of the hologram.
     *
     * @return the position
     */
    Vector3d getPosition();

    /**
     * Sets the location of the hologram.
     * <p>
     * <p>
     * The location of a hologram will not be saved as a {@link Location} object.
     * It will be split up into the {@link World}s {@link UUID} ({@link Location#getExtent()#getUniqueId()})
     * and the position ({@link Location#getPosition()}) in this {@link World}.
     * </p>
     *
     * @param location the new location
     */
    void setLocation(Location<World> location);

    /**
     * Returns whether the hologram is disabled.
     * <p>
     * <p>
     * A disabled hologram won't have any associated entities,
     * but it will stay saved.
     * </p>
     *
     * @return true if the hologram is disabled
     */
    boolean isDisabled();

    /**
     * Sets whether the hologram is disabled.
     * <p>
     * <p>
     * {@link EntityRepresentable#spawnAssociatedEntities()} will automatically set this to false
     * since it spawns the associated entities.
     * Likewise, {@link EntityRepresentable#removeAssociatedEntities()} will set this to true.
     * </p>
     *
     * @param disabled true if the hologram should be disabled
     */
    void setDisabled(boolean disabled);

    /**
     * Returns whether the hologram is removed and is not saved anymore.
     *
     * @return true if the hologram is removed
     */
    boolean isRemoved();

    /**
     * Kills all associated entities and removes it from the store.
     */
    void remove();

}
