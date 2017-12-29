package net.felixoi.felograms;

import com.google.common.reflect.TypeToken;
import com.google.inject.Inject;
import net.felixoi.felograms.api.hologram.HologramService;
import net.felixoi.felograms.command.FelogramsCommand;
import net.felixoi.felograms.configuration.MainConfiguration;
import net.felixoi.felograms.data.HologramData;
import net.felixoi.felograms.data.HologramDataBuilder;
import net.felixoi.felograms.data.ImmutableHologramData;
import net.felixoi.felograms.hologram.SimpleHologramService;
import net.felixoi.felograms.hologram.creation.HologramCreationProcessorRegistry;
import net.felixoi.felograms.hologram.creation.SimpleHologramCreationManager;
import net.felixoi.felograms.hologram.store.FileConfigurationHologramStore;
import net.felixoi.felograms.internal.command.Command;
import net.felixoi.felograms.internal.configuration.Configuration;
import net.felixoi.felograms.internal.configuration.SimpleConfiguration;
import net.felixoi.felograms.internal.hologram.HologramStore;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationManager;
import net.felixoi.felograms.listener.ListenerRegistry;
import net.felixoi.felograms.util.ConfigurationUtil;
import net.felixoi.felograms.util.LocaleUtil;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.objectmapping.GuiceObjectMapperFactory;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Plugin(id = "felograms", name = "Felograms", description = "A simple hologram plugin", authors = {"felixoi"})
public final class Felograms {

    private static Felograms INSTANCE;

    @Inject
    private Logger logger;
    @Inject
    private PluginContainer pluginContainer;
    @Inject
    private GuiceObjectMapperFactory mapperFactory;

    @Inject
    @ConfigDir(sharedRoot = false)
    private Path configDir;

    private MainConfiguration configuration;
    private HologramStore hologramStore;
    private HologramCreationManager hologramCreationManager;
    private Path picturesDirectory;

    @Listener
    public void onInit(GameInitializationEvent event) {

        // Instance
        INSTANCE = this;

        // Main Configuration
        Configuration config = SimpleConfiguration.builder()
                .setPath(this.configDir.resolve("config.conf"))
                .setOptions(ConfigurationOptions.defaults().setObjectMapperFactory(this.mapperFactory).setShouldCopyDefaults(true))
                .build();

        try {
            this.configuration = config.getRoot().getNode().getValue(TypeToken.of(MainConfiguration.class), new MainConfiguration());
            config.save();
        } catch (ObjectMappingException e) {
            this.logger.error("Failed to load configuration from file 'config.conf'!");
            e.printStackTrace();
        }

        // Language Settings and LocaleUtil
        LocaleUtil.initialize(this.configuration.getLocale());

        // Data Registration
        DataRegistration.builder()
                .dataClass(HologramData.class)
                .immutableClass(ImmutableHologramData.class)
                .builder(new HologramDataBuilder())
                .manipulatorId("hologram-data")
                .dataName("Hologram Data")
                .buildAndRegister(this.pluginContainer);

        // Listener Registration
        ListenerRegistry.registerListeners(this.pluginContainer);

        // Picture Directory
        this.picturesDirectory = this.configDir.resolve("images");
        this.createPicturesDirectory();

        // Service Registration
        Sponge.getServiceManager().setProvider(this, HologramService.class, new SimpleHologramService());

    }

    @Listener(order = Order.FIRST)
    public void onServerStart(GameStartingServerEvent event) {

        // Hologram Store
        this.hologramStore = new FileConfigurationHologramStore(SimpleConfiguration.builder()
                .setPath(this.configDir.resolve("data.conf"))
                .setOptions(ConfigurationOptions.defaults()
                        .setSerializers(ConfigurationUtil.getStandardSerializers())
                        .setObjectMapperFactory(this.mapperFactory))
                .build());
        this.hologramStore.initialize();

        // Creation Manager
        this.hologramCreationManager = new SimpleHologramCreationManager(this.hologramStore);
        HologramCreationProcessorRegistry.registerProcessors(this.hologramCreationManager);

        // Command Registration
        Command command = new FelogramsCommand();
        Sponge.getCommandManager().register(this.pluginContainer, command.getCommandSpec(), command.getAliases());

        this.logger.info("Started successfully!");

    }

    public static Felograms getInstance() {
        return INSTANCE;
    }

    public Logger getLogger() {
        return this.logger;
    }

    public MainConfiguration getConfiguration() {
        return this.configuration;
    }

    public HologramStore getHologramStore() {
        return this.hologramStore;
    }

    public HologramCreationManager getHologramCreationManager() {
        return this.hologramCreationManager;
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
