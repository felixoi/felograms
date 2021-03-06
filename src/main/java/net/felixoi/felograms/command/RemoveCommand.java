package net.felixoi.felograms.command;

import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.command.element.HologramCommandElement;
import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.command.Command;
import net.felixoi.felograms.internal.command.Permission;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

@Aliases({"remove", "delete"})
@Permission("felograms.remove")
public final class RemoveCommand extends Command {

    public RemoveCommand() {
        super(GenericArguments.onlyOne(new HologramCommandElement(Text.of("hologramName"))));
    }

    @Override
    public CommandResult process(CommandSource source, CommandContext args) {
        Hologram hologram = args.<Hologram>getOne("hologramName").get();

        hologram.remove();
        Message.ofLocalized(MessageTypes.SUCCESS, "hologram.removed", hologram.getName()).sendTo(source);

        return CommandResult.success();
    }

}
