package com.telepathicgrunt.the_bumblezone.registration;

import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.StatFormatter;
import net.minecraft.stats.Stats;
import net.minecraftforge.registries.DeferredRegister;

public class BzStats {
    public static final RegistrationProvider<ResourceLocation> CUSTOM_STAT = RegistrationProvider.get(Registry.CUSTOM_STAT_REGISTRY, BumblezoneCommon.MODID);

    public static final RegistryObject<ResourceLocation> CARPENTER_BEE_BOOTS_MINED_BLOCKS_RL = CUSTOM_STAT.register("carpenter_bee_boots_mined_blocks", () -> makeRL("carpenter_bee_boots_mined_blocks"));
    public static final RegistryObject<ResourceLocation> CARPENTER_BEE_BOOTS_WALL_HANG_TIME_RL = CUSTOM_STAT.register("carpenter_bee_boots_wall_hang_time", () -> makeRL("carpenter_bee_boots_wall_hang_time"));
    public static final RegistryObject<ResourceLocation> STINGLESS_BEE_HELMET_BEE_RIDER_RL = CUSTOM_STAT.register("stingless_bee_helmet_bee_rider", () -> makeRL("stingless_bee_helmet_bee_rider"));
    public static final RegistryObject<ResourceLocation> BUMBLE_BEE_CHESTPLATE_FLY_TIME_RL = CUSTOM_STAT.register("bumble_bee_chestplate_fly_time", () -> makeRL("bumble_bee_chestplate_fly_time"));
    public static final RegistryObject<ResourceLocation> HONEY_BEE_LEGGINGS_FLOWER_POLLEN_RL = CUSTOM_STAT.register("honey_bee_leggings_flower_pollen", () -> makeRL("honey_bee_leggings_flower_pollen"));

    private static ResourceLocation makeRL(String key) {
        return new ResourceLocation(BumblezoneCommon.MODID, key);
    }

    public static void initStatEntries() {
        Stats.CUSTOM.get(CARPENTER_BEE_BOOTS_MINED_BLOCKS_RL.get(), StatFormatter.DEFAULT);
        Stats.CUSTOM.get(CARPENTER_BEE_BOOTS_WALL_HANG_TIME_RL.get(), StatFormatter.DEFAULT);
        Stats.CUSTOM.get(STINGLESS_BEE_HELMET_BEE_RIDER_RL.get(), StatFormatter.DEFAULT);
        Stats.CUSTOM.get(BUMBLE_BEE_CHESTPLATE_FLY_TIME_RL.get(), StatFormatter.DEFAULT);
        Stats.CUSTOM.get(HONEY_BEE_LEGGINGS_FLOWER_POLLEN_RL.get(), StatFormatter.DEFAULT);
    }
}
