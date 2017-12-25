package net.felixoi.felograms.command;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.command.Command;
import net.felixoi.felograms.internal.command.Permission;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import net.felixoi.felograms.command.element.HologramCommandElement;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.text.Text;

@Aliases({"remove", "delete"})
@Permission("felograms.remove")
public class RemoveCommand extends Command {

    public RemoveCommand() {
        super(GenericArguments.onlyOne(new HologramCommandElement(Text.of("hologramID"))));
    }

    @Override
    public CommandResult process(CommandSource source, CommandContext args) {
        Hologram hologram = args.<Hologram>getOne("hologramID").get();

        Felograms.getInstance().getHologramManager().removeHologram(hologram.getID());
        Message.ofLocalized(MessageTypes.SUCCESS, "hologram.removed", hologram.getID()).sendTo(source);

        return CommandResult.success();
    }

}
