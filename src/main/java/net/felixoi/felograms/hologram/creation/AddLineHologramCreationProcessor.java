package net.felixoi.felograms.hologram.creation;

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

public class AddLineHologramCreationProcessor implements HologramCreationProcessor {

    @Override
    public String getID() {
        return "ADD_LINE";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("add", "addLine");
    }

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        Text line = TextSerializers.FORMATTING_CODE.deserialize(arguments);
        currentBuilder.line(line);

        Message.builder().messageType(MessageTypes.SUCCESS).localizedContent("creation.line_added").hoverContentText(line).sendTo(creator).build();

        return Optional.of(currentBuilder);
    }

}
