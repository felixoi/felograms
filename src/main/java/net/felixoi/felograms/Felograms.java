package net.felixoi.felograms;

import com.google.inject.Inject;
import net.felixoi.felograms.api.command.Command;
import net.felixoi.felograms.api.configuration.SimpleConfiguration;
import net.felixoi.felograms.api.data.HologramData;
import net.felixoi.felograms.api.data.ImmutableHologramData;
import net.felixoi.felograms.api.hologram.HologramCreationManager;
import net.felixoi.felograms.api.hologram.HologramManager;
import net.felixoi.felograms.api.hologram.HologramStore;
import net.felixoi.felograms.command.FelogramsCommand;
import net.felixoi.felograms.data.FelogramsHologramDataBuilder;
import net.felixoi.felograms.hologram.SimpleHologramCreationManager;
import net.felixoi.felograms.hologram.SimpleHologramManager;
import net.felixoi.felograms.hologram.store.FileConfigurationHologramStore;
import net.felixoi.felograms.listener.ListenerRegistry;
import net.felixoi.felograms.util.ConfigurationUtil;
import net.felixoi.felograms.util.LocaleUtil;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

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

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    private HologramManager hologramManager;
    private HologramStore hologramStore;
    private HologramCreationManager hologramCreationManager;
    private Path picturesDirectory;

    public static Felograms getInstance() {
        return INSTANCE;
    }

    @Listener
    public void onInit(GameInitializationEvent event) {
        INSTANCE = this;

        LocaleUtil.initialize("en");

        DataRegistration.builder()
                .dataClass(HologramData.class)
                .immutableClass(ImmutableHologramData.class)
                .builder(new FelogramsHologramDataBuilder())
                .manipulatorId("felogram-data")
                .dataName("Felograms Data")
                .buildAndRegister(this.pluginContainer);

        Sponge.getDataManager().registerBuilder(HologramData.class, new FelogramsHologramDataBuilder());

        ListenerRegistry.registerListeners(this.pluginContainer);

        this.picturesDirectory = this.configDir.resolve("images");
        this.createPicturesDirectory();
    }

    @Listener(order = Order.EARLY)
    public void onServerStart(GameStartingServerEvent event) {
        this.hologramStore = new FileConfigurationHologramStore(SimpleConfiguration.builder()
                .setPath(this.configDir.resolve("data.conf"))
                .setSerializerCollection(ConfigurationUtil.getStandardSerializers())
                .build());

        this.hologramManager = new SimpleHologramManager(this.hologramStore);
        this.hologramCreationManager = new SimpleHologramCreationManager();

        Command command = new FelogramsCommand();
        Sponge.getCommandManager().register(this.pluginContainer, command.getCommandSpec(), command.getAliases());

        this.logger.info("Started successfully!");
    }

    public Logger getLogger() {
        return this.logger;
    }

    public HologramManager getHologramManager() {
        return this.hologramManager;
    }

    public HologramCreationManager getHologramCreationManager() {
        return this.hologramCreationManager;
    }

    public HologramStore getHologramStore() {
        return this.hologramStore;
    }

    public Path getPicturesDirectory() {
        return this.picturesDirectory;
    }

    private void createPicturesDirectory() {
        if (!Files.exists(this.picturesDirectory)) {
            try {
                Files.createDirectories(this.picturesDirectory);
            } catch (IOException e) {
                this.logger.error("Couldn't create the picture directory! Stacktrace:");
                e.printStackTrace();
            }
        }
    }

}
