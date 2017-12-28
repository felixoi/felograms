package net.felixoi.felograms.hologram.creation.processor;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationBuilder;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.internal.message.MultiMessage;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

@Aliases({"id", "identifier"})
public final class IDHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<HologramCreationBuilder> process(HologramCreationBuilder builder, Player player, String arguments) {
        String[] args = arguments.split(" ");

        if (args.length == 1) {
            String hologramID = args[0];

            if (Felograms.getInstance().getHologramStore().getAll().stream().anyMatch(hologram -> hologram.getName().equalsIgnoreCase(hologramID))) {
                Message.ofLocalized(MessageTypes.ERROR, "hologram.id_exists", hologramID).sendTo(player);
            } else {
                if (builder.getName().equalsIgnoreCase(hologramID)) {
                    Message.ofLocalized(MessageTypes.WARNING, "creation.id.already", hologramID).sendTo(player);
                } else {
                    builder.setName(hologramID);
                    Message.ofLocalized(MessageTypes.SUCCESS, "creation.id.changed", hologramID).sendTo(player);
                }
            }
        } else {
            MultiMessage.builder()
                    .localizedMessage(MessageTypes.ERROR, (arguments.length() < 1 ? "command.arguments.not_enough" : "command.arguments.too_many"))
                    .localizedMessage(MessageTypes.INFO, "creation.id.usage")
                    .sendTo(player)
                    .buildAndSend();
        }

        return Optional.of(builder);
    }

}
