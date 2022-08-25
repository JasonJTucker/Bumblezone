package com.telepathicgrunt.the_bumblezone.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Doubles;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.FrontAndTop;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

public class GeneralUtils {

    private static int ACTIVE_ENTITIES = 0;
    private static final Set<Bee> BEE_SET = new HashSet<>();

    public static void updateEntityCount(ServerLevel world) {
        BEE_SET.clear();
        int counter = 0;
        for (Entity entity : world.getAllEntities()) {
            counter++;
            if(entity.getType() == EntityType.BEE) {
                BEE_SET.add((Bee)entity);
            }
        }
        ACTIVE_ENTITIES = counter;
        BEE_SET.removeIf(bee ->
                bee.isPersistenceRequired()
                        || bee.hasHive()
                        || bee.hasCustomName()
                        || bee.isLeashed()
                        || bee.isVehicle());
    }

    public static int getEntityCountInBz() {
        return ACTIVE_ENTITIES;
    }

    public static void adjustEntityCountInBz(int adjust) {
        ACTIVE_ENTITIES += adjust;
    }

    public static Set<Bee> getAllWildBees() {
        return BEE_SET;
    }

    /////////////////////////////

    // Weighted Random from: https://stackoverflow.com/a/6737362
    public static <T> T getRandomEntry(List<Pair<T, Integer>> rlList, RandomSource random) {
        double totalWeight = 0.0;

        // Compute the total weight of all items together.
        for (Pair<T, Integer> pair : rlList) {
            totalWeight += pair.getSecond();
        }

        // Now choose a random item.
        int index = 0;
        for (double randomWeightPicked = random.nextFloat() * totalWeight; index < rlList.size() - 1; ++index) {
            randomWeightPicked -= rlList.get(index).getSecond();
            if (randomWeightPicked <= 0.0) break;
        }

        return rlList.get(index).getFirst();
    }

    ////////////////

    public static BlockPos getRandomBlockposWithinRange(LivingEntity entity, int maxRadius, int minRadius) {
        BlockPos newBeePos;
        newBeePos = new BlockPos(
                entity.getX() + (entity.getRandom().nextInt(maxRadius) + minRadius) * (entity.getRandom().nextBoolean() ? 1 : -1),
                Doubles.constrainToRange(entity.getY() + (entity.getRandom().nextInt(maxRadius) + minRadius) * (entity.getRandom().nextBoolean() ? 1 : -1), 1, 254),
                entity.getZ() + (entity.getRandom().nextInt(maxRadius) + minRadius) * (entity.getRandom().nextBoolean() ? 1 : -1));
        return newBeePos;
    }

    ////////////////

    // Source: https://dzone.com/articles/be-lazy-with-java-8
    public static final class Lazy<T> {

        private volatile T value;

        public T getOrCompute(Supplier<T> supplier) {
            final T result = value; // Just one volatile read
            return result == null ? maybeCompute(supplier) : result;
        }

        private synchronized T maybeCompute(Supplier<T> supplier) {
            if (value == null) {
                value = requireNonNull(supplier.get());
            }
            return value;
        }
    }


    //////////////////////////////////////////

    /**
     * For doing basic trades.
     * Very short and barebone to what I want
     */
    public static class BasicItemTrade implements VillagerTrades.ItemListing  {
        private final Item itemToTrade;
        private final Item itemToReceive;
        private final int amountToGive;
        private final int amountToReceive;
        protected final int maxUses;
        protected final int experience;
        protected final float multiplier;

        public BasicItemTrade(Item itemToTrade, Item itemToReceive, int amountToGive,  int amountToReceive) {
            this(itemToReceive, itemToTrade, amountToGive, amountToReceive, 20, 2, 0.05F);
        }

        public BasicItemTrade(Item itemToTrade, Item itemToReceive, int amountToGive, int amountToReceive, int maxUses, int experience, float multiplier) {
            this.itemToTrade = itemToTrade;
            this.itemToReceive = itemToReceive;
            this.amountToGive = amountToGive;
            this.amountToReceive = amountToReceive;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = multiplier;
        }

        @Override
        public MerchantOffer getOffer(Entity entity, RandomSource random) {
            ItemStack in = new ItemStack(this.itemToTrade, this.amountToGive);
            ItemStack out = new ItemStack(this.itemToReceive, this.amountToReceive);
            return new MerchantOffer(in, out, this.maxUses, this.experience, this.multiplier);
        }
    }

    ///////////////////////

    public static final List<BlockState> VANILLA_CANDLES = ImmutableList.of(
            Blocks.CANDLE.defaultBlockState(),
            Blocks.CYAN_CANDLE.defaultBlockState(),
            Blocks.BLACK_CANDLE.defaultBlockState(),
            Blocks.BLUE_CANDLE.defaultBlockState(),
            Blocks.BROWN_CANDLE.defaultBlockState(),
            Blocks.GRAY_CANDLE.defaultBlockState(),
            Blocks.GREEN_CANDLE.defaultBlockState(),
            Blocks.LIGHT_BLUE_CANDLE.defaultBlockState(),
            Blocks.LIGHT_GRAY_CANDLE.defaultBlockState(),
            Blocks.LIME_CANDLE.defaultBlockState(),
            Blocks.MAGENTA_CANDLE.defaultBlockState(),
            Blocks.ORANGE_CANDLE.defaultBlockState(),
            Blocks.PINK_CANDLE.defaultBlockState(),
            Blocks.PURPLE_CANDLE.defaultBlockState(),
            Blocks.RED_CANDLE.defaultBlockState(),
            Blocks.WHITE_CANDLE.defaultBlockState(),
            Blocks.YELLOW_CANDLE.defaultBlockState()
    );

    //////////////////////////////////////////////

    /**
     * For giving the player an item properly into their inventory
     */
    public static void givePlayerItem(Player playerEntity, InteractionHand hand, ItemStack itemstackToGive, boolean giveContainerItem, boolean shrinkCurrentItem) {
        ItemStack playerItem = playerEntity.getItemInHand(hand);
        ItemStack copiedPlayerItem = playerItem.copy();

        if(shrinkCurrentItem) {
            playerItem.shrink(1);
        }

        // Give item itself to users
        if(!itemstackToGive.isEmpty()) {
            if (playerItem.isEmpty()) {
                // places result item in hand
                playerEntity.setItemInHand(hand, itemstackToGive);
            }
            // places result item in inventory
            else if (!playerEntity.getInventory().add(itemstackToGive)) {
                // drops result item if inventory is full
                playerEntity.drop(itemstackToGive, false);
            }
        }

        // give container item of player's item if specified
        if(giveContainerItem && copiedPlayerItem.getItem().hasCraftingRemainingItem()) {
            ItemStack containerItem = copiedPlayerItem.getItem().getCraftingRemainingItem().getDefaultInstance();
            if (playerEntity.getItemInHand(hand).isEmpty()) {
                // places result item in hand
                playerEntity.setItemInHand(hand, containerItem);
            }
            // places result item in inventory
            else if (!playerEntity.getInventory().add(containerItem)) {
                // drops result item if inventory is full
                playerEntity.drop(containerItem, false);
            }
        }
    }

    //////////////////////////////////////////////

    // More optimized with checking if the jigsaw blocks can connect
    public static boolean canJigsawsAttach(StructureTemplate.StructureBlockInfo jigsaw1, StructureTemplate.StructureBlockInfo jigsaw2) {
        FrontAndTop prop1 = jigsaw1.state.getValue(JigsawBlock.ORIENTATION);
        FrontAndTop prop2 = jigsaw2.state.getValue(JigsawBlock.ORIENTATION);
        String joint = jigsaw1.nbt.getString("joint");
        if(joint.isEmpty()) {
            joint = prop1.front().getAxis().isHorizontal() ? "aligned" : "rollable";
        }

        boolean isRollable = joint.equals("rollable");
        return prop1.front() == prop2.front().getOpposite() &&
                (isRollable || prop1.top() == prop2.top()) &&
                jigsaw1.nbt.getString("target").equals(jigsaw2.nbt.getString("name"));
    }
}
