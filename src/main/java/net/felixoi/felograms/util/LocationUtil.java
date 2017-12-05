package net.felixoi.felograms.util;

import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import static com.google.common.base.Preconditions.checkNotNull;

public class LocationUtil {

    public static Vector3d getBlockMiddle(Vector3i blockPosition) {
        checkNotNull(blockPosition, "The location object in LocationUtil#getBlockMiddle(Location<World>) cannot be null.");

        return Vector3d.from(blockPosition.getX() + 0.5F, blockPosition.getY(), blockPosition.getZ() + 0.5F);
    }

}
