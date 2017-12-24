package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.api.command.Aliases;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramCreationProcessor;
import net.felixoi.felograms.api.message.Message;
import net.felixoi.felograms.api.message.MessageTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Aliases({"status", "current"})
public class StatusHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        if (currentBuilder.getLines().isEmpty()) {
            Message.builder().messageType(MessageTypes.INFO).localizedLine("creation.status.empty").sendTo(creator).build();
        } else {
            Message.Builder builder = Message.builder().messageType(MessageTypes.INFO).localizedLine("creation.status.show");

            AtomicInteger index = new AtomicInteger();

            currentBuilder.getLines().forEach(line -> {
                int currentIndex = index.incrementAndGet();

                builder.line(Text.of(currentIndex + ". ", line));
            });

            builder.sendTo(creator).build();
        }

        return Optional.of(currentBuilder);
    }

}
