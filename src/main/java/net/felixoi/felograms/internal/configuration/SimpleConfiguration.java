package net.felixoi.felograms.internal.configuration;

import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleConfiguration implements Configuration {

    private final Path path;
    private final ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode rootNode;
    private ConfigurationOptions options;

    private SimpleConfiguration(Path path, ConfigurationOptions options) {
        this.path = checkNotNull(path, "The variable 'path' in SimpleConfiguration#SimpleConfiguration cannot be null.");
        this.options = checkNotNull(options, "The variable 'options' in SimpleConfiguration#SimpleConfiguration cannot be null.");

        this.createFile();

        this.loader = HoconConfigurationLoader.builder().setPath(this.path).build();

        try {
            this.rootNode = this.loader.load(this.options);
        } catch (IOException e) {
            this.rootNode = this.loader.createEmptyNode(this.options);
        }

        this.save();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void createFile() {
        if (!Files.exists(this.path)) {
            try {
                Files.createDirectories(this.path.getParent());
                Files.createFile(this.path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Path getPath() {
        return this.path;
    }

    @Override
    public ConfigurationOptions getConfigurationOptions() {
        return this.options;
    }

    @Override
    public CommentedConfigurationNode getRoot() {
        return this.rootNode;
    }

    @Override
    public void save() {
        try {
            this.loader.save(this.rootNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final class Builder implements Configuration.Builder {

        private Path path;
        private ConfigurationOptions options;

        private Builder() {
            this.reset();
        }

        @Override
        public Configuration.Builder setPath(Path path) {
            this.path = checkNotNull(path, "The variable 'path' in Builder#setPath cannot be null.");

            return this;
        }

        @Override
        public Configuration.Builder setOptions(ConfigurationOptions options) {
            this.options = checkNotNull(options, "The variable 'options' in Builder#setOptions cannot be null.");

            return this;
        }

        @Override
        public Configuration build() {
            checkNotNull(this.path, "The variable 'path' in Builder#build cannot be null.");

            return new SimpleConfiguration(this.path, this.options);
        }

        @Override
        public Configuration.Builder from(Configuration configuration) {
            this.path = configuration.getPath();

            return this;
        }

        @Override
        public Configuration.Builder reset() {
            this.path = null;
            this.options = ConfigurationOptions.defaults();

            return this;
        }

    }

}
