package net.felixoi.felograms.internal.hologram;

import net.felixoi.felograms.api.hologram.Hologram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class HologramStore {

    private Map<UUID, Hologram> holograms;

    public HologramStore() {
        this.holograms = new HashMap<>();
    }

    public void initialize() {
        this.load().forEach(hologram -> this.holograms.put(hologram.getUniqueId(), hologram));
    }

    public List<Hologram> getAll() {
        return new ArrayList<>(this.holograms.values());
    }

    public Optional<Hologram> get(UUID uuid) {
        checkNotNull(uuid, "The variable 'uuid' in HologramStore#get cannot be null.");

        return this.holograms.containsKey(uuid) ? Optional.of(this.holograms.get(uuid)) : Optional.empty();
    }

    public Optional<Hologram> get(String name) {
        checkNotNull(name, "The variable 'name' in HologramStore#get cannot be null.");

        return this.holograms.values().stream().filter(hologram -> hologram.getName().equalsIgnoreCase(name)).findFirst();
    }

    public void add(Hologram hologram, boolean save) {
        checkNotNull(hologram, "The variable 'hologram' in HologramStore#add cannot be null.");

        this.holograms.put(hologram.getUniqueId(), hologram);
        if(save) {
            this.save();
        }
    }

    public void add(Hologram hologram) {
        this.add(hologram, true);
    }

    public void update(Hologram hologram) {
        checkNotNull(hologram, "The variable 'hologram' in HologramStore#update cannot be null.");

        this.holograms.put(hologram.getUniqueId(), hologram);
        this.save();
    }

    public void remove(UUID hologramUUID) {
        checkNotNull(hologramUUID, "The variable 'hologramUUID' in HologramStore#remove cannot be null.");

        this.holograms.remove(hologramUUID);
        this.save();
    }

    public void save() {
        this.saveHolograms(this.getAll());
    }

    public abstract List<Hologram> load();

    protected abstract void saveHolograms(List<Hologram> holograms);

}
