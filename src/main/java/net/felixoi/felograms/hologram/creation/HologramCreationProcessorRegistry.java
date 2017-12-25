package net.felixoi.felograms.hologram.creation;

import net.felixoi.felograms.internal.hologram.HologramCreationManager;

import static com.google.common.base.Preconditions.checkNotNull;

public class HologramCreationProcessorRegistry {

    public static void registerProcessors(HologramCreationManager creationManager) {
        checkNotNull(creationManager, "The variable 'creationManager' in HologramCreationProcessorRegistry#registerProcessors(creationManager) cannot be null.");

        creationManager.registerProcessor(new AddLineHologramCreationProcessor());
        creationManager.registerProcessor(new AddImageHologramCreationProcessor());
        creationManager.registerProcessor(new ExitHologramCreationProcessor());
        creationManager.registerProcessor(new FinishHologramCreationProcessor());
        creationManager.registerProcessor(new StatusHologramCreationProcessor());
        creationManager.registerProcessor(new IDHologramCreationProcessor());
        creationManager.registerProcessor(new DeleteHologramCreationProcessor());
        creationManager.registerProcessor(new InsertHologramCreationProcessor());
        creationManager.registerProcessor(new ModifyHologramCreationProcessor());
    }

}
