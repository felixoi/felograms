package net.felixoi.felograms.api.exception;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Thrown when trying to get an world which is not present.
 */
public class WorldNotFoundException extends NullPointerException {

    public WorldNotFoundException(String worldIdentifier) {
        checkNotNull(worldIdentifier, "The variable 'wordUUID' in WorldNotFoundException#WorldNotFoundException cannot be null.");

        throw new NullPointerException("No world identified by " + worldIdentifier + " has been found!");
    }

}
