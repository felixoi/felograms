package net.felixoi.felograms.hologram.creation.processor;

import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationBuilder;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.internal.message.MultiMessage;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Optional;

@Aliases({"delete", "del", "remove", "rm"})
public final class DeleteHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<HologramCreationBuilder> process(HologramCreationBuilder builder, Player player, String arguments) {
        String[] args = arguments.split(" ");

        if (args.length == 1) {
            try {
                int lineNumber = Integer.valueOf(args[0]);

                if(lineNumber <= builder.getLines().size()) {
                    List<Text> lines = builder.getLines();
                    lines.remove(lineNumber - 1);

                    builder.setLines(lines);
                    Message.ofLocalized(MessageTypes.SUCCESS, "creation.delete.success").sendTo(player);

                    return Optional.of(builder);
                } else {
                    MultiMessage.builder()
                            .localizedMessage(MessageTypes.ERROR, "creation.delete.bounds", lineNumber, builder.getLines().size())
                            .localizedMessage(MessageTypes.INFO, "creation.status.usage")
                            .sendTo(player)
                            .buildAndSend();
                }
            } catch (NumberFormatException e) {
                MultiMessage.builder()
                        .localizedMessage(MessageTypes.ERROR, "command.arguments.expected_integer", args[0])
                        .localizedMessage(MessageTypes.INFO, "creation.delete.usage")
                        .sendTo(player)
                        .buildAndSend();
            }
        } else {
            Message.ofLocalized(MessageTypes.ERROR, "creation.delete.usage").sendTo(player);
        }

        return Optional.empty();
    }

}
