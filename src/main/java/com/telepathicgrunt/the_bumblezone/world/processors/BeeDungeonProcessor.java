package com.telepathicgrunt.the_bumblezone.world.processors;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.telepathicgrunt.the_bumblezone.Bumblezone;
import com.telepathicgrunt.the_bumblezone.blocks.HoneyCrystal;
import com.telepathicgrunt.the_bumblezone.blocks.HoneycombBrood;
import com.telepathicgrunt.the_bumblezone.modcompat.*;
import com.telepathicgrunt.the_bumblezone.modinit.BzBlocks;
import com.telepathicgrunt.the_bumblezone.modinit.BzFluids;
import com.telepathicgrunt.the_bumblezone.modinit.BzProcessors;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.command.arguments.BlockStateParser;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

import static java.lang.Integer.parseInt;

/**
 * POOL ENTRY MUST BE USING legacy_single_pool_element OR ELSE THE STRUCTURE BLOCK IS REMOVED BEFORE THIS PROCESSOR RUNS.
 */
public class BeeDungeonProcessor extends StructureProcessor {

    public static final Codec<BeeDungeonProcessor> CODEC = Codec.unit(BeeDungeonProcessor::new);
    private BeeDungeonProcessor() { }

    @Override
    public Template.BlockInfo func_230386_a_(IWorldReader worldView, BlockPos pos, BlockPos blockPos, Template.BlockInfo structureBlockInfoLocal, Template.BlockInfo structureBlockInfoWorld, PlacementSettings structurePlacementData) {
        BlockState blockState = structureBlockInfoWorld.state;
        BlockPos worldPos = structureBlockInfoWorld.pos;
        Random random = new SharedSeedRandom();
        random.setSeed(worldPos.toLong() * worldPos.getY());

        // placing altar blocks
        if (blockState.isIn(Blocks.STRUCTURE_BLOCK)) {
            String metadata = structureBlockInfoWorld.nbt.getString("metadata");
            BlockState belowBlock = worldView.getChunk(worldPos).getBlockState(worldPos);

            //altar blocks cannot be placed on air
            if(belowBlock.isAir()){
                blockState = Blocks.CAVE_AIR.getDefaultState();
            }
            else{
                switch (metadata){
                    case "center": {
                        if(ModChecker.buzzierBeesPresent && random.nextFloat() < 0.8f && Bumblezone.BzModCompatibilityConfig.allowScentedCandlesBeeDungeon.get()) {
                            blockState = BuzzierBeesRedirection.BBGetRandomTier3Candle(
                                    random,
                                    Bumblezone.BzModCompatibilityConfig.powerfulCandlesRarityBeeDungeon.get()+1,
                                    random.nextInt(random.nextInt(random.nextInt(3)+1)+1)+1,
                                    false,
                                    true);
                        }
                        else if(ModChecker.charmPresent && !ModChecker.buzzierBeesPresent &&
                                Bumblezone.BzModCompatibilityConfig.allowCCandlesBeeDungeon.get() && random.nextFloat() < 0.33f)
                        {
                            blockState = CharmRedirection.CGetCandle(false, true);
                        }
                        else if (ModChecker.buzzierBeesPresent || random.nextFloat() < 0.6f) {
                            blockState = BzBlocks.HONEY_CRYSTAL.get().getDefaultState();
                        }
                        else {
                            blockState = Blocks.CAVE_AIR.getDefaultState();
                        }
                    }
                    break;
                    case "inner_ring": {
                        if(ModChecker.buzzierBeesPresent && random.nextFloat() < 0.4f && Bumblezone.BzModCompatibilityConfig.allowScentedCandlesBeeDungeon.get()) {
                            blockState = BuzzierBeesRedirection.BBGetRandomTier2Candle(
                                            random,
                                            Bumblezone.BzModCompatibilityConfig.powerfulCandlesRarityBeeDungeon.get(),
                                            random.nextInt(random.nextInt(3)+1)+1,
                                            false,
                                            true);
                        }
                        else if(ModChecker.charmPresent && Bumblezone.BzModCompatibilityConfig.allowCCandlesBeeDungeon.get() &&
                                (ModChecker.buzzierBeesPresent ? random.nextFloat() < 0.05f : random.nextFloat() < 0.25f))
                        {
                            blockState = CharmRedirection.CGetCandle(false, true);
                        }
                        else if (ModChecker.buzzierBeesPresent ? random.nextBoolean() : random.nextFloat() < 0.35f) {
                            blockState = BzBlocks.HONEY_CRYSTAL.get().getDefaultState();
                        }
                        else {
                            blockState = Blocks.CAVE_AIR.getDefaultState();
                        }
                    }
                    break;
                    case "outer_ring": {
                        if(ModChecker.buzzierBeesPresent && random.nextFloat() < 0.25f && Bumblezone.BzModCompatibilityConfig.allowScentedCandlesBeeDungeon.get()) {
                            blockState = BuzzierBeesRedirection.BBGetRandomTier1Candle(
                                            random,
                                            random.nextInt(3)+1,
                                            false,
                                            true);
                        }
                        else if(ModChecker.charmPresent && Bumblezone.BzModCompatibilityConfig.allowCCandlesBeeDungeon.get() &&
                                (ModChecker.buzzierBeesPresent ? random.nextFloat() < 0.1f : random.nextFloat() < 0.2f))
                        {
                            blockState = CharmRedirection.CGetCandle(false, true);
                        }
                        else if (random.nextFloat() < 0.45f) {
                            blockState = BzBlocks.HONEY_CRYSTAL.get().getDefaultState();
                        }
                        else {
                            blockState = Blocks.CAVE_AIR.getDefaultState();
                        }
                    }
                    break;
                    default: break;
                }
            }
        }

        // main body and ceiling
        else if(blockState.isIn(Blocks.HONEYCOMB_BLOCK) || blockState.isIn(BzBlocks.FILLED_POROUS_HONEYCOMB.get())){
            if(ModChecker.productiveBeesPresent && random.nextFloat() < Bumblezone.BzModCompatibilityConfig.PBOreHoneycombSpawnRateBeeDungeon.get()) {
                blockState = ProductiveBeesRedirection.PBGetRandomHoneycomb(random, Bumblezone.BzModCompatibilityConfig.PBGreatHoneycombRarityBeeDungeon.get()).getFirst();
            }
            else if(ModChecker.resourcefulBeesPresent && random.nextFloat() < Bumblezone.BzModCompatibilityConfig.RBOreHoneycombSpawnRateBeeDungeon.get()) {
                blockState = ResourcefulBeesRedirection.RBGetRandomHoneycomb(random, Bumblezone.BzModCompatibilityConfig.RBGreatHoneycombRarityBeeDungeon.get()).getFirst();
            }
            else if (random.nextFloat() < 0.4f) {
                blockState = Blocks.HONEYCOMB_BLOCK.getDefaultState();
            }
            else {
                blockState = BzBlocks.FILLED_POROUS_HONEYCOMB.get().getDefaultState();
            }
        }

        // walls
        else if(blockState.isIn(BzBlocks.HONEYCOMB_BROOD.get())){
            if (random.nextFloat() < 0.6f) {
                blockState = BzBlocks.HONEYCOMB_BROOD.get().getDefaultState()
                        .with(HoneycombBrood.STAGE, random.nextInt(3))
                        .with(HoneycombBrood.FACING, blockState.get(HoneycombBrood.FACING));
            }
            else if (random.nextFloat() < 0.2f) {
                blockState = Blocks.HONEY_BLOCK.getDefaultState();
            }
            else {
                blockState = BzBlocks.FILLED_POROUS_HONEYCOMB.get().getDefaultState();
            }
        }

        // sugar water
        else if(blockState.isIn(BzFluids.SUGAR_WATER_BLOCK.get())){
            if(random.nextFloat() < 0.1f){
                blockState = BzBlocks.HONEY_CRYSTAL.get().getDefaultState().with(HoneyCrystal.WATERLOGGED, true);
            }
        }

        return new Template.BlockInfo(worldPos, blockState, structureBlockInfoWorld.nbt);
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return BzProcessors.BEE_DUNGEON_PROCESSOR;
    }
}