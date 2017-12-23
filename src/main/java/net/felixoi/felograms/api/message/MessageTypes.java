package net.felixoi.felograms.api.message;

import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

public final class MessageTypes {

    public static MessageType INFO = new MessageType() {

        @Override
        public String getID() {
            return "INFO";
        }

        @Override
        public String getName() {
            return "Info";
        }

        @Override
        public TextColor getColor() {
            return TextColors.AQUA;
        }

    };
    public static MessageType SUCCESS = new MessageType() {

        @Override
        public String getID() {
            return "SUCCESS";
        }

        @Override
        public String getName() {
            return "Success";
        }

        @Override
        public TextColor getColor() {
            return TextColors.GREEN;
        }

    };
    public static MessageType WARNING = new MessageType() {

        @Override
        public String getID() {
            return "WARNING";
        }

        @Override
        public String getName() {
            return "Warning";
        }

        @Override
        public TextColor getColor() {
            return TextColors.YELLOW;
        }

    };
    public static MessageType ERROR = new MessageType() {

        @Override
        public String getID() {
            return "ERROR";
        }

        @Override
        public String getName() {
            return "Error";
        }

        @Override
        public TextColor getColor() {
            return TextColors.RED;
        }

    };
    public static MessageType DEBUG = new MessageType() {

        @Override
        public String getID() {
            return "DEBUG";
        }

        @Override
        public String getName() {
            return "Debug";
        }

        @Override
        public TextColor getColor() {
            return TextColors.LIGHT_PURPLE;
        }

    };

    private MessageTypes() {
    }

}
