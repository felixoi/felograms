package net.felixoi.felograms.internal.configuration;

import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleConfiguration implements Configuration {

    private final Path path;
    private final ConfigurationLoader<CommentedConfigurationNode> loader;
    private CommentedConfigurationNode rootNode;
    private TypeSerializerCollection typeSerializerCollection;

    private SimpleConfiguration(Path path, TypeSerializerCollection typeSerializerCollection) {
        this.path = checkNotNull(path, "The variable 'path' in SimpleConfiguration#SimpleConfiguration(path, typeSerializerCollection) cannot be null.");
        this.typeSerializerCollection = checkNotNull(typeSerializerCollection, "The variable 'typeSerializerCollection' in SimpleConfiguration#SimpleConfiguration(path, typeSerializerCollection) cannot be null.");

        this.createFile();

        ConfigurationOptions options = ConfigurationOptions.defaults().setSerializers(this.typeSerializerCollection);

        this.loader = HoconConfigurationLoader.builder().setPath(this.path).build();

        try {
            this.rootNode = this.loader.load(options);
        } catch (IOException e) {
            this.rootNode = this.loader.createEmptyNode(options);
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
    public TypeSerializerCollection getSerializers() {
        return this.typeSerializerCollection;
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

    public static class Builder implements Configuration.Builder {

        private Path path;
        private TypeSerializerCollection serializers;

        private Builder() {
        }

        @Override
        public Configuration.Builder setPath(Path path) {
            this.path = checkNotNull(path, "The variable 'path' in Builder#setPath(path) cannot be null.");

            return this;
        }

        @Override
        public Configuration.Builder setSerializerCollection(TypeSerializerCollection typeSerializerCollection) {
            this.serializers = checkNotNull(typeSerializerCollection, "The variable 'typeSerializerCollection' in Builder#setSerializerCollection(typeSerializerCollection) cannot be null.");

            return this;
        }


        @Override
        public Configuration build() {
            checkNotNull(this.path, "The variable 'path' in Builder#build() cannot be null.");
            checkNotNull(this.serializers, "The variable 'this.serializers' in Builder#build() cannot be null.");

            return new SimpleConfiguration(this.path, this.serializers);
        }

        @Override
        public Configuration.Builder from(Configuration configuration) {
            this.path = configuration.getPath();
            this.serializers = configuration.getSerializers();

            return this;
        }

        @Override
        public Configuration.Builder reset() {
            this.path = null;
            this.serializers = null;

            return this;
        }

    }

}
