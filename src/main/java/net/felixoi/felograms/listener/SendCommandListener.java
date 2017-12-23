package net.felixoi.felograms.listener;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.HologramCreationProcessor;
import net.felixoi.felograms.hologram.creation.*;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.command.SendCommandEvent;
import org.spongepowered.api.event.filter.cause.First;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SendCommandListener {

    @Listener
    public void onCommand(SendCommandEvent event, @First Player player) {
        UUID uuid = player.getUniqueId();

        if (Felograms.getInstance().getHologramCreationManager().getCreators().contains(uuid)) {
            event.setCancelled(true);

            List<HologramCreationProcessor> activeProcessors = Arrays.asList(
                    new AddLineHologramCreationProcessor(),
                    new AddImageHologramCreationProcessor(),
                    new ExitHologramCreationProcessor(),
                    new FinishHologramCreationProcessor(),
                    new StatusHologramCreationProcessor(),
                    new IDHologramCreationProcessor());

            Optional<HologramCreationProcessor> processor = activeProcessors.stream().filter(hologramCreationProcessor -> {
                StringBuilder stringBuilder = new StringBuilder();

                for (String alias : hologramCreationProcessor.getAliases()) {
                    stringBuilder.append(alias).append("|");
                }

                String aliasRegEx = stringBuilder.toString().substring(0, stringBuilder.length() - 1);

                return event.getCommand().matches("(?i:(" + aliasRegEx + "))");
            }).findFirst();

            if (processor.isPresent()) {
                Felograms.getInstance().getHologramCreationManager().process(processor.get(), uuid, player, event.getArguments(), player.getLocation());
            } else {

            }
        }
    }

}
