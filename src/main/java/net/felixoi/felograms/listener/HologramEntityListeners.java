package net.felixoi.felograms.listener;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.data.FelogramKeys;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.world.World;

public final class HologramEntityListeners {

    @Listener(order = Order.LATE)
    public void onStarting(GameStartingServerEvent event) {
        Sponge.getServer().getWorlds().forEach(this::removeHologramEntities);

        Felograms.getInstance().getHologramStore().getAll().forEach(hologram -> {
            if (!hologram.isDisabled()) {
                hologram.spawnAssociatedEntities();
            }
        });
    }

    @Listener
    public void onStopping(GameStoppingServerEvent event) {
        Sponge.getServer().getWorlds().forEach(this::removeHologramEntities);
    }

    private void removeHologramEntities(World world) {
        world.getEntities().forEach(entity -> {
            if (entity.getType().equals(EntityTypes.ARMOR_STAND) && entity.get(FelogramKeys.IS_HOLOGRAM).isPresent()) {
                System.out.println("removed entity: " + entity.getUniqueId().toString());
                entity.remove();
            }
        });
    }

}
