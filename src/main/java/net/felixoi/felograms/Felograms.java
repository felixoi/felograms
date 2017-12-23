package net.felixoi.felograms;

import com.google.inject.Inject;
import net.felixoi.felograms.api.configuration.Configuration;
import net.felixoi.felograms.api.data.HologramData;
import net.felixoi.felograms.api.data.ImmutableHologramData;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramCreationManager;
import net.felixoi.felograms.api.hologram.HologramManager;
import net.felixoi.felograms.api.hologram.HologramStore;
import net.felixoi.felograms.command.FelogramsCommand;
import net.felixoi.felograms.configuration.SimpleConfiguration;
import net.felixoi.felograms.data.FelogramsHologramData;
import net.felixoi.felograms.data.FelogramsHologramDataBuilder;
import net.felixoi.felograms.data.ImmutableFelogramsHologramData;
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
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
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

    @Listener
    public void onInit(GameInitializationEvent event) {
        DataRegistration.builder()
                .dataClass(HologramData.class)
                .immutableClass(ImmutableHologramData.class)
                .builder(new FelogramsHologramDataBuilder())
                .manipulatorId("felogram-data")
                .dataName("Felograms Data")
                .buildAndRegister(this.pluginContainer);

        Sponge.getDataManager().registerBuilder(HologramData.class, new FelogramsHologramDataBuilder());
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        INSTANCE = this;

        this.picturesDirectory = this.configDir.resolve("images");
        this.createPicturesDirectory();

        LocaleUtil.initialize("en");

        Configuration dataConfig = SimpleConfiguration.builder()
                .setPath(this.configDir.resolve("data.conf"))
                .setSerializerCollection(ConfigurationUtil.getStandardSerializers())
                .build();

        this.hologramStore = new FileConfigurationHologramStore(dataConfig);

        this.hologramManager = new SimpleHologramManager(this.hologramStore);
        this.hologramCreationManager = new SimpleHologramCreationManager();

        ListenerRegistry.registerListeners(this.pluginContainer);
        Sponge.getCommandManager().register(this.pluginContainer, new FelogramsCommand().getCommandSpec(), "felograms");

        this.logger.info("Started successfully!");
    }

    public static Felograms getInstance() {
        return INSTANCE;
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
