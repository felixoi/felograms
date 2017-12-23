package net.felixoi.felograms.api.configuration.serializer;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;

public class Vector3dTypeSerializer implements TypeSerializer<Vector3d> {

    @Override
    public Vector3d deserialize(TypeToken<?> type, ConfigurationNode value) throws ObjectMappingException {
        return Vector3d.from(value.getNode("x").getDouble(), value.getNode("y").getDouble(), value.getNode("z").getDouble());
    }

    @Override
    public void serialize(TypeToken<?> type, Vector3d obj, ConfigurationNode value) throws ObjectMappingException {
        value.getNode("x").setValue(obj.getX());
        value.getNode("y").setValue(obj.getY());
        value.getNode("z").setValue(obj.getZ());
    }

}
