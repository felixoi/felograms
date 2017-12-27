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

public class SimpleHologramService implements HologramService {

    @Override
    public List<Hologram> getHolograms() {
        this.checkAvailability();

        return new ArrayList<>(Felograms.getInstance().getHologramManager().getHolograms());
    }

    @Override
    public Optional<Hologram> getHologram(UUID uuid) {
        this.checkAvailability();

        return Felograms.getInstance().getHologramManager().getHologram(uuid);
    }

    @Override
    public Hologram createHologram(List<Text> lines, Location<World> location) {
        this.checkAvailability();

        return new SimpleHologram(Felograms.getInstance().getHologramManager(), lines, location);
    }

    private void checkAvailability() {
        if (Felograms.getInstance().getHologramManager() == null) {
            throw new NullPointerException("Sorry! HologramService is not fully operational before GameStartingServerEvent!");
        }
    }

}
