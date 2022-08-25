package com.telepathicgrunt.the_bumblezone.registration;

import com.google.common.collect.ImmutableSet;
import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BzPOI {
    public static final RegistrationProvider<PoiType> POI_TYPES = RegistrationProvider.get(Registry.POINT_OF_INTEREST_TYPE_REGISTRY, BumblezoneCommon.MODID);

    public static final RegistryObject<PoiType> BROOD_BLOCK_POI = POI_TYPES.register("brood_block_poi", () -> new PoiType(ImmutableSet.copyOf(BzBlocks.HONEYCOMB_BROOD.get().getStateDefinition().getPossibleStates()), 0, 1));
}
