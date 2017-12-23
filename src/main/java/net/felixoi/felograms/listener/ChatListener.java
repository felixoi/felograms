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
            event.setChannel(MessageChannel.TO_NONE);

            List<HologramCreationProcessor> activeProcessors = Arrays.asList(
                    new AddLineHologramCreationProcessor(),
                    new AddImageHologramCreationProcessor(),
                    new ExitHologramCreationProcessor(),
                    new FinishHologramCreationProcessor(),
                    new StatusHologramCreationProcessor());

            Optional<HologramCreationProcessor> processor = activeProcessors.stream().filter(hologramCreationProcessor -> {
                StringBuilder stringBuilder = new StringBuilder();

                for (String alias : hologramCreationProcessor.getAliases()) {
                    stringBuilder.append(alias).append("|");
                }

                String aliasRegEx = stringBuilder.toString().substring(0, stringBuilder.length() - 1);

                return rawMessage.matches("(?i:^<(" + aliasRegEx + ")( (.*))?)");
            }).findFirst();

            if (processor.isPresent()) {
                Felograms.getInstance().getHologramCreationManager().process(processor.get(), uuid, player, rawMessage.substring(rawMessage.indexOf(" ") + 1), player.getLocation());
            } else {
                Felograms.getInstance().getHologramCreationManager().process(new AddLineHologramCreationProcessor(), uuid, player, rawMessage, player.getLocation());
            }
        }
    }

}
