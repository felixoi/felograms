package net.felixoi.felograms.internal.hologram;

import net.felixoi.felograms.api.hologram.Hologram;

import java.util.List;

public interface HologramStore {

    List<Hologram> loadHolograms();

    void saveHolograms(List<Hologram> holograms);

}
