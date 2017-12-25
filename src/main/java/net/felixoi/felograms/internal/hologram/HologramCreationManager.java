package net.felixoi.felograms.internal.hologram;

import net.felixoi.felograms.api.hologram.Hologram;
import org.spongepowered.api.entity.living.player.Player;

import java.util.*;

public interface HologramCreationManager {

    Map<UUID, Hologram.Builder> getCreations();

    Set<UUID> getCreators();

    Optional<Hologram.Builder> getCreation(UUID uuid);

    void startCreation(UUID uuid, String hologramID);

    void stopCreation(UUID uuid);

    void registerProcessor(HologramCreationProcessor processor);

    List<HologramCreationProcessor> getProcessors();

    void process(HologramCreationProcessor processor, Player player, String arguments);

}
