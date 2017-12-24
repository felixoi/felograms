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

@Aliases({"id", "identifier"})
public class IDHologramCreationProcessor extends HologramCreationProcessor {

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        String[] args = arguments.split(" ");

        if (args.length == 1) {
            String hologramID = args[0];

            if (Felograms.getInstance().getHologramManager().getHologramIDs().contains(hologramID)) {
                Message.builder().messageType(MessageTypes.ERROR).localizedLine("hologram.id_exists", hologramID).sendTo(creator).build();
            } else {
                if(currentBuilder.getID().get().equalsIgnoreCase(hologramID)) {
                    Message.builder().messageType(MessageTypes.WARNING).localizedLine("creation.id.already", hologramID).sendTo(creator).build();
                } else {
                    currentBuilder.setID(hologramID);
                    Message.builder().messageType(MessageTypes.SUCCESS).localizedLine("creation.id.changed", hologramID).sendTo(creator).build();
                }
            }
        } else {
            Message.builder().messageType(MessageTypes.ERROR).localizedLine("creation.id.usage").sendTo(creator).build();
        }

        return Optional.of(currentBuilder);
    }

}
