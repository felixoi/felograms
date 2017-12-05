package net.felixoi.felograms.command;

import net.felixoi.felograms.Felograms;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class CreateCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        if (src instanceof Player) {
            Player player = (Player) src;

            String id = args.<String>getOne("id").get();

            player.sendMessage(Text.of("Starting creation of: " + id));

            Felograms.getInstance().getHologramCreationManager().startCreationProcess(player.getUniqueId(), id);

            return CommandResult.success();
        } else {
            return CommandResult.empty();
        }
    }

}
