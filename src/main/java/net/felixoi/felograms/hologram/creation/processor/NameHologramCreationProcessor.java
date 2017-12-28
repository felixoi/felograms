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

@Aliases({"name"})
public final class NameHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<HologramCreationBuilder> process(HologramCreationBuilder builder, Player player, String arguments) {
        String[] args = arguments.split(" ");

        if (args.length == 1) {
            String name = args[0];

            if (Felograms.getInstance().getHologramStore().getAll().stream().anyMatch(hologram -> hologram.getName().equalsIgnoreCase(name))) {
                Message.ofLocalized(MessageTypes.ERROR, "hologram.name_exists", name).sendTo(player);
            } else {
                if (builder.getName().equalsIgnoreCase(name)) {
                    Message.ofLocalized(MessageTypes.WARNING, "creation.name.already", name).sendTo(player);
                } else {
                    builder.setName(name);
                    Message.ofLocalized(MessageTypes.SUCCESS, "creation.name.changed", name).sendTo(player);
                }
            }
        } else {
            MultiMessage.builder()
                    .localizedMessage(MessageTypes.ERROR, (arguments.length() < 1 ? "command.arguments.not_enough" : "command.arguments.too_many"))
                    .localizedMessage(MessageTypes.INFO, "creation.name.usage")
                    .sendTo(player)
                    .buildAndSend();
        }

        return Optional.of(builder);
    }

}
