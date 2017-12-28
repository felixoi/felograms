package net.felixoi.felograms.command;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.internal.command.AcceptedSources;
import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.command.Command;
import net.felixoi.felograms.internal.command.Permission;
import net.felixoi.felograms.internal.message.Message;
import net.felixoi.felograms.internal.message.MessageTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

@Aliases("create")
@Permission("felograms.create")
@AcceptedSources(Player.class)
public final class CreateCommand extends Command {

    public CreateCommand() {
        super(GenericArguments.onlyOne(GenericArguments.string(Text.of("hologramID"))));
    }

    @Override
    public CommandResult process(CommandSource source, CommandContext args) {
        Player player = (Player) source;
        String hologramID = args.<String>getOne("hologramID").get();

        if (Felograms.getInstance().getHologramStore().getAll().stream().anyMatch(hologram -> hologram.getName().equalsIgnoreCase(hologramID))) {
            Message.ofLocalized(MessageTypes.ERROR, "hologram.id_exists", hologramID).sendTo(player);
        } else {
            if (Felograms.getInstance().getHologramCreationManager().getCreators().contains(player.getUniqueId())) {
                Message.ofLocalized(MessageTypes.ERROR, "creation.start.already").sendTo(player);
            } else {
                Felograms.getInstance().getHologramCreationManager().startCreation(player.getUniqueId(), hologramID);
                Message.ofLocalized(MessageTypes.SUCCESS, "creation.start.success", hologramID).sendTo(player);
            }
        }

        return CommandResult.success();
    }

}
