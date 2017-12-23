package net.felixoi.felograms.api.message;

import net.felixoi.felograms.util.LocaleUtil;
import net.felixoi.felograms.util.TextUtil;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.util.ResettableBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public class Message implements TextRepresentable {

    private static String PREFIX = "Felograms";
    private static String EMPTY_PREFIX = "                ";
    private static Text DIVIDER = Text.of(TextColors.GRAY, "»");

    private MessageType messageType;
    private List<Text> lines;

    private Message(MessageType messageType, List<Text> lines) {
        this.messageType = checkNotNull(messageType, "The variable 'messageType' in Message#Message(MessageType, String) cannot be null.");
        this.lines = checkNotNull(lines, "The variable 'lines' in Message#Message([messageType, lines]) cannot be null.");
    }

    public static Builder builder() {
        return new Builder();
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    public List<Text> getLines() {
        return this.lines;
    }

    @Override
    public Text toText() {
        AtomicInteger index = new AtomicInteger();

        return TextUtil.listToText(this.lines.stream().map(text -> {
            int currentIndex = index.getAndIncrement();

            return Text.of(this.messageType.getColor(), currentIndex < 1 ? (Text.of(PREFIX + " ", DIVIDER, " ")) : EMPTY_PREFIX, TextColors.WHITE, text);
        }).collect(Collectors.toList()));
    }

    public static class Builder implements ResettableBuilder<Message, Builder> {

        private MessageType messageType;
        private List<Text> lines;
        private Collection<MessageReceiver> receivers;

        private Builder() {
            this.lines = new ArrayList<>();
        }

        public Builder messageType(MessageType messageType) {
            this.messageType = checkNotNull(messageType, "The variable 'messageType' in Message.Builder#messageType(MessageType) cannot be null.");

            return this;
        }

        public Builder setLines(List<Text> lines) {
            this.lines = checkNotNull(lines, "The variable 'lines' in Builder#setLines(lines) cannot be null.");

            return this;
        }

        public Builder line(String content, Object... arguments) {
            checkNotNull(content, "The variable 'key' in Builder#line(key, arguments) cannot be null.");

            this.lines.add(TextSerializers.FORMATTING_CODE.deserialize(String.format(content, arguments)));

            return this;
        }

        public Builder localizedLine(String key, Object... arguments) {
            checkNotNull(key, "The variable 'key' in Builder#localizedLine(key, arguments) cannot be null.");

            this.lines.add(TextSerializers.FORMATTING_CODE.deserialize(LocaleUtil.getMessage(key, arguments)));

            return this;
        }

        public Builder line(Text content) {
            checkNotNull(content, "The variable 'line' in Builder#content(content) cannot be null.");

            this.lines.add(content);

            return this;
        }

        public Builder sendTo(MessageReceiver... receivers) {
            this.receivers = Arrays.asList(receivers);

            return this;
        }

        public Message build() {
            checkNotNull(this.messageType, "The variable 'lines' in Message.Builder cannot be null.");
            checkState(!this.lines.isEmpty(), "The list 'lines' in Message.Builder cannot be null");

            Message message = new Message(this.messageType, this.lines);

            if (this.receivers != null) {
                this.receivers.forEach((receiver -> receiver.sendMessage(message.toText())));
            }

            return message;
        }

        @Override
        public Builder from(Message message) {
            this.messageType = message.getMessageType();
            this.lines = message.getLines();

            return this;
        }

        @Override
        public Builder reset() {
            this.messageType = null;
            this.lines = null;
            this.receivers = null;

            return this;
        }
    }

}
