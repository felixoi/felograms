package net.felixoi.felograms.api.command;

import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.message.Message;
import net.felixoi.felograms.api.message.MessageTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.Arrays;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkState;

public abstract class Command implements CommandExecutor {

    private final String permission;
    private final String description;
    private final CommandSpec commandSpec;
    private final Class<? extends Command>[] children;
    private final String[] aliases;

    public Command(CommandElement... commandElements) {
        checkState(getClass().isAnnotationPresent(Aliases.class), "Alias annotation for commandSpec class" + getClass().getName() + "is not present.");

        this.aliases = getClass().getAnnotation(Aliases.class).value();
        this.permission = getClass().isAnnotationPresent(Permission.class) ? getClass().getAnnotation(Permission.class).value() : null;
        this.description = getClass().isAnnotationPresent(Description.class) ? getClass().getAnnotation(Description.class).value() : null;
        this.children = getClass().isAnnotationPresent(Children.class) ? getClass().getAnnotation(Children.class).value() : new Class[0];

        CommandSpec.Builder builder = CommandSpec.builder().executor(this).childArgumentParseExceptionFallback(false);

        if (commandElements.length > 0) {
            builder.arguments(commandElements);
        } else {
            builder.arguments(GenericArguments.none());
        }
        if (this.permission != null) {
            builder.permission(this.permission);
        }
        if (this.description != null) {
            builder.description(TextSerializers.FORMATTING_CODE.deserialize(description));
        }

        for (Class<? extends Command> child : this.children) {
            try {
                Command command = child.newInstance();
                builder.child(command.getCommandSpec(), command.getAliases());
            } catch (InstantiationException | IllegalAccessException e) {
                Felograms.getInstance().getLogger().error("Failed to register child commandSpec from class " + getClass().getName());
                e.printStackTrace();
            }
        }

        this.commandSpec = builder.build();
    }

    public abstract CommandResult process(CommandSource source, CommandContext args);

    public Class<? extends Command>[] getChildren() {
        return this.children;
    }

    public String getDescription() {
        return this.description;
    }

    public String[] getAliases() {
        return this.aliases;
    }

    public Optional<String> getPermission() {
        return Optional.ofNullable(this.permission);
    }

    public CommandSpec getCommandSpec() {
        return this.commandSpec;
    }

    @Override
    public CommandResult execute(CommandSource source, CommandContext args) {
        if (getClass().isAnnotationPresent(AcceptedSources.class)) {
            Class<? extends CommandSource>[] classes = getClass().getAnnotation(AcceptedSources.class).value();

            if (Arrays.stream(classes).anyMatch(clazz -> clazz.isAssignableFrom(source.getClass()))) {
                return this.process(source, args);
            } else {
                Message.builder().messageType(MessageTypes.ERROR).localizedLine("command.incorrect_source").sendTo(source).build();
                return CommandResult.success();
            }
        } else {
            return this.process(source, args);
        }
    }

}
