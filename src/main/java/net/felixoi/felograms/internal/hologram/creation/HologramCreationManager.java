package net.felixoi.felograms.internal.hologram.creation;

import org.spongepowered.api.entity.living.player.Player;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface HologramCreationManager {

    Map<UUID, HologramCreationBuilder> getCreations();

    Set<UUID> getCreators();

    Optional<HologramCreationBuilder> getCreation(UUID uuid);

    void startCreation(UUID uuid, String hologramID);

    void stopCreation(UUID uuid);

    void registerProcessor(HologramCreationProcessor processor);

    List<HologramCreationProcessor> getProcessors();

    void process(HologramCreationProcessor processor, Player player, String arguments);

}
