package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.internal.hologram.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.internal.message.MultiMessage;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.List;
import java.util.Optional;

@Aliases({"insert", "ins"})
public class InsertHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, Player player, String arguments) {
        String[] args = arguments.split(" ");

        if(args.length >= 2) {
            try {
                int lineNumber = Integer.valueOf(args[0]);

                List<Text> lines = currentBuilder.getLines();
                String rawLine = arguments.substring(arguments.indexOf(" ") + 1);
                Text line = TextSerializers.FORMATTING_CODE.deserialize(rawLine);
                lines.add(lineNumber - 1, line);

                currentBuilder.setLines(lines);

                MultiMessage.builder()
                        .localizedMessage(MessageTypes.SUCCESS, "creation.insert.success")
                        .message(MessageTypes.CONSEQUENCE, rawLine)
                        .sendTo(player)
                        .buildAndSend();

                return Optional.of(currentBuilder);
            } catch (NumberFormatException e) {
                MultiMessage.builder()
                        .localizedMessage(MessageTypes.ERROR, "command.arguments.expected_integer", args[0])
                        .localizedMessage(MessageTypes.INFO, "creation.insert.usage")
                        .sendTo(player)
                        .buildAndSend();
            }
        } else {
            MultiMessage.builder()
                    .localizedMessage(MessageTypes.ERROR, "command.arguments.not_enough")
                    .localizedMessage(MessageTypes.INFO, "creation.insert.usage")
                    .sendTo(player)
                    .buildAndSend();
        }

        return Optional.empty();
    }

}
