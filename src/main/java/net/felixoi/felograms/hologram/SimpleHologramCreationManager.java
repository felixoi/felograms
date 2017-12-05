package net.felixoi.felograms.hologram;

import net.felixoi.felograms.api.hologram.HologramCreationManager;
import org.spongepowered.api.text.Text;

import java.util.*;

public class SimpleHologramCreationManager implements HologramCreationManager {

    private Map<UUID, String> creators;
    private Map<String, List<Text>> creations;

    public SimpleHologramCreationManager() {
        this.creators = new HashMap<>();
        this.creations = new HashMap<>();
    }

    @Override
    public Set<UUID> getCreators() {
        return this.creators.keySet();
    }


    @Override
    public Collection<String> getCreationIDs() {
        return this.creators.values();
    }

    @Override
    public Optional<String> getCreationID(UUID uuid) {
        return Optional.ofNullable(this.creators.get(uuid));
    }

    @Override
    public Optional<List<Text>> getLines(String creationID) {
        return Optional.ofNullable(this.creations.get(creationID));
    }

    @Override
    public void setLines(String creationID, List<Text> lines) {
        this.creations.put(creationID, lines);
    }

    @Override
    public void startCreationProcess(UUID uuid, String id) {
        this.creators.put(uuid, id);
        this.creations.put(id, new ArrayList<>());
    }

    @Override
    public void stopCreationProcess(UUID uuid) {
        this.creators.remove(uuid);
    }

}
