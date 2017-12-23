package net.felixoi.felograms.api.hologram;

import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface HologramCreationManager {

    Map<UUID, Hologram.Builder> getCreations();

    Set<UUID> getCreators();

    Optional<Hologram.Builder> getCreation(UUID uuid);

    void startCreation(UUID uuid, String hologramID);

    void stopCreation(UUID uuid);

    void process(HologramCreationProcessor processor, UUID uuid, MessageReceiver creator, String arguments, Location<World> location);

}
