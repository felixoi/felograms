package net.felixoi.felograms.command;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.text.Text;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandRegistry {

    private static Map<List<String>, CommandCallable> children = new HashMap<>();

    public static void registerCommands(PluginContainer pluginContainer) {
        registerSubCommands();

        CommandSpec felogramsCommand = CommandSpec.builder()
                .permission("felograms.info")
                .description(Text.of("A simple hologram plugin"))
                .executor(new FelogramsCommand())
                .children(children)
                .build();
        Sponge.getCommandManager().register(pluginContainer, felogramsCommand, "felograms", "felogram", "felo", "holo", "hologram", "holograms");
    }

    private static void registerSubCommands() {
        CommandSpec createCommand = CommandSpec.builder()
                .permission("felograms.create")
                .description(Text.of("Create a hologram"))
                .executor(new CreateCommand())
                .build();
        children.put(Arrays.asList("create", "c", "new"), createCommand);
    }

}
