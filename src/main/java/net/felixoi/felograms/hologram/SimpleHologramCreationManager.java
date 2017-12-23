package net.felixoi.felograms.hologram;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramCreationManager;
import net.felixoi.felograms.api.hologram.HologramCreationProcessor;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleHologramCreationManager implements HologramCreationManager {

    private Map<UUID, Hologram.Builder> creations;

    public SimpleHologramCreationManager() {
        this.creations = new HashMap<>();
    }

    @Override
    public Map<UUID, Hologram.Builder> getCreations() {
        return this.creations;
    }

    @Override
    public Set<UUID> getCreators() {
        return this.creations.keySet();
    }

    @Override
    public Optional<Hologram.Builder> getCreation(UUID uuid) {
        checkNotNull(uuid, "The variable 'uuid' in SimpleHologramCreationManager#getCreation(uuid) cannot be null.");

        return Optional.ofNullable(this.creations.get(uuid));
    }

    @Override
    public void startCreation(UUID uuid, String hologramID) {
        checkNotNull(uuid, "The variable 'uuid' in SimpleHologramCreationManager#startCreation(uuid) cannot be null.");
        checkNotNull(hologramID, "The variable 'hologramID' in SimpleHologramCreationManager#startCreation(uuid, hologramID) cannot be null.");

        this.creations.put(uuid, SimpleHologram.builder().setManager(Felograms.getInstance().getHologramManager()).setID(hologramID));
    }

    @Override
    public void stopCreation(UUID uuid) {
        checkNotNull(uuid, "The variable 'uuid' in SimpleHologramCreationManager#stopCreation(uuid) cannot be null.");

        this.creations.remove(uuid);
    }

    @Override
    public void process(HologramCreationProcessor processor, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        checkNotNull(processor, "The variable 'processor' in SimpleHologramCreationManager#process(processor, uuid, creator, arguments, location) cannot be null.");
        checkNotNull(uuid, "The variable 'uuid' in SimpleHologramCreationManager#process(processor, uuid, creator, arguments, location) cannot be null.");
        checkNotNull(creator, "The variable 'cr' in SimpleHologramCreationManager#process(processor, uuid, creator, arguments, location) cannot be null.");
        checkNotNull(arguments, "The variable 'arguments' in SimpleHologramCreationManager#process(processor, uuid, creator, arguments, location) cannot be null.");
        checkNotNull(location, "The variable 'location' in SimpleHologramCreationManager#process(processor, uuid, creator, arguments, location) cannot be null.");

        if (this.getCreation(uuid).isPresent()) {
            Optional<Hologram.Builder> builder = processor.process(this.getCreation(uuid).get(), uuid, creator, arguments, location);

            builder.ifPresent(creation -> this.creations.put(uuid, creation));
        }
    }

}
