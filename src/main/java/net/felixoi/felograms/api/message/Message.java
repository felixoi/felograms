package net.felixoi.felograms.api.message;

import net.felixoi.felograms.util.LocaleUtil;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.util.ResettableBuilder;

import java.util.Arrays;
import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

public class Message implements TextRepresentable {

    private static String PREFIX = "Felograms";
    private static Text DIVIDER = Text.of(TextColors.GRAY, "»");

    private MessageType messageType;
    private Text content;

    private Message(MessageType messageType, String content) {
        this.messageType = checkNotNull(messageType, "The variable 'messageType' in Message#Message(MessageType, String) cannot be null.");
        this.content = checkNotNull(Text.of(content), "The variable 'content' in Message#Message([messageType, content]) cannot be null.");
    }

    private Message(MessageType messageType, Text content) {
        this.messageType = checkNotNull(messageType, "The variable 'messageType' in Message#Message(MessageType, String) cannot be null.");
        this.content = checkNotNull(content, "The variable 'content' in Message#Message([messageType, content]) cannot be null.");
    }

    public static Builder builder() {
        return new Builder();
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public Text getContent() {
        return this.content;
    }

    @Override
    public Text toText() {
        return Text.of(this.messageType.getColor(), PREFIX, " ", DIVIDER, " ", TextColors.WHITE, this.content);
    }

    public static class Builder implements ResettableBuilder<Message, Builder> {

        private MessageType messageType;
        private Text content;
        private Collection<MessageReceiver> receivers;

        public Builder messageType(MessageType messageType) {
            this.messageType = checkNotNull(messageType, "The variable 'messageType' in Message.Builder#messageType(MessageType) cannot be null.");

            return this;
        }

        public Builder content(String content, Object... arguments) {
            this.content = checkNotNull(TextSerializers.FORMATTING_CODE.deserialize(String.format(content, arguments)), "The variable 'content' in Message.Builder#content(String) cannot be null.");

            return this;
        }

        public Builder localizedContent(String key, Object... arguments) {
            this.content = checkNotNull(TextSerializers.FORMATTING_CODE.deserialize(LocaleUtil.getMessage(key, arguments)), "The variable 'key' in Builder#localizedContent(key, arguments) cannot be null.");

            return this;
        }

        public Builder content(Text content) {
            this.content = checkNotNull(content, "The variable 'content' in Message.Builder#content(Text) cannot be null.");

            return this;
        }

        public Builder hoverContentText(Text hoverText) {
            checkNotNull(hoverText, "The variable 'hoverText' in Builder#hoverContentText(hoverText) cannot be null.");

            this.content = Text.builder().append(this.content).onHover(TextActions.showText(hoverText)).build();

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

            if (this.receivers != null) {
                this.receivers.forEach((receiver -> receiver.sendMessage(message.toText())));
            }

            return message;
        }

        @Override
        public Builder from(Message message) {
            this.messageType = message.getMessageType();
            this.content = message.getContent();

            return this;
        }

        @Override
        public Builder reset() {
            this.messageType = null;
            this.content = null;
            this.receivers = null;

            return this;
        }
    }

}
