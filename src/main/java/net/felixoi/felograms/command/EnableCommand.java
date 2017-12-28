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

@Aliases("enable")
@Permission("felograms.enable")
public final class EnableCommand extends Command {

    public EnableCommand() {
        super(GenericArguments.onlyOne(new HologramCommandElement(Text.of("hologramID"))));
    }

    @Override
    public CommandResult process(CommandSource source, CommandContext args) {
        Hologram hologram = args.<Hologram>getOne("hologramID").get();

        if (!hologram.areAssociatedEntitiesRemoved()) {
            Message.ofLocalized(MessageTypes.WARNING, "hologram.enabled.already", hologram.getName()).sendTo(source);
        } else {
            hologram.spawnAssociatedEntities();
            Message.ofLocalized(MessageTypes.SUCCESS, "hologram.enabled.success", hologram.getName()).sendTo(source);
        }

        return CommandResult.success();
    }

}
