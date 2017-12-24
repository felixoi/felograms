package net.felixoi.felograms.api.hologram;

import net.felixoi.felograms.api.command.Aliases;
import net.felixoi.felograms.api.command.Permission;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.channel.MessageReceiver;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.UUID;

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

    public Optional<Hologram.Builder> processInput(Hologram.Builder builder, UUID uuid, Player player, String arguments, Location<World> location){
        if(this.permission != null && !player.hasPermission(this.permission)) {
            return Optional.of(builder);
        }

        return this.process(builder, uuid, player, arguments, location);
    }

    public abstract Optional<Hologram.Builder> process(Hologram.Builder builder, UUID uuid, MessageReceiver creator, String arguments, Location<World> location);

}
