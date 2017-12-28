package net.felixoi.felograms.hologram.creation.processor;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationBuilder;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationProcessor;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.internal.message.MultiMessage;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

@Aliases({"finish", "create"})
public final class FinishHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<HologramCreationBuilder> process(HologramCreationBuilder builder, Player player, String arguments) {
        if (Felograms.getInstance().getHologramStore().getAll().stream().anyMatch(hologram -> hologram.getName().equalsIgnoreCase(builder.getName()))) {
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
                Hologram hologram = builder.setLocation(player.getLocation()).build();
                hologram.spawnAssociatedEntities();

                Felograms.getInstance().getHologramCreationManager().stopCreation(player.getUniqueId());
                Message.ofLocalized(MessageTypes.SUCCESS, "hologram.created", builder.getName()).sendTo(player);
            }
        }

        return Optional.empty();
    }

}
