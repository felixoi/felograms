package net.felixoi.felograms.configuration.serializers;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

import java.util.UUID;

public class UUIDTypeSerializer implements TypeSerializer<UUID> {

    @Override
    public UUID deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        return UUID.fromString(value.getString());
    }

    @Override
    public void serialize(TypeToken<?> type, UUID uuid, ConfigurationNode value) throws ObjectMappingException {
        value.setValue(uuid.toString());
    }

}
