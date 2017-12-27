package net.felixoi.felograms.hologram.creation.processor;

import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationBuilder;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.internal.message.MultiMessage;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Optional;

@Aliases({"add", "addline"})
public class AddLineHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<HologramCreationBuilder> process(HologramCreationBuilder builder, Player player, String arguments) {
        if (arguments != null && !arguments.trim().equals("")) {
            Text line = TextSerializers.FORMATTING_CODE.deserialize(arguments);
            builder.line(line);

            MultiMessage.builder()
                    .localizedMessage(MessageTypes.SUCCESS, "creation.add.success")
                    .message(MessageTypes.CONSEQUENCE, arguments)
                    .sendTo(player)
                    .buildAndSend();

            return Optional.of(builder);
        } else {
            MultiMessage.builder()
                    .localizedMessage(MessageTypes.ERROR, "command.arguments.not_enough")
                    .localizedMessage(MessageTypes.INFO, "creation.add.usage")
                    .sendTo(player)
                    .buildAndSend();

            return Optional.empty();
        }
    }

}
