package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.Felograms;
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

public class IDHologramCreationProcessor implements HologramCreationProcessor {

    @Override
    public String getID() {
        return "ID";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("id", "identifier");
    }

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        String[] args = arguments.split(" ");

        if (args.length == 1) {
            String hologramID = args[0];

            if (Felograms.getInstance().getHologramManager().getHologramIDs().contains(hologramID)) {
                Message.builder().messageType(MessageTypes.ERROR).localizedContent("hologram.id_exists", hologramID).sendTo(creator).build();
            } else {
                if(currentBuilder.getID().get().equalsIgnoreCase(hologramID)) {
                    Message.builder().messageType(MessageTypes.WARNING).localizedContent("creation.id.already", hologramID).sendTo(creator).build();
                } else {
                    currentBuilder.setID(hologramID);
                    Message.builder().messageType(MessageTypes.SUCCESS).localizedContent("creation.id.changed", hologramID).sendTo(creator).build();
                }
            }
        } else {
            Message.builder().messageType(MessageTypes.ERROR).localizedContent("creation.id.usage").sendTo(creator).build();
        }

        return Optional.of(currentBuilder);
    }

}
