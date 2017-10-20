package net.felixoi.felograms.command;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.impl.SimpleHologram;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Arrays;

public class CreateCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if(src instanceof Player) {
            Player player = (Player) src;

            Hologram hologram = new SimpleHologram(Arrays.asList(Text.of("test"), Text.of("test123")), player.getLocation());
            Felograms.getInstance().getHologramManager().spawnHologram(hologram);

            return CommandResult.success();
        } else {
            return CommandResult.empty();
        }
    }

}
