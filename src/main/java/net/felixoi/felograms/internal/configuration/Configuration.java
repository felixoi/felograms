package net.felixoi.felograms.internal.configuration;

import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMapperFactory;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import org.spongepowered.api.util.ResettableBuilder;

import java.nio.file.Path;

public interface Configuration {

    Path getPath();

    ConfigurationOptions getConfigurationOptions();


    CommentedConfigurationNode getRoot();

    void save();

    interface Builder extends ResettableBuilder<Configuration, Builder> {

        Builder setPath(Path path);

        Builder setOptions(ConfigurationOptions options);

        Configuration build();

    }

}
