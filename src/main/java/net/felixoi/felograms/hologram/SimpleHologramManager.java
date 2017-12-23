package net.felixoi.felograms.hologram;

import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramManager;
import net.felixoi.felograms.api.hologram.HologramStore;

import java.util.*;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleHologramManager implements HologramManager {

    private HologramStore hologramStore;
    private Map<String, Hologram> holograms;

    public SimpleHologramManager(HologramStore hologramStore) {
        this.hologramStore = checkNotNull(hologramStore, "The variable 'holograms' in SimpleHologramManager#SimpleHologramManager(holograms) cannot be null.");

        this.holograms = new HashMap<>();

        hologramStore.loadHolograms().forEach(hologram -> {
            this.holograms.put(hologram.getID(), hologram);
        });
    }

    @Override
    public Collection<Hologram> getHolograms() {
        return this.holograms.values();
    }

    @Override
    public Set<String> getHologramIDs() {
        return this.holograms.keySet();
    }

    @Override
    public Collection<Hologram> getEnabledHolograms() {
        return this.getHolograms().stream().filter(Hologram::isDisabled).collect(Collectors.toList());
    }

    @Override
    public void addHologram(Hologram hologram) {
        checkNotNull(hologram, "The variable 'hologram' in SimpleHologramManager#addHologram(hologram) cannot be null.");

        this.holograms.put(hologram.getID(), hologram);
        this.hologramStore.saveHolograms(this.holograms.values());
    }

    @Override
    public Optional<Hologram> getHologram(String hologramID) {
        if (this.isExistent(hologramID)) {
            return Optional.of(this.holograms.get(hologramID));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void removeHologram(String hologramID) {
        if (this.getHologram(hologramID).isPresent()) {
            this.getHologram(hologramID).get().removeAssociatedEntities();
        }

        this.holograms.remove(hologramID);
        this.hologramStore.saveHolograms(this.holograms.values());
    }

    @Override
    public boolean isExistent(String hologramID) {
        return this.holograms.containsKey(hologramID);
    }
}
