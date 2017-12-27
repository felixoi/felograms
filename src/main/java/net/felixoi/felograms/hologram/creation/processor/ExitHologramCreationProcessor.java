package net.felixoi.felograms.hologram.creation.processor;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationBuilder;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

@Aliases({"exit", "cancel", "quit"})
public class ExitHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<HologramCreationBuilder> process(HologramCreationBuilder builder, Player player, String arguments) {
        Felograms.getInstance().getHologramCreationManager().stopCreation(player.getUniqueId());
        Message.ofLocalized(MessageTypes.WARNING, "creation.quit.manual").sendTo(player);

        return Optional.empty();
    }

}
