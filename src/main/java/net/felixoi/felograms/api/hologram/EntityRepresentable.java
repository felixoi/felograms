package net.felixoi.felograms.api.hologram;

import java.util.List;
import java.util.UUID;

public interface EntityRepresentable {

    List<UUID> getAssociatedEntities();

    void spawnAssociatedEntities();

    void removeAssociatedEntities();

    void forceRespawnAssociatedEntities();

}
