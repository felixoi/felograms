package net.felixoi.felograms.api.hologram;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Represents a service for managing holograms.
 */
public interface HologramService {

    /**
     * Gets all holograms which are created.
     *
     * @return the list of holograms
     */
    List<Hologram> getHolograms();

    /**
     * Gets a specific hologram by its unique id
     *
     * @param uuid the unique id
     * @return The hologram, if it exists
     */
    Optional<Hologram> getHologram(UUID uuid);

    /**
     * Creates a new hologram and returns it.
     *
     * @param lines    the lines
     * @param location the location
     * @return the created hologram instance
     */
    Hologram createHologram(List<Text> lines, Location<World> location);

}
