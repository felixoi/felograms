package net.felixoi.felograms.hologram;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public final class SimpleHologramService implements HologramService {

    @Override
    public List<Hologram> getHolograms() {
        this.checkAvailability();

        return new ArrayList<>(Felograms.getInstance().getHologramStore().getAll());
    }

    @Override
    public Optional<Hologram> getHologram(UUID uuid) {
        this.checkAvailability();

        return Felograms.getInstance().getHologramStore().get(uuid);
    }

    @Override
    public Hologram createHologram(List<Text> lines, Location<World> location) {
        this.checkAvailability();

        return new SimpleHologram(lines, location);
    }

    private void checkAvailability() {
        if (Felograms.getInstance().getHologramStore() == null) {
            throw new NullPointerException("Sorry! HologramService is not fully operational before GameStartingServerEvent!");
        }
    }

}
