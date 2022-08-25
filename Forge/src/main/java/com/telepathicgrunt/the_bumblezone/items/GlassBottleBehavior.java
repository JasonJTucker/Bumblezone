package com.telepathicgrunt.the_bumblezone.items;

import com.telepathicgrunt.the_bumblezone.fluids.HoneyFluidBlock;
import com.telepathicgrunt.the_bumblezone.fluids.SugarWaterFluid;
import com.telepathicgrunt.the_bumblezone.modinit.BzItems;
import com.telepathicgrunt.the_bumblezone.modinit.BzTags;
import com.telepathicgrunt.the_bumblezone.utils.GeneralUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;

public class GlassBottleBehavior {

    public static boolean useBottleOnSugarWater(Level world, Player playerEntity, InteractionHand playerHand, BlockPos blockPos) {
        if (world.getFluidState(blockPos).getType() instanceof SugarWaterFluid) {
            ItemStack itemstack = playerEntity.getItemInHand(playerHand);

            if (itemstack.getItem() == Items.GLASS_BOTTLE) {
                world.playSound(playerEntity, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);

                if(!playerEntity.isCreative()) {
                    GeneralUtils.givePlayerItem(playerEntity, playerHand, new ItemStack(BzItems.SUGAR_WATER_BOTTLE.get()), false, true);
                }
                return true;
            }
        }

        return false;
    }


    public static boolean useBottleOnHoneyFluid(Level world, Player playerEntity, InteractionHand playerHand, BlockPos blockPos) {
        FluidState currentFluidState = world.getFluidState(blockPos);
        if (currentFluidState.is(BzTags.BZ_HONEY_FLUID) && currentFluidState.isSource()) {
            world.setBlock(blockPos, currentFluidState.createLegacyBlock().setValue(HoneyFluidBlock.LEVEL, 5), 3); // reduce honey
            world.playSound(playerEntity, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);

            if(!playerEntity.isCreative()) {
                GeneralUtils.givePlayerItem(playerEntity, playerHand, new ItemStack(Items.HONEY_BOTTLE), false, true);
            }

            world.playSound(playerEntity, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
            world.gameEvent(playerEntity, GameEvent.FLUID_PICKUP, blockPos);
            return true;
        }

        return false;
    }

    public static boolean useBottleOnRoyalJellyFluid(Level world, Player playerEntity, InteractionHand playerHand, BlockPos blockPos) {
        FluidState currentFluidState = world.getFluidState(blockPos);
        if (currentFluidState.is(BzTags.ROYAL_JELLY_FLUID) && currentFluidState.isSource()) {
            world.setBlock(blockPos, currentFluidState.createLegacyBlock().setValue(HoneyFluidBlock.LEVEL, 5), 3); // reduce honey
            world.playSound(playerEntity, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);

            if(!playerEntity.isCreative()) {
                GeneralUtils.givePlayerItem(playerEntity, playerHand, new ItemStack(BzItems.ROYAL_JELLY_BOTTLE.get()), false, true);
            }

            world.playSound(playerEntity, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.PLAYERS, 1.0F, 1.0F);
            world.gameEvent(playerEntity, GameEvent.FLUID_PICKUP, blockPos);
            return true;
        }

        return false;
    }

}
