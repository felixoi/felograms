package net.felixoi.felograms.api.command;

import org.spongepowered.api.command.CommandSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AcceptedSources {

    Class<? extends CommandSource>[] value();

}
