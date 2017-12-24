package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.api.command.Aliases;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramCreationProcessor;
import net.felixoi.felograms.api.message.Message;
import net.felixoi.felograms.api.message.MessageTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Aliases({"add", "addline"})
public class AddLineHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        Text line = TextSerializers.FORMATTING_CODE.deserialize(arguments);
        currentBuilder.line(line);

        Message.builder().messageType(MessageTypes.SUCCESS)
                .localizedLine("creation.line_added", TextSerializers.FORMATTING_CODE.serialize(line))
                .line(line)
                .sendTo(creator).build();

        return Optional.of(currentBuilder);
    }

}
