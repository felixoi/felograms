package net.felixoi.felograms.hologram.store;

import com.google.common.collect.Lists;
import com.google.common.reflect.TypeToken;
import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.internal.configuration.Configuration;
import net.felixoi.felograms.internal.hologram.HologramStore;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FileConfigurationHologramStore implements HologramStore {

    private final Configuration configuration;

    public FileConfigurationHologramStore(Configuration configuration) {
        this.configuration = checkNotNull(configuration, "The variable 'configuration' in FileConfigurationHologramStore#FileConfigurationHologramStore(configuration) cannot be null.");
    }

    @Override
    public List<Hologram> loadHolograms() {
        try {
            return this.configuration.getRoot().getNode("holograms").getList(TypeToken.of(Hologram.class));
        } catch (ObjectMappingException e) {
            Felograms.getInstance().getLogger().error("Failed to load holograms from file configuration!");
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void saveHolograms(List<Hologram> holograms) {
        List<Hologram> list = Lists.newArrayList(holograms);

        try {
            this.configuration.getRoot().getNode("holograms").setValue(new TypeToken<List<Hologram>>() {
            }, list);
        } catch (ObjectMappingException e) {
            Felograms.getInstance().getLogger().error("Failed to save holograms to file configuration!");
            e.printStackTrace();
        }
        this.configuration.save();
    }

}
