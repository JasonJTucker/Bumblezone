package com.telepathicgrunt.the_bumblezone.registration;

import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;


public class BzBlockEntities {
    public static final RegistrationProvider<BlockEntityType<?>> BLOCK_ENTITIES = RegistrationProvider.get(Registry.BLOCK_ENTITY_TYPE_REGISTRY, BumblezoneCommon.MODID);

    //Blocks
    public static final RegistryObject<BlockEntityType<?>> HONEY_COCOON = BLOCK_ENTITIES.register("honey_cocoon", () -> BlockEntityType.Builder.of(HoneyCocoonBlockEntity::new, BzBlocks.HONEY_COCOON.get()).build(null));
    public static final RegistryObject<BlockEntityType<?>> INCENSE_CANDLE = BLOCK_ENTITIES.register("incense_candle", () -> BlockEntityType.Builder.of(IncenseCandleBlockEntity::new, BzBlocks.INCENSE_BASE_CANDLE.get()).build(null));
}