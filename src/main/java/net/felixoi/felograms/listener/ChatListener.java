package net.felixoi.felograms.listener;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.api.hologram.HologramCreationManager;
import net.felixoi.felograms.api.message.Message;
import net.felixoi.felograms.api.message.MessageType;
import net.felixoi.felograms.api.message.MessageTypes;
import net.felixoi.felograms.hologram.SimpleHologram;
import net.felixoi.felograms.util.LocationUtil;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.channel.MessageChannel;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class ChatListener {

    private HologramCreationManager creationManager;

    ChatListener() {
        this.creationManager = Felograms.getInstance().getHologramCreationManager();
    }

    @Listener
    public void onChat(MessageChannelEvent.Chat event, @First Player player) {
        UUID uuid = player.getUniqueId();

        if (this.creationManager.getCreationID(uuid).isPresent()) {
            String id = this.creationManager.getCreationID(uuid).get();

            if (this.creationManager.getLines(id).isPresent()) {
                List<Text> currentLines = this.creationManager.getLines(id).get();

                event.setChannel(MessageChannel.TO_NONE);

                switch (event.getRawMessage().toPlain()) {
                    case "<cancel":
                    case "<exit": {
                        this.creationManager.stopCreationProcess(uuid);

                        Message.builder().messageType(MessageTypes.WARNING).content("You left the hologram creation process!").sendTo(player).build();
                        break;
                    }
                    case "<status": {
                        Message.builder().messageType(MessageTypes.NEUTRAL).content("The hologram would contain the following lines:").sendTo(player).build();
                        AtomicInteger index = new AtomicInteger();
                        currentLines.iterator().forEachRemaining(text -> player.sendMessage(Text.of(index.incrementAndGet() + ". ", text)));
                        break;
                    }
                    case "<finish":
                    case "<create": {
                        if (currentLines.isEmpty()) {
                            player.sendMessage(Text.of("Hologram creation failed! No lines have been added."));
                            player.sendMessage(Text.of("You automatically quit the creation process!"));

                            this.creationManager.stopCreationProcess(uuid);
                        } else {
                            Hologram hologram = new SimpleHologram(currentLines, player.getLocation().setPosition(LocationUtil.getBlockMiddle(player.getLocation().getBlockPosition())));
                            Felograms.getInstance().getHologramManager().spawnHologram(hologram);

                            this.creationManager.stopCreationProcess(uuid);

                            player.sendMessage(Text.of("The hologram has been created!"));
                        }

                        break;
                    }
                    default: {
                        Text input = TextSerializers.FORMATTING_CODE.deserialize(event.getRawMessage().toPlain());
                        currentLines.add(input);
                        this.creationManager.setLines(id, currentLines);

                        player.sendMessage(Text.of("This line has been added: ", input));
                        break;
                    }
                }
            } else {
                player.sendMessage(Text.of("An error occurred!"));
                player.sendMessage(Text.of("You automatically quit the creation process!"));
                this.creationManager.stopCreationProcess(uuid);
            }
        }
    }

}
