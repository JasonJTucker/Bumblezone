package com.telepathicgrunt.the_bumblezone.items;

import com.telepathicgrunt.the_bumblezone.entities.nonliving.PollenPuffEntity;
import com.telepathicgrunt.the_bumblezone.modinit.BzItems;
import com.telepathicgrunt.the_bumblezone.modinit.BzSounds;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.dispenser.AbstractProjectileDispenseBehavior;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.Vec3;

public class PollenPuff extends Item {
    public PollenPuff(Item.Properties properties) {
        super(properties);

        DispenserBlock.registerBehavior(this, new AbstractProjectileDispenseBehavior() {
            protected Projectile getProjectile(Level world, Position position, ItemStack itemStack) {
                return Util.make(new PollenPuffEntity(
                        world,
                        position.x(),
                        position.y(),
                        position.z()),
                        (pollenPuffEntity) -> pollenPuffEntity.setItem(itemStack));
            }
        });
    }

    @Override
    public InteractionResult interactLivingEntity(ItemStack stack, Player playerEntity, LivingEntity entity, InteractionHand hand) {
        // No clientside exit early because if we return early, the use method runs on server and thus, throws pollen puff and depollinates bee at same time.
        if (!(entity instanceof Bee beeEntity)) return InteractionResult.PASS;

        ItemStack itemstack = playerEntity.getItemInHand(hand);

        // right clicking on pollinated bee with pollen puff with room, gets pollen puff into hand.
        // else, if done with pollen puff without room, drops pollen puff in world
        if(beeEntity.hasNectar() && itemstack.getItem().equals(BzItems.POLLEN_PUFF)) {
            PollenPuff.spawnItemstackEntity(entity.level, playerEntity.getRandom(), beeEntity.blockPosition(), new ItemStack(BzItems.POLLEN_PUFF.get(), 1));
            playerEntity.swing(hand, true);
            beeEntity.dropOffNectar();
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player playerEntity, InteractionHand hand) {
        ItemStack itemstack = playerEntity.getItemInHand(hand);

        if (!world.isClientSide()) {
            world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), BzSounds.POLLEN_PUFF_THROW.get(), SoundSource.PLAYERS, 0.5F, 0.4F / (playerEntity.getRandom().nextFloat() * 0.4F + 0.8F));
                PollenPuffEntity pollenPuffEntity = new PollenPuffEntity(world, playerEntity);
                pollenPuffEntity.setItem(itemstack);
                pollenPuffEntity.shootFromRotation(playerEntity, playerEntity.getXRot(), playerEntity.getYRot(), 0.0F, 1.5F, 1.0F);
                world.addFreshEntity(pollenPuffEntity);

            playerEntity.awardStat(Stats.ITEM_USED.get(this));
            if (!playerEntity.isCreative()) {
                itemstack.shrink(1);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
    }

    public static void spawnItemstackEntity(Level world, RandomSource random, BlockPos blockPos, ItemStack itemStack) {
        if (!world.isClientSide() && !itemStack.isEmpty()) {
            double x = (double)(random.nextFloat() * 0.5F) + 0.25D;
            double y = (double)(random.nextFloat() * 0.5F) + 0.25D;
            double z = (double)(random.nextFloat() * 0.5F) + 0.25D;
            ItemEntity itemEntity = new ItemEntity(world, (double)blockPos.getX() + x, (double)blockPos.getY() + y, (double)blockPos.getZ() + z, itemStack);
            itemEntity.setDefaultPickUpDelay();
            world.addFreshEntity(itemEntity);
        }
    }

    public static void spawnItemstackEntity(Level world, Vec3 pos, ItemStack itemStack) {
        if (!world.isClientSide() && !itemStack.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, pos.x(), pos.y(), pos.z(), itemStack);
            itemEntity.setDefaultPickUpDelay();
            world.addFreshEntity(itemEntity);
        }
    }
}