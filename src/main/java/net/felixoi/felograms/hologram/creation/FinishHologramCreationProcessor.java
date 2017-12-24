package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.command.Aliases;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramCreationProcessor;
import net.felixoi.felograms.api.message.Message;
import net.felixoi.felograms.api.message.MessageTypes;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Aliases({"finish", "create"})
public class FinishHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        if(Felograms.getInstance().getHologramManager().getHologramIDs().contains(currentBuilder.getID().get())) {
            Message.builder().messageType(MessageTypes.ERROR).localizedLine("creation.id.exists").sendTo(creator).build();
            Message.builder().messageType(MessageTypes.INFO).localizedLine("creation.id.usage").sendTo(creator).build();

            return Optional.of(currentBuilder);
        } else {
            if (currentBuilder.getLines().isEmpty()) {
                Message.builder().messageType(MessageTypes.ERROR).localizedLine("creation.create.empty").sendTo(creator).build();
                Message.builder().messageType(MessageTypes.WARNING).localizedLine("creation.quit.auto").sendTo(creator).build();
                Felograms.getInstance().getHologramCreationManager().stopCreation(uuid);

            } else {
                Hologram hologram = currentBuilder.setDisabled(false).setLocation(location).buildAndRegister();
                Felograms.getInstance().getHologramCreationManager().stopCreation(uuid);
                Message.builder().messageType(MessageTypes.SUCCESS).localizedLine("hologram.created", hologram.getID()).sendTo(creator).build();
            }
        }

        return Optional.empty();
    }

}
