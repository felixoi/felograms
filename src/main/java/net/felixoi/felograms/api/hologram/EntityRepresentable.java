package net.felixoi.felograms.api.hologram;

import net.felixoi.felograms.api.exception.WorldNotFoundException;

import java.util.List;
import java.util.UUID;

/**
 * An object which can be represented by entities.
 */
public interface EntityRepresentable {

    /**
     * Gets a list of all unique id's of entities which are representatives.
     *
     * @return the list of entity uuid's
     */
    List<UUID> getAssociatedEntities();

    /**
     * Spawns all associated entities.
     *
     * @throws WorldNotFoundException if the world of the associated entities is not found.
     */
    void spawnAssociatedEntities() throws WorldNotFoundException;

    /**
     * Removes all associated entities.
     *
     * @throws WorldNotFoundException if the world of the associated entities is not found.
     */
    void removeAssociatedEntities() throws WorldNotFoundException;

    /**
     * Returns whether all associated entities have been removed.
     *
     * @return true if all associated entities has been removed.
     */
    boolean areAssociatedEntitiesRemoved();

}
