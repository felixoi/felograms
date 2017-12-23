package net.felixoi.felograms.listener;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.plugin.PluginContainer;

public class ListenerRegistry {

    public static void registerListeners(PluginContainer pluginContainer) {
        Sponge.getEventManager().registerListeners(pluginContainer, new ChatListener());
        Sponge.getEventManager().registerListeners(pluginContainer, new SendCommandListener());
        Sponge.getEventManager().registerListeners(pluginContainer, new EntityDamageListener());
        Sponge.getEventManager().registerListeners(pluginContainer, new HologramEntityListeners());
    }

}
