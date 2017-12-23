package net.felixoi.felograms.listener;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.data.FelogramsKeys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.Getter;

public class EntityDamageListener {

    @Listener
    public void onDamage(DamageEntityEvent event, @Getter("getTargetEntity") Entity entity) {
        if (entity.getType().equals(EntityTypes.ARMOR_STAND)) {
            Felograms.getInstance().getLogger().info(entity.get(FelogramsKeys.IS_HOLOGRAM).isPresent() + "");

            Felograms.getInstance().getHologramManager().getHolograms().forEach(hologram -> hologram.getAssociatedEntities().forEach(hologramEntity -> {
                if(entity.getUniqueId().equals(hologramEntity)) {
                    event.setCancelled(true);
                }
            }));
        }
    }

}
