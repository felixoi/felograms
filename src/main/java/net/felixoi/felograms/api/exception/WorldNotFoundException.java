package net.felixoi.felograms.api.exception;

import static com.google.common.base.Preconditions.checkNotNull;

public class WorldNotFoundException extends NullPointerException {

    public WorldNotFoundException(String worldIdentifier) {
        checkNotNull(worldIdentifier, "The variable 'worldIdentifier' in WorldNotFoundException#WorldNotFoundException(worldIdentifier) cannot be null.");

        throw new NullPointerException("No world with the uuid '" + worldIdentifier + "' has been found!");
    }

}
