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

public class FinishHologramCreationProcessor implements HologramCreationProcessor {

    @Override
    public String getID() {
        return "FINISH";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("finish", "create");
    }

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        if (currentBuilder.getLines().isEmpty()) {
            Message.builder().messageType(MessageTypes.ERROR).localizedContent("creation.no_lines_added").sendTo(creator).build();
            Message.builder().messageType(MessageTypes.WARNING).localizedContent("creation.auto_quit").sendTo(creator).build();
            Felograms.getInstance().getHologramCreationManager().stopCreation(uuid);

        } else {
            Hologram hologram = currentBuilder.setDisabled(false).setLocation(location).buildAndRegister();
            Felograms.getInstance().getHologramCreationManager().stopCreation(uuid);
            Message.builder().messageType(MessageTypes.SUCCESS).localizedContent("creation.finished", hologram.getID()).sendTo(creator).build();
        }

        return Optional.empty();
    }

}
