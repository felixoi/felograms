package net.felixoi.felograms.configuration;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public final class MainConfiguration {

    @Setting(comment = "Specifies the plugin language. Currently supported: de, en")
    private String locale = "en";

    @Setting(comment = "Specifies the vertical spacing between the lines if several lines exist.")
    private double spaceBetweenLines = 0.25;

    public String getLocale() {
        return this.locale;
    }

    public double getSpaceBetweenLines() {
        return this.spaceBetweenLines;
    }

}
