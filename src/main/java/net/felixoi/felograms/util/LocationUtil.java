package net.felixoi.felograms.util;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocationUtil {

    public static Location<World> transformToBlockMiddle(Location<World> location) {
        checkNotNull(location, "The location object in LocationUtil#transformToBlockMiddle(Location<World>) cannot be null.");

        Vector3i block = location.getBlockPosition();
        Vector3d newPosition = Vector3d.from(block.getX() + 0.5F, block.getY(), block.getZ() + 0.5F);

        return location.setPosition(newPosition);
    }

}
