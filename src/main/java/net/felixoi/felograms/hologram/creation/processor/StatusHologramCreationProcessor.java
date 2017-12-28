package net.felixoi.felograms.hologram.creation.processor;

import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationBuilder;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.internal.message.MultiMessage;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Aliases({"status", "current"})
public final class StatusHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<HologramCreationBuilder> process(HologramCreationBuilder builder, Player player, String arguments) {
        if (builder.getLines().isEmpty()) {
            Message.ofLocalized(MessageTypes.INFO, "creation.status.empty").sendTo(player);
        } else {
            MultiMessage.Builder messageBuilder = MultiMessage.builder().localizedMessage(MessageTypes.INFO, "creation.status.show");

            AtomicInteger index = new AtomicInteger();

            builder.getLines().forEach(line -> {
                int currentIndex = index.incrementAndGet();

                messageBuilder.message(MessageTypes.CONSEQUENCE, Text.of(currentIndex + ". ", line));
            });

            messageBuilder.sendTo(player).buildAndSend();
        }

        return Optional.empty();
    }

}
