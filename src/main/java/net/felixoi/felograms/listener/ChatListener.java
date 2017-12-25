package net.felixoi.felograms.listener;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.hologram.creation.AddLineHologramCreationProcessor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.message.MessageChannelEvent;

import java.util.UUID;

public class ChatListener {

    @Listener
    public void onChat(MessageChannelEvent.Chat event, @First Player player) {
        UUID uuid = player.getUniqueId();
        String rawMessage = event.getRawMessage().toPlain();

        if (Felograms.getInstance().getHologramCreationManager().getCreators().contains(uuid)) {
            event.setCancelled(true);

            Felograms.getInstance().getHologramCreationManager().process(new AddLineHologramCreationProcessor(), player, rawMessage);
        }
    }

}
