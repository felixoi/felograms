package net.felixoi.felograms.command;

import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.command.Children;
import net.felixoi.felograms.internal.command.Command;
import net.felixoi.felograms.internal.command.Permission;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.text.Text;

@Aliases({"felograms", "felogram", "felo", "holograms", "hologram", "holo"})
@Permission("felograms.info")
@Children({CreateCommand.class, DisableCommand.class, EnableCommand.class, RemoveCommand.class})
public final class FelogramsCommand extends Command {

    @Override
    public CommandResult process(CommandSource source, CommandContext args) {
        source.sendMessage(Text.of("test"));
        return CommandResult.success();
    }

}
