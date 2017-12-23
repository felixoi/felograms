package net.felixoi.felograms.api.exception;

import static com.google.common.base.Preconditions.checkNotNull;

public class HologramNotFoundException extends NullPointerException {

    public HologramNotFoundException(String hologramID) {
        checkNotNull(hologramID, "The variable 'hologramID' in HologramNotFoundException#HologramNotFoundException(hologramID) cannot be null.");

        throw new NullPointerException("No hologram with the id " + hologramID + "was found!");
    }

}
