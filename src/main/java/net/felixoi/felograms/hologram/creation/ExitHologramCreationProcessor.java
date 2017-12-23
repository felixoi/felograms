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

public class ExitHologramCreationProcessor implements HologramCreationProcessor {

    @Override
    public String getID() {
        return "EXIT";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("exit", "cancel");
    }

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        Felograms.getInstance().getHologramCreationManager().stopCreation(uuid);
        Message.builder().messageType(MessageTypes.WARNING).localizedLine("creation.exit").sendTo(creator).build();

        return Optional.empty();
    }

}
