package com.telepathicgrunt.the_bumblezone.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.the_bumblezone.modinit.BzBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class GiantHoneyCrystalFeature extends Feature<NoneFeatureConfiguration> {

    public GiantHoneyCrystalFeature(Codec<NoneFeatureConfiguration> configFactory) {
        super(configFactory);
    }

    /**
     * Place crystal block attached to a block if it is buried underground or underwater
     */
    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        BlockPos.MutableBlockPos blockpos$Mutable = new BlockPos.MutableBlockPos();
        WorldGenLevel level = context.level();
        RandomSource random = context.random();
        BlockPos origin = context.origin();

        if (level.getBlockState(origin).canOcclude()) {
            return false;
        }

        boolean validSpot = false;
        boolean superSlant = false;
        Direction wallDirection = null;
        for (Direction direction : Direction.Plane.VERTICAL) {
            blockpos$Mutable.set(origin).move(direction, 5);
            BlockState state = level.getBlockState(blockpos$Mutable);
            if (state.canOcclude()) {
                validSpot = true;
                break;
            }
        }

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            blockpos$Mutable.set(origin).move(direction, 1);
            BlockState state = level.getBlockState(blockpos$Mutable);
            if (state.canOcclude()) {
                superSlant = true;
                wallDirection = direction;
                validSpot = true;
            }
        }

        if (!validSpot) {
            return false;
        }

        blockpos$Mutable.set(origin).move(Direction.UP, 5);
        int directionSign = level.getBlockState(blockpos$Mutable).canOcclude() ? -1 : 1;
        int currentY = origin.getY() - (directionSign * 5);
        int thickness = random.nextInt(3) + 4;
        int height = random.nextInt(5) + 12;
        int slantAmountX = random.nextInt(10) * (random.nextBoolean() ? -1 : 1);
        int slantAmountZ = random.nextInt(10) * (random.nextBoolean() ? -1 : 1);
        if (random.nextInt(4) == 0) {
            slantAmountX = 0;
        }
        if (random.nextInt(4) == 0) {
            slantAmountZ = 0;
        }

        if (superSlant) {
            slantAmountX = -wallDirection.getStepX() * (random.nextInt(2) + 1);
            slantAmountZ = -wallDirection.getStepZ() * (random.nextInt(2) + 1);
        }

        for (int layer = 0; layer < height; layer++) {
            float currentThickness = thickness;
            int currentXSlant = slantAmountX == 0 ? 0 : layer / slantAmountX;
            int currentZSlant = slantAmountZ == 0 ? 0 : layer / slantAmountZ;
            if (layer == 0) {
                currentThickness -= 2;
            }
            else if (layer == 1) {
                currentThickness -= 1;
            }
            else if (layer == height - 1) {
                currentThickness = 0.5f;
                currentXSlant = slantAmountX == 0 ? 0 : (layer - 1) / slantAmountX;
                currentZSlant = slantAmountZ == 0 ? 0 : (layer - 1) / slantAmountZ;
            }
            else {
                currentThickness *= Math.max(((height - layer) / (float)height), 0.2f);
                currentThickness = Math.max(currentThickness, 1.1f);
            }

            for (int x = (int) -currentThickness; x <= currentThickness; x++) {
                for (int z = (int) -currentThickness; z <= currentThickness; z++) {
                    if ((x * x) + (z * z) < (currentThickness * currentThickness)) {
                        blockpos$Mutable.set(
                                origin.getX() + x + currentXSlant,
                                currentY,
                                origin.getZ() + z + currentZSlant);

                        BlockState state = level.getBlockState(blockpos$Mutable);
                        if (!state.canOcclude()) {
                            level.setBlock(
                                    blockpos$Mutable,
                                    BzBlocks.GLISTERING_HONEY_CRYSTAL.get().defaultBlockState(),
                                    3);
                        }
                    }
                }
            }

            currentY += directionSign;
        }

        return false;
    }
}