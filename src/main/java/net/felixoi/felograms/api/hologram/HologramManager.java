package net.felixoi.felograms.api.hologram;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface HologramManager {

    Collection<Hologram> getHolograms();

    Set<String> getHologramIDs();

    Collection<Hologram> getEnabledHolograms();

    void addHologram(Hologram hologram);

    Optional<Hologram> getHologram(String hologramID);

    void removeHologram(String hologramID);

    boolean isExistent(String hologramID);

}
