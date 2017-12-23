package net.felixoi.felograms.listener;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.hologram.creation.AddLineHologramCreationProcessor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.First;

import java.util.UUID;

public class SendCommandListener {

    @Listener
    public void onCommand(SendCommandEvent event, @First Player player) {
        UUID uuid = player.getUniqueId();
        String rawMessage = "/" + event.getCommand() + (event.getArguments().trim().equals("") ? "": " " + event.getArguments());

        if (Felograms.getInstance().getHologramCreationManager().getCreators().contains(uuid)) {
            event.setCancelled(true);

            Felograms.getInstance().getHologramCreationManager().process(new AddLineHologramCreationProcessor(), uuid, player, rawMessage, player.getLocation());
        }
    }

}
