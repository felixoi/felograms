package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.internal.hologram.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.internal.message.MultiMessage;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Aliases({"status", "current"})
public class StatusHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, Player player, String arguments) {
        if (currentBuilder.getLines().isEmpty()) {
            Message.ofLocalized(MessageTypes.INFO, "creation.status.empty").sendTo(player);
        } else {
            MultiMessage.Builder builder = MultiMessage.builder().localizedMessage(MessageTypes.INFO, "creation.status.show");

            AtomicInteger index = new AtomicInteger();

            currentBuilder.getLines().forEach(line -> {
                int currentIndex = index.incrementAndGet();

                builder.message(MessageTypes.CONSEQUENCE, Text.of(currentIndex + ". ", line));
            });

            builder.sendTo(player).buildAndSend();
        }

        return Optional.empty();
    }

}
