package com.telepathicgrunt.the_bumblezone.modinit;

import com.telepathicgrunt.the_bumblezone.Bumblezone;
import com.telepathicgrunt.the_bumblezone.world.features.BeeDungeon;
import com.telepathicgrunt.the_bumblezone.world.features.CaveSugarWaterfall;
import com.telepathicgrunt.the_bumblezone.world.features.GiantHoneyCrystalFeature;
import com.telepathicgrunt.the_bumblezone.world.features.HoneyCrystalFeature;
import com.telepathicgrunt.the_bumblezone.world.features.HoneycombCaves;
import com.telepathicgrunt.the_bumblezone.world.features.HoneycombHole;
import com.telepathicgrunt.the_bumblezone.world.features.LayeredBlockSurface;
import com.telepathicgrunt.the_bumblezone.world.features.NbtFeature;
import com.telepathicgrunt.the_bumblezone.world.features.PollinatedCaves;
import com.telepathicgrunt.the_bumblezone.world.features.SpiderInfestedBeeDungeon;
import com.telepathicgrunt.the_bumblezone.world.features.StickyHoneyResidueFeature;
import com.telepathicgrunt.the_bumblezone.world.features.WebWall;
import com.telepathicgrunt.the_bumblezone.world.features.configs.BiomeBasedLayerConfig;
import com.telepathicgrunt.the_bumblezone.world.features.configs.HoneyCrystalFeatureConfig;
import com.telepathicgrunt.the_bumblezone.world.features.configs.NbtFeatureConfig;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class BzFeatures {
    public static Feature<NbtFeatureConfig> HONEYCOMB_HOLE = new HoneycombHole(NbtFeatureConfig.CODEC);
    public static Feature<NoneFeatureConfiguration> HONEYCOMB_CAVES = new HoneycombCaves(NoneFeatureConfiguration.CODEC);
    public static Feature<NoneFeatureConfiguration> POLLINATED_CAVES = new PollinatedCaves(NoneFeatureConfiguration.CODEC);
    public static Feature<NoneFeatureConfiguration> CAVE_SUGAR_WATERFALL = new CaveSugarWaterfall(NoneFeatureConfiguration.CODEC);
    public static Feature<HoneyCrystalFeatureConfig> HONEY_CRYSTAL_FEATURE = new HoneyCrystalFeature(HoneyCrystalFeatureConfig.CODEC);
    public static Feature<NoneFeatureConfiguration> GIANT_HONEY_CRYSTAL_FEATURE = new GiantHoneyCrystalFeature(NoneFeatureConfiguration.CODEC);
    public static Feature<NoneFeatureConfiguration> STICKY_HONEY_RESIDUE_FEATURE = new StickyHoneyResidueFeature(NoneFeatureConfiguration.CODEC);
    public static Feature<NbtFeatureConfig> NBT_FEATURE = new NbtFeature(NbtFeatureConfig.CODEC);
    public static Feature<NbtFeatureConfig> BEE_DUNGEON = new BeeDungeon(NbtFeatureConfig.CODEC);
    public static Feature<NbtFeatureConfig> SPIDER_INFESTED_BEE_DUNGEON = new SpiderInfestedBeeDungeon(NbtFeatureConfig.CODEC);
    public static Feature<BiomeBasedLayerConfig> LAYERED_BLOCK_SURFACE = new LayeredBlockSurface(BiomeBasedLayerConfig.CODEC);
    public static Feature<NoneFeatureConfiguration> WEB_WALL = new WebWall(NoneFeatureConfiguration.CODEC);

    public static void registerFeatures() {
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "honeycomb_holes"), HONEYCOMB_HOLE);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "honeycomb_caves"), HONEYCOMB_CAVES);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "pollinated_caves"), POLLINATED_CAVES);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "cave_sugar_waterfall"), CAVE_SUGAR_WATERFALL);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "honey_crystals_feature"), HONEY_CRYSTAL_FEATURE);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "giant_honey_crystal_feature"), GIANT_HONEY_CRYSTAL_FEATURE);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "sticky_honey_residue_feature"), STICKY_HONEY_RESIDUE_FEATURE);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "nbt_feature"), NBT_FEATURE);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "bee_dungeon"), BEE_DUNGEON);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "spider_infested_bee_dungeon"), SPIDER_INFESTED_BEE_DUNGEON);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "layered_block_surface"), LAYERED_BLOCK_SURFACE);
        Registry.register(Registry.FEATURE, new ResourceLocation(BumblezoneCommon.MODID, "web_wall"), WEB_WALL);
    }
}
