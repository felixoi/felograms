package net.felixoi.felograms.command.element;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.util.LocaleUtil;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class HologramCommandElement extends CommandElement {

    public HologramCommandElement(Text key) {
        super(key);
    }

    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String hologramID = args.next();

        Optional<Hologram> hologram = Felograms.getInstance().getHologramStore().get(hologramID);
        if (hologram.isPresent()) {
            return hologram.get();
        } else {
            throw args.createError(Text.of(LocaleUtil.getMessage("hologram.not_found", hologramID)));
        }
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        List<String> choices = Felograms.getInstance().getHologramStore().getAll().stream().map(Hologram::getName).collect(Collectors.toList());

        final Optional<String> nextArg = args.nextIfPresent();
        if (nextArg.isPresent()) {
            choices = choices.stream().filter(hologramID -> hologramID.startsWith(nextArg.get())).collect(Collectors.toList());
        }

        return choices;
    }

}
