package net.felixoi.felograms.api.hologram;

import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HologramCreationProcessor {

    String getID();

    List<String> getAliases();

    Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location);

}
