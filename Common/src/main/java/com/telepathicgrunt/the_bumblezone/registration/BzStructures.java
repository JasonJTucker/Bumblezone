package com.telepathicgrunt.the_bumblezone.registration;

import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import com.telepathicgrunt.the_bumblezone.world.structures.GenericOptimizedStructure;
import com.telepathicgrunt.the_bumblezone.world.structures.HoneyCaveRoomStructure;
import com.telepathicgrunt.the_bumblezone.world.structures.PollinatedStreamStructure;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;

public class BzStructures {

    public static final RegistrationProvider<StructureType<?>> STRUCTURES = RegistrationProvider.get(Registry.STRUCTURE_TYPE_REGISTRY, BumblezoneCommon.MODID);

    public static final RegistryObject<StructureType<PollinatedStreamStructure>> POLLINATED_STREAM = STRUCTURES.register("pollinated_stream", () -> () -> PollinatedStreamStructure.CODEC);
    public static final RegistryObject<StructureType<HoneyCaveRoomStructure>> HONEY_CAVE_ROOM = STRUCTURES.register("honey_cave_room", () -> () -> HoneyCaveRoomStructure.CODEC);
    public static final RegistryObject<StructureType<GenericOptimizedStructure>> GENERIC_OPTIMIZED_STRUCTURE = STRUCTURES.register("generic_optimized_structure", () -> () -> GenericOptimizedStructure.CODEC);
}
