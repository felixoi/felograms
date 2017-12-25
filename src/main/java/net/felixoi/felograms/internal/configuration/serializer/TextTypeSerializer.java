package net.felixoi.felograms.internal.configuration.serializer;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

public class TextTypeSerializer implements TypeSerializer<Text> {

    @Override
    public Text deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        return TextSerializers.FORMATTING_CODE.deserialize(value.getString());
    }

    @Override
    public void serialize(TypeToken<?> type, Text text, ConfigurationNode value) throws ObjectMappingException {
        value.setValue(TextSerializers.FORMATTING_CODE.serialize(text));
    }

}
