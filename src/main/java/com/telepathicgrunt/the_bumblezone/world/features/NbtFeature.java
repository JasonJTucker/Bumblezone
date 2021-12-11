package com.telepathicgrunt.the_bumblezone.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.the_bumblezone.Bumblezone;
import com.telepathicgrunt.the_bumblezone.mixin.world.StructureTemplateAccessor;
import com.telepathicgrunt.the_bumblezone.utils.GeneralUtils;
import com.telepathicgrunt.the_bumblezone.world.features.configs.NbtFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.ProcessorLists;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.List;
import java.util.Optional;

public class NbtFeature extends Feature<NbtFeatureConfig> {

    public NbtFeature(Codec<NbtFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean place(FeaturePlaceContext<NbtFeatureConfig> context) {
        ResourceLocation nbtRL = GeneralUtils.getRandomEntry(context.config().nbtResourcelocationsAndWeights, context.random());

        StructureManager structureManager = context.level().getLevel().getStructureManager();
        StructureTemplate template = structureManager.getOrCreate(nbtRL);
        if(template == null) {
            Bumblezone.LOGGER.error("Identifier to the specified nbt file was not found! : {}", nbtRL);
            return false;
        }
        Rotation rotation = Rotation.getRandom(context.random());

        // For proper offsetting the feature so it rotate properly around position parameter.
        BlockPos halfLengths = new BlockPos(
                template.getSize().getX() / 2,
                template.getSize().getY() / 2,
                template.getSize().getZ() / 2);

        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos().set(context.origin());

        // offset the feature's position
        BlockPos position = context.origin().above(context.config().structureYOffset);

        StructurePlaceSettings placementsettings = (new StructurePlaceSettings()).setRotation(rotation).setRotationPivot(halfLengths).setIgnoreEntities(false);
        Optional<StructureProcessorList> processor = context.level().getLevel().getServer().registryAccess().registryOrThrow(Registry.PROCESSOR_LIST_REGISTRY).getOptional(context.config().processor);
        processor.orElse(ProcessorLists.EMPTY).list().forEach(placementsettings::addProcessor); // add all processors
        mutable.set(position).move(-halfLengths.getX(), 0, -halfLengths.getZ()); // pivot
        template.placeInWorld(context.level(), position, mutable, placementsettings, context.random(), Block.UPDATE_INVISIBLE);

        // Post-processors
        // For all processors that are sensitive to neighboring blocks such as vines.
        // Post processors will place the blocks themselves so we will not do anything with the return of Structure.process
        placementsettings.clearProcessors();
        Optional<StructureProcessorList> postProcessor = context.level().getLevel().getServer().registryAccess().registryOrThrow(Registry.PROCESSOR_LIST_REGISTRY).getOptional(context.config().postProcessor);
        postProcessor.orElse(ProcessorLists.EMPTY).list().forEach(placementsettings::addProcessor); // add all post processors
        List<StructureTemplate.StructureBlockInfo> list = placementsettings.getRandomPalette(((StructureTemplateAccessor)template).thebumblezone_getBlocks(), mutable).blocks();
        StructureTemplate.processBlockInfos(context.level(), mutable, mutable, placementsettings, list);

        return true;
    }
}
