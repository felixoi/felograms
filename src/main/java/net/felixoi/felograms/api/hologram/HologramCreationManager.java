package net.felixoi.felograms.api.hologram;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;

public interface HologramCreationManager {

    Map<UUID, Hologram.Builder> getCreations();

    Set<UUID> getCreators();

    Optional<Hologram.Builder> getCreation(UUID uuid);

    void startCreation(UUID uuid, String hologramID);

    void stopCreation(UUID uuid);

    void registerProcessor(HologramCreationProcessor processor);

    List<HologramCreationProcessor> getProcessors();

    void process(HologramCreationProcessor processor, UUID uuid, Player player, String arguments, Location<World> location);

}
