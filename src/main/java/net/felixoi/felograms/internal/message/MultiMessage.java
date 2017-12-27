package net.felixoi.felograms.internal.message;

import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public final class MultiMessage {

    private List<Message> messages;

    private MultiMessage(List<Message> messages) {
        this.messages = checkNotNull(messages, "The variable 'messages' in MultiMessage#MultiMessage cannot be null.");
    }

    public static Builder builder() {
        return new Builder();
    }

    public List<Message> getMessages() {
        return this.messages;
    }

    public static class Builder {

        private List<Message> messages;
        private MessageReceiver[] receivers;

        private Builder() {
            this.messages = new ArrayList<>();
        }

        public void setMessages(List<Message> messages) {
            this.messages = checkNotNull(messages, "The variable 'messages' in Builder#setMessages cannot be null.");
        }

        public Builder message(Message message) {
            checkNotNull(message, "The variable 'message' in Builder#message cannot be null.");

            this.messages.add(message);

            return this;
        }

        public Builder message(MessageType messageType, Text text) {
            checkNotNull(messageType, "The variable 'messageType' in Builder#message cannot be null.");
            checkNotNull(text, "The variable 'text' in Builder#message cannot be null.");

            this.messages.add(Message.of(messageType, text));

            return this;
        }

        public Builder message(MessageType messageType, String content, Object... args) {
            checkNotNull(messageType, "The variable 'messageType' in Builder#localizedMessage cannot be null.");
            checkNotNull(content, "The variable 'key' in Builder#localizedMessage cannot be null.");

            this.messages.add(Message.of(messageType, content, args));

            return this;
        }

        public Builder localizedMessage(MessageType messageType, String key, Object... args) {
            checkNotNull(messageType, "The variable 'messageType' in Builder#localizedMessage cannot be null.");
            checkNotNull(key, "The variable 'key' in Builder#localizedMessage cannot be null.");

            this.messages.add(Message.ofLocalized(messageType, key, args));

            return this;
        }

        public Builder sendTo(MessageReceiver... receivers) {
            this.receivers = receivers;

            return this;
        }

        public MultiMessage build() {
            return new MultiMessage(this.messages);
        }

        public MultiMessage buildAndSend() {
            for (Message message : this.messages) {
                for (MessageReceiver receiver : this.receivers) {
                    receiver.sendMessage(message.toText());
                }
            }

            return this.build();
        }

    }

}
