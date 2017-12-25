package net.felixoi.felograms.internal.message;

import net.felixoi.felograms.util.LocaleUtil;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;

import static com.google.common.base.Preconditions.checkNotNull;

public final class Message implements TextRepresentable {

    private static String PREFIX = "Felograms";
    private static String DIVIDER = "»";
    private static String CONSEQUENCE_PREFIX = "\u25B6";

    private MessageType messageType;
    private Text text;

    private Message(MessageType messageType, Text text) {
        this.messageType = checkNotNull(messageType, "The variable 'messageType' in Message#Message cannot be null.");
        checkNotNull(text, "The variable 'text' in Message#Message cannot be null.");

        if(messageType.equals(MessageTypes.CONSEQUENCE)) {
            this.text = Text.of(TextColors.GRAY, CONSEQUENCE_PREFIX, " ", TextColors.RESET, text);
        } else if(messageType.equals(MessageTypes.NONE)) {
            this.text = text;
        } else {
            this.text = Text.of(messageType.getColor(), PREFIX, " ", TextColors.GRAY, DIVIDER, " ", TextColors.RESET, text);
        }
    }

    private static Message of(MessageType messageType, String content) {
        checkNotNull(content, "The variable 'content' in Message#of cannot be null.");

        return new Message(messageType, TextSerializers.FORMATTING_CODE.deserialize(content));
    }

    public static Message of(MessageType messageType, Text text) {
        return new Message(messageType, text);
    }

    public static Message of(MessageType messageType, String content, Object... args) {
        return of(messageType, String.format(content, args));
    }

    public static Message ofLocalized(MessageType messageType, String key, Object... args) {
        checkNotNull(key, "The variable 'key' in Message#ofLocalized cannot be null.");

        return of(messageType, LocaleUtil.getMessage(key), args);
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    @Override
    public Text toText() {
        return this.text;
    }

    public void sendTo(MessageReceiver... receivers) {
        for(MessageReceiver receiver : receivers) {
            receiver.sendMessage(this.text);
        }
    }

}
