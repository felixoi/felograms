package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.hologram.creation.processor.AddImageHologramCreationProcessor;
import net.felixoi.felograms.hologram.creation.processor.AddLineHologramCreationProcessor;
import net.felixoi.felograms.hologram.creation.processor.DeleteHologramCreationProcessor;
import net.felixoi.felograms.hologram.creation.processor.ExitHologramCreationProcessor;
import net.felixoi.felograms.hologram.creation.processor.FinishHologramCreationProcessor;
import net.felixoi.felograms.hologram.creation.processor.NameHologramCreationProcessor;
import net.felixoi.felograms.hologram.creation.processor.InsertHologramCreationProcessor;
import net.felixoi.felograms.hologram.creation.processor.ModifyHologramCreationProcessor;
import net.felixoi.felograms.hologram.creation.processor.StatusHologramCreationProcessor;
import net.felixoi.felograms.internal.hologram.creation.HologramCreationManager;

import static com.google.common.base.Preconditions.checkNotNull;

public final class HologramCreationProcessorRegistry {

    public static void registerProcessors(HologramCreationManager creationManager) {
        checkNotNull(creationManager, "The variable 'creationManager' in HologramCreationProcessorRegistry#registerProcessors(creationManager) cannot be null.");

        creationManager.registerProcessor(new AddLineHologramCreationProcessor());
        creationManager.registerProcessor(new AddImageHologramCreationProcessor());
        creationManager.registerProcessor(new ExitHologramCreationProcessor());
        creationManager.registerProcessor(new FinishHologramCreationProcessor());
        creationManager.registerProcessor(new StatusHologramCreationProcessor());
        creationManager.registerProcessor(new NameHologramCreationProcessor());
        creationManager.registerProcessor(new DeleteHologramCreationProcessor());
        creationManager.registerProcessor(new InsertHologramCreationProcessor());
        creationManager.registerProcessor(new ModifyHologramCreationProcessor());
    }

}
