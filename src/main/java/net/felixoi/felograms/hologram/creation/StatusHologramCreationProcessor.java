package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramCreationProcessor;
import net.felixoi.felograms.api.message.Message;
import net.felixoi.felograms.api.message.MessageTypes;
import net.felixoi.felograms.util.TextUtil;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;

public class StatusHologramCreationProcessor implements HologramCreationProcessor {

    @Override
    public String getID() {
        return "STATUS";
    }

    @Override
    public List<String> getAliases() {
        return Arrays.asList("status", "current");
    }

    @Override
    public Optional<Hologram.Builder> process(Hologram.Builder currentBuilder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location) {
        if (currentBuilder.getLines().isEmpty()) {
            Message.builder().messageType(MessageTypes.INFO).localizedContent("creation.status.empty").sendTo(creator).build();
        } else {
            Message.builder().messageType(MessageTypes.INFO).localizedContent("creation.status.current").hoverContentText(TextUtil.listToText(currentBuilder.getLines())).sendTo(creator).build().toText();
        }

        return Optional.of(currentBuilder);
    }

}
