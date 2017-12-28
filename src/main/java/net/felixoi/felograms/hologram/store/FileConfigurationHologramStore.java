package net.felixoi.felograms.hologram.store;

import com.google.common.reflect.TypeToken;
import net.felixoi.felograms.Felograms;
import net.felixoi.felograms.api.hologram.Hologram;
import net.felixoi.felograms.internal.configuration.Configuration;
import net.felixoi.felograms.internal.hologram.HologramStore;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class FileConfigurationHologramStore extends HologramStore {

    private final Configuration configuration;

    public FileConfigurationHologramStore(Configuration configuration) {
        this.configuration = checkNotNull(configuration, "The variable 'configuration' in FileConfigurationHologramStore#FileConfigurationHologramStore(configuration) cannot be null.");
    }

    @Override
    public List<Hologram> load() {
        ConfigurationNode node = this.configuration.getRoot().getNode("holograms");

        if(node.isVirtual()) {
            return Collections.emptyList();
        } else {
            try {
                return node.getList(TypeToken.of(Hologram.class));
            } catch (ObjectMappingException e) {
                Felograms.getInstance().getLogger().error("Failed to load holograms from file configuration!");
                e.printStackTrace();
                return null;
            }
        }
    }

    @Override
    protected void saveHolograms(List<Hologram> holograms) {
        try {
            this.configuration.getRoot().getNode("holograms").setValue(new TypeToken<List<Hologram>>() {
            }, holograms);
        } catch (ObjectMappingException e) {
            Felograms.getInstance().getLogger().error("Failed to save holograms to file configuration!");
            e.printStackTrace();
        }
        this.configuration.save();
    }

}
