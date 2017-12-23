package net.felixoi.felograms.api.configuration;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import org.spongepowered.api.util.ResettableBuilder;

import java.nio.file.Path;

public interface Configuration {

    Path getPath();

    TypeSerializerCollection getSerializers();

    CommentedConfigurationNode getRoot();

    void save();

    interface Builder extends ResettableBuilder<Configuration, Builder> {

        Builder setPath(Path path);

        Builder setSerializerCollection(TypeSerializerCollection typeSerializerCollection);

        Configuration build();

    }

}
