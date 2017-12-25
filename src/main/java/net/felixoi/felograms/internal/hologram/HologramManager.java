package net.felixoi.felograms.internal.hologram;

import net.felixoi.felograms.api.hologram.Hologram;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface HologramManager {

    Collection<Hologram> getHolograms();

    Set<String> getHologramIDs();

    void addHologram(Hologram hologram);

    Optional<Hologram> getHologram(String hologramID);

    void removeHologram(String hologramID);

    boolean isExistent(String hologramID);

}
