package net.felixoi.felograms;

import com.google.inject.Inject;
import net.felixoi.felograms.api.hologram.HologramManager;
import net.felixoi.felograms.command.CommandRegistry;
import net.felixoi.felograms.impl.SimpleHologramManager;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

@Plugin(
        id = "felograms",
        name = "Felograms",
        description = "A simple hologram plugin",
        authors = {
                "felixoi"
        }
)
public class Felograms {

    private static Felograms INSTANCE;

    @Inject
    private Logger logger;

    @Inject
    private PluginContainer pluginContainer;

    private HologramManager hologramManager;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        INSTANCE = this;

        CommandRegistry.registerCommands(pluginContainer);

        this.hologramManager = new SimpleHologramManager(this.pluginContainer);
        this.logger.info("Started successfully!");
    }

    public static Felograms getInstance() {
        return INSTANCE;
    }

    public HologramManager getHologramManager() {
        return this.hologramManager;
    }
}
