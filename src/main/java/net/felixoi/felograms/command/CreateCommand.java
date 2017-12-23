package net.felixoi.felograms.command;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.command.AcceptedSources;
import net.felixoi.felograms.api.command.Aliases;
import net.felixoi.felograms.api.command.Command;
import net.felixoi.felograms.api.command.Permission;
import net.felixoi.felograms.api.message.Message;
import net.felixoi.felograms.api.message.MessageTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

@Aliases("create")
@Permission("felograms.create")
@AcceptedSources(Player.class)
public class CreateCommand extends Command {

    public CreateCommand() {
        super(GenericArguments.onlyOne(GenericArguments.string(Text.of("hologramID"))));
    }

    @Override
    public CommandResult process(CommandSource source, CommandContext args) {
        Player player = (Player) source;
        String hologramID = args.<String>getOne("hologramID").get();

        if(Felograms.getInstance().getHologramManager().getHologramIDs().contains(hologramID)) {
            Message.builder().messageType(MessageTypes.ERROR).localizedLine("hologram.id_exists", hologramID).sendTo(player).build();
        } else {
            if (Felograms.getInstance().getHologramCreationManager().getCreators().contains(player.getUniqueId())) {
                Message.builder().messageType(MessageTypes.ERROR).localizedLine("creation.already_started").sendTo(player).build();
            } else {
                Felograms.getInstance().getHologramCreationManager().startCreation(player.getUniqueId(), hologramID);
                Message.builder().messageType(MessageTypes.SUCCESS).localizedLine("creation.started", hologramID).sendTo(player).build();
            }
        }

        return CommandResult.success();
    }

}
