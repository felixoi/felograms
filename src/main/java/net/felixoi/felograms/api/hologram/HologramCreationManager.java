package net.felixoi.felograms.api.hologram;

import org.spongepowered.api.text.Text;

import java.util.*;

public interface HologramCreationManager {

    Set<UUID> getCreators();

    Collection<String> getCreationIDs();

    Optional<String> getCreationID(UUID uuid);

    Optional<List<Text>> getLines(String creationID);

    void setLines(String creationID, List<Text> lines);

    void startCreationProcess(UUID uuid, String id);

    void stopCreationProcess(UUID uuid);

}
