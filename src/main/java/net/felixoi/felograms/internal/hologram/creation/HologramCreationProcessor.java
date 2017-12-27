package net.felixoi.felograms.internal.hologram.creation;

import net.felixoi.felograms.internal.command.Aliases;
import net.felixoi.felograms.internal.command.Permission;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public abstract class HologramCreationProcessor {

    private String[] aliases;
    private String permission;

    protected HologramCreationProcessor() {
        checkState(getClass().isAnnotationPresent(Aliases.class), "Aliases annotation for command class " + getClass().getName() + " is not present.");

        this.aliases = getClass().getAnnotation(Aliases.class).value();
        this.permission = getClass().isAnnotationPresent(Permission.class) ? getClass().getAnnotation(Permission.class).value() : null;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public Optional<String> getPermission() {
        return Optional.ofNullable(this.permission);
    }

    public Optional<HologramCreationBuilder> processInput(HologramCreationBuilder builder, Player player, String arguments) {
        checkNotNull(builder, "The variable 'builder' in HologramCreationProcessor#processInput cannot be null.");
        checkNotNull(player, "The variable 'player' in HologramCreationProcessor#processInput cannot be null.");
        checkNotNull(arguments, "The variable 'arguments' in HologramCreationProcessor#processInput cannot be null.");

        if (this.permission != null && !player.hasPermission(this.permission)) {
            return Optional.of(builder);
        }

        return this.process(builder, player, arguments);
    }

    public abstract Optional<HologramCreationBuilder> process(HologramCreationBuilder builder, Player player, String arguments);

}
