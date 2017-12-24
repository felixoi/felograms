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

@Aliases({"delete", "del", "remove", "rm"})
public class DeleteHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        String[] args = arguments.split(" ");

        if(args.length == 1) {
            try {
                int lineNumber = Integer.valueOf(args[0]);

                List<Text> lines = currentBuilder.getLines();
                lines.remove(lineNumber - 1);

                currentBuilder.setLines(lines);
                Message.builder().messageType(MessageTypes.SUCCESS).localizedLine("creation.delete.success").sendTo(creator).build();
            } catch (NumberFormatException e) {
                Message.builder().messageType(MessageTypes.ERROR).localizedLine("creation.delete.not_number").sendTo(creator).build();
                Message.builder().messageType(MessageTypes.INFO).localizedLine("creation.delete.usage").sendTo(creator).build();
            }
        } else {
            Message.builder().messageType(MessageTypes.ERROR).localizedLine("creation.delete.usage").sendTo(creator).build();
        }

        return Optional.of(currentBuilder);
    }

}
