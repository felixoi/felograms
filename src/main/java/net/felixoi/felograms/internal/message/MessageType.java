package net.felixoi.felograms.internal.message;

import org.spongepowered.api.text.format.TextColor;

public interface MessageType {

    String getID();

    String getName();

    TextColor getColor();

}

