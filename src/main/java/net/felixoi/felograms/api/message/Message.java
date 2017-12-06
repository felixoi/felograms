package net.felixoi.felograms.api.message;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;

import java.util.Arrays;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

public class Message implements TextRepresentable {

    private static String PREFIX = "Felograms";
    private static Text DIVIDER = Text.of(TextColors.GRAY, "»");

    private MessageType messageType;
    private String content;

    private Message(MessageType messageType, String content) {
        this.messageType = checkNotNull(messageType, "The variable 'messageType' in Message#Message(MessageType, String) cannot be null.");
        this.content = checkNotNull(content, "The variable 'content' in Message#Message([messageType, content]) cannot be null.");
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public String getContent() {
        return this.content;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public Text toText() {
        return Text.of(this.messageType.getColor(), PREFIX, " ", DIVIDER, " ", TextColors.WHITE, this.content);
    }

    public static class Builder {

        private MessageType messageType;
        private String content;
        private Collection<MessageReceiver> receivers;

        public Builder messageType(MessageType messageType) {
            this.messageType = checkNotNull(messageType, "The variable 'messageType' in Message.Builder#messageType(MessageType) cannot be null.");

            return this;
        }

        public Builder content(String content) {
            this.content = checkNotNull(content, "The variable 'content' in Message.Builder#content(String) cannot be null.");

            return this;
        }

        public Builder sendTo(MessageReceiver... receivers) {
            this.receivers = Arrays.asList(receivers);

            return this;
        }

        public Message build() {
            checkNotNull(this.content, "The variable 'messageType' in Message.Builder cannot be null.");
            checkNotNull(this.messageType, "The variable 'content' in Message.Builder cannot be null.");

            Message message = new Message(this.messageType, this.content);

            if(this.receivers != null) {
                this.receivers.forEach((receiver -> receiver.sendMessage(message.toText())));
            }

            return message;
        }

    }

}
