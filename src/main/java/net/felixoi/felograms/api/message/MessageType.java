package net.felixoi.felograms.api.message;

import org.spongepowered.api.text.format.TextColor;

public interface MessageType {

    String getID();

    String getName();

    TextColor getColor();

}

