package net.felixoi.felograms.api.hologram;

import java.util.Collection;

public interface HologramStore {

    Collection<Hologram> loadHolograms();

    void saveHolograms(Collection<Hologram> holograms);

}
