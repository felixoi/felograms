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

public class InsertHologramCreationProcessor implements HologramCreationProcessor {

    @Override
    public String getID() {
        return "INSERT";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("insert", "ins");
    }

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        String[] args = arguments.split(" ");

        if(args.length >= 2) {
            try {
                int lineNumber = Integer.valueOf(args[0]);

                List<Text> lines = currentBuilder.getLines();
                Text line = TextSerializers.FORMATTING_CODE.deserialize(arguments.substring(arguments.indexOf(" ") + 1));
                lines.add(lineNumber - 1, line);

                currentBuilder.setLines(lines);
                Message.builder().messageType(MessageTypes.SUCCESS)
                        .localizedLine("creation.insert.success")
                        .line(line)
                        .sendTo(creator).build();
            } catch (NumberFormatException e) {
                Message.builder().messageType(MessageTypes.ERROR).localizedLine("creation.insert.not_number").sendTo(creator).build();
                Message.builder().messageType(MessageTypes.INFO).localizedLine("creation.insert.usage").sendTo(creator).build();
            }
        } else {
            Message.builder().messageType(MessageTypes.ERROR).localizedLine("creation.insert.usage").sendTo(creator).build();
        }

        return Optional.of(currentBuilder);
    }

}
