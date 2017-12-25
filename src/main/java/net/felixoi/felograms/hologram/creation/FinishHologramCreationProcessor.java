package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.internal.hologram.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.internal.message.MultiMessage;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

@Aliases({"finish", "create"})
public class FinishHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder builder, Player player, String arguments) {
        if(Felograms.getInstance().getHologramManager().getHologramIDs().contains(builder.getID().get())) {
            MultiMessage.builder()
                    .localizedMessage(MessageTypes.ERROR, "creation.id.exists")
                    .localizedMessage(MessageTypes.INFO, "creation.id.usage")
                    .sendTo(player)
                    .buildAndSend();

            return Optional.of(builder);
        } else {
            if (builder.getLines().isEmpty()) {
                MultiMessage.builder()
                        .localizedMessage(MessageTypes.ERROR, "creation.create.empty")
                        .localizedMessage(MessageTypes.WARNING, "creation.quit.auto")
                        .sendTo(player)
                        .buildAndSend();
                Felograms.getInstance().getHologramCreationManager().stopCreation(player.getUniqueId());

            } else {
                Hologram hologram = builder.setDisabled(false).setLocation(player.getLocation()).buildAndRegister();
                Felograms.getInstance().getHologramCreationManager().stopCreation(player.getUniqueId());
                Message.ofLocalized(MessageTypes.SUCCESS, "hologram.created", hologram.getID()).sendTo(player);
            }
        }

        return Optional.empty();
    }

}
