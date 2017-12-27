package net.felixoi.felograms.hologram;

import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.internal.hologram.HologramManager;
import net.felixoi.felograms.internal.hologram.HologramStore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleHologramManager implements HologramManager {

    private Map<UUID, Hologram> holograms;

    public SimpleHologramManager() {
        this.holograms = new HashMap<>();
    }

    @Override
    public List<Hologram> getHolograms() {
        return new ArrayList<>(this.holograms.values());
    }

    @Override
    public void addHologram(Hologram hologram) {
        checkNotNull(hologram, "The variable 'hologram' in SimpleHologramManager#addHologram cannot be null.");

        this.holograms.put(hologram.getUniqueId(), hologram);
    }

    @Override
    public Optional<Hologram> getHologram(UUID uuid) {
        return this.holograms.containsKey(uuid) ? Optional.of(this.holograms.get(uuid)) : Optional.empty();
    }

    @Override
    public Optional<Hologram> getHologram(String name) {
        checkNotNull(name, "The variable 'name' in SimpleHologramManager#getHologram cannot be null.");

        return this.holograms.values().stream().filter(hologram -> hologram.getName().equalsIgnoreCase(name)).findFirst();
    }

    @Override
    public void removeHologram(UUID uuid) {
        checkNotNull(uuid, "The variable 'uuid' in SimpleHologramManager#removeHologram cannot be null.");

        this.holograms.remove(uuid);
    }

    @Override
    public void load(HologramStore store) {
        store.loadHolograms().forEach(hologram -> {
            this.holograms.put(hologram.getUniqueId(), hologram);

            if (!hologram.isDisabled()) {
                hologram.spawnAssociatedEntities();
            }
        });
    }

    @Override
    public void save(HologramStore store) {
        store.saveHolograms(this.getHolograms());
    }

}
