package net.felixoi.felograms.impl;

import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramManager;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.extent.Extent;

import java.util.*;

public class SimpleHologramManager implements HologramManager {

    private PluginContainer pluginContainer;
    private Map<UUID, List<Entity>> entities;

    public SimpleHologramManager(PluginContainer pluginContainer) {
        Objects.requireNonNull(pluginContainer, "'pluginContainer' in net.felixoi.felograms cannot be null.");

        this.entities = new HashMap<>();
        this.pluginContainer = pluginContainer;
    }

    @Override
    public void spawnHologram(Hologram hologram) {
        Extent world = hologram.getLocation().getExtent();

        for(int index = 0; index < hologram.getLines().size(); index++) {
            Text currentLine = hologram.getLines().get(index);
            Entity armorStand = world.createEntity(EntityTypes.ARMOR_STAND, hologram.getLocation().getPosition().add(0,  hologram.getLines().size() * 0.25 - index * 0.25 - 0.5, 0));

            // offer data to armor stand
            armorStand.offer(Keys.DISPLAY_NAME, currentLine);
            armorStand.offer(Keys.CUSTOM_NAME_VISIBLE, true);
            armorStand.offer(Keys.ARMOR_STAND_MARKER, true);
            armorStand.offer(Keys.INVISIBLE, true);
            armorStand.offer(Keys.HAS_GRAVITY, false);

            if(!entities.containsKey(hologram.getUUID())) {
                this.entities.put(hologram.getUUID(), new ArrayList<>());
            }

            this.entities.get(hologram.getUUID()).add(armorStand);
        }

        SpawnCause spawnCause = SpawnCause.builder().type(SpawnTypes.PLUGIN).build();
        world.spawnEntities(this.entities.get(hologram.getUUID()), Cause.source(spawnCause).owner(this.pluginContainer).build());
    }

    @Override
    public void removeHologram(Hologram hologram) {

    }

}
