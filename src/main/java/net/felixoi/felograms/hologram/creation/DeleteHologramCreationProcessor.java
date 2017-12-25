package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.internal.hologram.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.internal.message.MultiMessage;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Optional;

@Aliases({"delete", "del", "remove", "rm"})
public class DeleteHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder builder, Player player, String arguments) {
        String[] args = arguments.split(" ");

        if(args.length == 1) {
            try {
                int lineNumber = Integer.valueOf(args[0]);

                List<Text> lines = builder.getLines();
                lines.remove(lineNumber - 1);

                builder.setLines(lines);
                Message.ofLocalized(MessageTypes.SUCCESS, "creation.delete.success");

                return Optional.of(builder);
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
