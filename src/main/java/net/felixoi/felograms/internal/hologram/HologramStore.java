package net.felixoi.felograms.internal.hologram;

import net.felixoi.felograms.api.hologram.Hologram;

import java.util.Collection;

public interface HologramStore {

    Collection<Hologram> loadHolograms();

    void saveHolograms(Collection<Hologram> holograms);

}
