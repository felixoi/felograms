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
        super(GenericArguments.onlyOne(GenericArguments.string(Text.of("hologramName"))));
    }

    @Override
    public CommandResult process(CommandSource source, CommandContext args) {
        Player player = (Player) source;
        String hologramName = args.<String>getOne("hologramName").get();

        if (Felograms.getInstance().getHologramStore().getAll().stream().anyMatch(hologram -> hologram.getName().equalsIgnoreCase(hologramName))) {
            Message.ofLocalized(MessageTypes.ERROR, "hologram.name_exists", hologramName).sendTo(player);
        } else {
            if (Felograms.getInstance().getHologramCreationManager().getCreators().contains(player.getUniqueId())) {
                Message.ofLocalized(MessageTypes.ERROR, "creation.start.already").sendTo(player);
            } else {
                Felograms.getInstance().getHologramCreationManager().startCreation(player.getUniqueId(), hologramName);
                Message.ofLocalized(MessageTypes.SUCCESS, "creation.start.success", hologramName).sendTo(player);
            }
        }

        return CommandResult.success();
    }

}
