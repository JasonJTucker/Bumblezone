package com.telepathicgrunt.bumblezone.world.dimension.layer;

import com.telepathicgrunt.bumblezone.world.dimension.BzBiomeProvider;
import net.minecraft.world.biome.layer.type.CrossSamplingLayer;
import net.minecraft.world.biome.layer.util.LayerRandomnessSource;


public enum BzBiomePollinatedFieldsLayer implements CrossSamplingLayer {
    INSTANCE;

    public int sample(LayerRandomnessSource context, int n, int e, int s, int w, int center) {
        int hivePillarId = BzBiomeProvider.LAYERS_BIOME_REGISTRY.getRawId(
                BzBiomeProvider.LAYERS_BIOME_REGISTRY.get(BzBiomeProvider.POLLINATED_PILLAR));
        int pollinatedFields = BzBiomeProvider.LAYERS_BIOME_REGISTRY.getRawId(
                BzBiomeProvider.LAYERS_BIOME_REGISTRY.get(BzBiomeProvider.POLLINATED_FIELDS));

        if(center != hivePillarId){
            boolean borderingHivePillar = false;

            if((n == hivePillarId || e == hivePillarId) ||
                (w == hivePillarId || s == hivePillarId)) {
                borderingHivePillar = true;
            }

            if (borderingHivePillar) {
                return pollinatedFields;
            }
        }
        return center;
    }
}