package net.felixoi.felograms.listener;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.HologramCreationProcessor;
import net.felixoi.felograms.hologram.creation.*;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.text.channel.MessageChannel;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ChatListener {

    @Listener
    public void onChat(MessageChannelEvent.Chat event, @First Player player) {
        UUID uuid = player.getUniqueId();
        String rawMessage = event.getRawMessage().toPlain();

        if (Felograms.getInstance().getHologramCreationManager().getCreators().contains(uuid)) {
            event.setCancelled(true);

            Felograms.getInstance().getHologramCreationManager().process(new AddLineHologramCreationProcessor(), uuid, player, rawMessage, player.getLocation());
        }
    }

}
