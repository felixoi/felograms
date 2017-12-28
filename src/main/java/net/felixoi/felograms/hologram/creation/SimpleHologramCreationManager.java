package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.internal.hologram.HologramStore;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationBuilder;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationManager;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationProcessor;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleHologramCreationManager implements HologramCreationManager {

    private HologramStore hologramStore;
    private Map<UUID, HologramCreationBuilder> creations;
    private List<HologramCreationProcessor> processors;

    public SimpleHologramCreationManager(HologramStore hologramStore) {
        this.hologramStore = checkNotNull(hologramStore, "The variable 'hologramStore' in SimpleHologramCreationManager#SimpleHologramCreationManager cannot be null.");
        this.creations = new HashMap<>();
        this.processors = new ArrayList<>();
    }

    @Override
    public Map<UUID, HologramCreationBuilder> getCreations() {
        return this.creations;
    }

    @Override
    public Set<UUID> getCreators() {
        return this.creations.keySet();
    }

    @Override
    public Optional<HologramCreationBuilder> getCreation(UUID uuid) {
        checkNotNull(uuid, "The variable 'uuid' in SimpleHologramCreationManager#getCreation(uuid) cannot be null.");

        return Optional.ofNullable(this.creations.get(uuid));
    }

    @Override
    public void startCreation(UUID uuid, String hologramID) {
        checkNotNull(uuid, "The variable 'uuid' in SimpleHologramCreationManager#startCreation(uuid) cannot be null.");
        checkNotNull(hologramID, "The variable 'hologramID' in SimpleHologramCreationManager#startCreation(uuid, hologramID) cannot be null.");

        this.creations.put(uuid, new SimpleCreationBuilder(hologramID));
    }

    @Override
    public void stopCreation(UUID uuid) {
        checkNotNull(uuid, "The variable 'uuid' in SimpleHologramCreationManager#stopCreation(uuid) cannot be null.");

        this.creations.remove(uuid);
    }

    @Override
    public void registerProcessor(HologramCreationProcessor processor) {
        this.processors.add(checkNotNull(processor, "The variable 'processor' in SimpleHologramCreationManager#registerProcessor(processor) cannot be null."));
    }

    @Override
    public List<HologramCreationProcessor> getProcessors() {
        return this.processors;
    }

    @Override
    public void process(HologramCreationProcessor processor, Player player, String arguments) {
        checkNotNull(processor, "The variable 'processor' in SimpleHologramCreationManager#process cannot be null.");
        checkNotNull(player, "The variable 'player' in SimpleHologramCreationManager#process cannot be null.");
        checkNotNull(arguments, "The variable 'arguments' in SimpleHologramCreationManager#process cannot be null.");

        UUID uuid = player.getUniqueId();

        if (this.getCreation(uuid).isPresent()) {
            Optional<HologramCreationBuilder> builder = processor.processInput(this.getCreation(uuid).get(), player, arguments);

            builder.ifPresent(creation -> this.creations.put(uuid, creation));
        }
    }

}
