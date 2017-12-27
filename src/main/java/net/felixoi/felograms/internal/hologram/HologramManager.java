package net.felixoi.felograms.internal.hologram;

import net.felixoi.felograms.api.hologram.Hologram;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HologramManager {

    List<Hologram> getHolograms();

    void addHologram(Hologram hologram);

    Optional<Hologram> getHologram(UUID uuid);

    Optional<Hologram> getHologram(String name);

    void removeHologram(UUID uuid);

    void load(HologramStore store);

    void save(HologramStore store);

}
