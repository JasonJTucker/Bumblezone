package com.telepathicgrunt.the_bumblezone.blocks;

import com.mojang.math.Vector3f;
import com.telepathicgrunt.the_bumblezone.modinit.BzCriterias;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class RedstoneHoneyWeb extends HoneyWeb {

    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    private static final Vec3[] COLORS = Util.make(new Vec3[16], (vec3s) -> {
        for(int powerLevel = 0; powerLevel <= 15; ++powerLevel) {
            float brightness = (float)powerLevel / 15.0F;
            float red = brightness * 0.6F + (brightness > 0.0F ? 0.4F : 0.3F);
            float green = Mth.clamp(brightness * brightness * 0.7F - 0.5F, 0.0F, 1.0F);
            float blue = Mth.clamp(brightness * brightness * 0.6F - 0.7F, 0.0F, 1.0F);
            vec3s[powerLevel] = new Vec3(red, green, blue);
        }
    });

    public RedstoneHoneyWeb() {
        super(Properties.of(Material.WEB, MaterialColor.COLOR_ORANGE).noCollission().requiresCorrectToolForDrops().strength(4.0F).lightLevel(blockState -> (blockState.getValue(POWER) + 9) / 10));
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(NORTHSOUTH, false)
                .setValue(EASTWEST, false)
                .setValue(UPDOWN, false)
                .setValue(POWER, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockStateBuilder) {
        blockStateBuilder.add(NORTHSOUTH, EASTWEST, UPDOWN, POWER);
    }

    @Override
    public void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        super.entityInside(blockState,level, blockPos, entity);
        VoxelShape shape = this.shapeByIndex[this.getAABBIndex(blockState)];
        shape = shape.move(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (Shapes.joinIsNotEmpty(shape, Shapes.create(entity.getBoundingBox()), BooleanOp.AND)) {
            if (blockState.is(this) && blockState.getValue(POWER) != 15) {
                level.setBlock(blockPos, blockState.setValue(POWER, 15), 3);
                level.scheduleTick(new BlockPos(blockPos), this, 10);

                if(entity instanceof ServerPlayer serverPlayer) {
                    BzCriterias.TRIGGER_REDSTONE_HONEY_WEB_TRIGGER.trigger(serverPlayer);
                }
            }
        }
    }

    @Override
    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource random) {
        if (serverLevel.getBlockState(blockPos).getValue(POWER) == 15) {
            boolean noEntitiesInbounds = true;
            List<? extends Entity> list = serverLevel.getEntities(null, blockState.getShape(serverLevel, blockPos).bounds().move(blockPos));
            if (!list.isEmpty()) {
                for(Entity entity : list) {
                    if (!entity.isIgnoringBlockTriggers()) {
                        noEntitiesInbounds = false;
                        break;
                    }
                }
            }

            if(noEntitiesInbounds) {
                serverLevel.setBlock(blockPos, blockState.setValue(POWER, 0), 3);
            }
            else {
                serverLevel.scheduleTick(new BlockPos(blockPos), this, 10);
            }
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext placeContext) {
        return super.getStateForPlacement(placeContext);
    }

    @Override
    public void onPlace(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState1, boolean pushed) {
        if (!blockState1.is(blockState.getBlock()) && !level.isClientSide) {
            this.updatePowerStrength(level, blockState, blockPos);

            for(Direction direction : Direction.Plane.VERTICAL) {
                level.updateNeighborsAt(blockPos.relative(direction), this);
            }
        }
    }

    @Override
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState blockState1, boolean pushed) {
        if (!pushed && !blockState.is(blockState1.getBlock())) {
            super.onRemove(blockState, level, blockPos, blockState1, pushed);
            if (!level.isClientSide) {
                for (Direction direction : Direction.values()) {
                    level.updateNeighborsAt(blockPos.relative(direction), this);
                }
            }
        }
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos1, boolean b) {
        if (!level.isClientSide) {
            if(blockState.is(this) && blockState.getValue(POWER) != 15) {
                this.updatePowerStrength(level, blockState, blockPos);
            }
        }
    }

    private void updatePowerStrength(Level level, BlockState blockState, BlockPos blockPos) {
        int currentPower = this.calculateTargetStrength(level, blockState, blockPos);
        if (blockState.getValue(POWER) != currentPower) {
            if (level.getBlockState(blockPos) == blockState) {
                level.setBlock(blockPos, blockState.setValue(POWER, currentPower), 3);
            }

            for(Direction direction : Direction.values()) {
                level.updateNeighborsAt(blockPos.relative(direction), this);
            }
        }
    }
    
    private int calculateTargetStrength(Level level, BlockState centerState, BlockPos blockPos) {
        int decreasingPower = 0;
        for(Direction direction : Direction.values()) {
            BlockPos blockpos2 = blockPos.relative(direction);
            BlockState neighborState = level.getBlockState(blockpos2);
            decreasingPower = Math.max(decreasingPower, this.getWireSignal(centerState, neighborState));
        }
        return Math.max(0, decreasingPower - 1);
    }
    
    private int getWireSignal(BlockState centerState, BlockState neighborState) {
        if(neighborState.is(this)) {
            if (centerState.getValue(NORTHSOUTH) && neighborState.getValue(NORTHSOUTH)) {
                return neighborState.getValue(POWER);
            }
            if (centerState.getValue(EASTWEST) && neighborState.getValue(EASTWEST)) {
                return neighborState.getValue(POWER);
            }
            if (centerState.getValue(UPDOWN) && neighborState.getValue(UPDOWN)) {
                return neighborState.getValue(POWER);
            }
        }

        return 0;
    }

    /**
     * Tells game that this block can generate a Redstone signal
     */
    @Override
    public boolean isSignalSource(BlockState state) {
        return true;
    }

    /**
     * Powers the block it's attached to. Or powers blocks next to if it's on the floor
     */
    @Override
    public int getSignal(BlockState thisState, BlockGetter blockAccess, BlockPos pos, Direction direction) {
        //power nearby blocks if on floor
        int power = thisState.getValue(POWER);
        BlockState parentState = blockAccess.getBlockState(pos.relative(direction.getOpposite()));
        if (power > 0 && !parentState.is(this)) {
            if (direction.getAxis() != Direction.Axis.X && thisState.getValue(NORTHSOUTH)) {
                return power;
            }
            if (direction.getAxis() != Direction.Axis.Z && thisState.getValue(EASTWEST)) {
                return power;
            }
            if (direction.getAxis() != Direction.Axis.Y && thisState.getValue(UPDOWN)) {
                return power;
            }
        }
        return 0;
    }

    /**
     * Powers through the block that it is attached to.
     */
    @Override
    public int getDirectSignal(BlockState blockstate, BlockGetter blockAccess, BlockPos pos, Direction side) {
        return 0;
    }


    /**
     * Called periodically clientside on blocks near the player to show redstone particles
     */
    @Override
    public void animateTick(BlockState blockState, Level world, BlockPos position, RandomSource random) {
        super.animateTick(blockState, world, position, random);

        int power = blockState.getValue(POWER);
        if(power != 0) {
            for (int i = 0; i == random.nextInt(35); ++i) {
                this.addRedstoneParticle(world, random, position, blockState.getShape(world, position), power);
            }
        }
    }

    /**
     * intermediary method to apply the blockshape and ranges that the particle can spawn in for the next addRedstoneParticle method
     */
    private void addRedstoneParticle(Level world, RandomSource random, BlockPos blockPos, VoxelShape blockShape, int power) {
        this.addRedstoneParticle(
                world,
                random,
                blockPos.getX() + blockShape.min(Direction.Axis.X),
                blockPos.getX() + blockShape.max(Direction.Axis.X),
                blockPos.getY() + blockShape.min(Direction.Axis.Y),
                blockPos.getY() + blockShape.max(Direction.Axis.Y),
                blockPos.getZ() + blockShape.min(Direction.Axis.Z),
                blockPos.getZ() + blockShape.max(Direction.Axis.Z),
                power);
    }

    /**
     * Adds the actual redstone particle into the world within the given range
     */
    private void addRedstoneParticle(Level world, RandomSource random, double xMin, double xMax, double yMin, double yMax, double zMax, double zMin, int power) {
        world.addParticle(new DustParticleOptions(new Vector3f(COLORS[power]), 1.0F), Mth.lerp(random.nextDouble(), xMin, xMax), Mth.lerp(random.nextDouble(), yMin, yMax), Mth.lerp(random.nextDouble(), zMax, zMin), 0.0D, 0.0D, 0.0D);
    }
}
