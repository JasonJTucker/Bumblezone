package com.telepathicgrunt.the_bumblezone.enchantments;

import com.telepathicgrunt.the_bumblezone.modinit.BzCriterias;
import com.telepathicgrunt.the_bumblezone.modinit.BzEnchantments;
import com.telepathicgrunt.the_bumblezone.utils.GeneralUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashSet;
import java.util.Set;

public class CombCutterEnchantment extends Enchantment {
    private static final GeneralUtils.Lazy<Set<Block>> TARGET_BLOCKS = new GeneralUtils.Lazy<>();
    private static final GeneralUtils.Lazy<Set<Block>> LESSER_TARGET_BLOCKS = new GeneralUtils.Lazy<>();

    public CombCutterEnchantment() {
        super(Enchantment.Rarity.RARE, EnchantmentCategory.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public Set<Block> getTargetBlocks() {
        return TARGET_BLOCKS.getOrCompute(() -> {
            Set<Block> validBlocks = new HashSet<>();
            ForgeRegistries.BLOCKS.getEntries().forEach(entry ->{
                if(entry.getKey().location().getPath().contains("comb")) {
                    validBlocks.add(entry.getValue());
                }
            });
            return validBlocks;
        });
    }

    public Set<Block> getLesserTargetBlocks() {
        return LESSER_TARGET_BLOCKS.getOrCompute(() -> {
            Set<Block> validBlocks = new HashSet<>();
            ForgeRegistries.BLOCKS.getEntries().forEach(entry ->{
                String path = entry.getKey().location().getPath();
                if(entry.getValue() instanceof BeehiveBlock || path.contains("hive") || path.contains("nest") || (path.contains("wax") && !path.contains("waxed"))) {
                    validBlocks.add(entry.getValue());
                }
            });
            return validBlocks;
        });
    }

    public static void attemptFasterMining(PlayerEvent.BreakSpeed event){
        if(BzEnchantments.COMB_CUTTER.get().getTargetBlocks().contains(event.getState().getBlock())){
            mineFaster(event, false);
        }
        else if(BzEnchantments.COMB_CUTTER.get().getLesserTargetBlocks().contains(event.getState().getBlock())){
            mineFaster(event, true);
        }
    }

    private static void mineFaster(PlayerEvent.BreakSpeed event, boolean lesserTarget) {
        float breakSpeed = event.getNewSpeed();
        Player playerEntity = event.getEntity();
        ItemStack itemStack = playerEntity.getMainHandItem();
        int equipmentLevel = EnchantmentHelper.getEnchantmentLevel(BzEnchantments.COMB_CUTTER.get(), playerEntity);
        if (equipmentLevel > 0 && !itemStack.isEmpty()) {
            breakSpeed += (float)(equipmentLevel * equipmentLevel + (lesserTarget ? 3 : 13));
        }
        event.setNewSpeed(breakSpeed);
    }

    public static void increasedCombDrops(Player playerEntity, Level world, BlockPos pos) {
        ItemStack itemStack = playerEntity.getMainHandItem();
        int equipmentLevel = EnchantmentHelper.getEnchantmentLevel(BzEnchantments.COMB_CUTTER.get(), playerEntity);
        if (equipmentLevel > 0 && !itemStack.isEmpty()) {
            Block.popResource(world, pos, new ItemStack(Items.HONEYCOMB, equipmentLevel * 3));
            if(playerEntity instanceof ServerPlayer) {
                BzCriterias.COMB_CUTTER_EXTRA_DROPS_TRIGGER.trigger((ServerPlayer) playerEntity);
            }
        }
    }

    @Override
    public int getMinCost(int level) {
        return 10 * (level - 1);
    }

    @Override
    public int getMaxCost(int level) {
        return super.getMinCost(level) + 13;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean canEnchant(ItemStack stack) {
        return stack.getItem() instanceof ShearsItem || stack.getItem() instanceof SwordItem || stack.is(Items.BOOK);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack) {
        return this.canEnchant(stack);
    }
}
