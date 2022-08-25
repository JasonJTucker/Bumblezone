package com.telepathicgrunt.the_bumblezone.registration;

import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import com.telepathicgrunt.the_bumblezone.entities.mobs.BeeQueenEntity;
import com.telepathicgrunt.the_bumblezone.entities.mobs.BeehemothEntity;
import com.telepathicgrunt.the_bumblezone.entities.mobs.HoneySlimeEntity;
import com.telepathicgrunt.the_bumblezone.entities.nonliving.BeeStingerEntity;
import com.telepathicgrunt.the_bumblezone.entities.nonliving.HoneyCrystalShardEntity;
import com.telepathicgrunt.the_bumblezone.entities.nonliving.PollenPuffEntity;
import com.telepathicgrunt.the_bumblezone.entities.nonliving.ThrownStingerSpearEntity;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BzEntities {
    public static final RegistrationProvider<EntityType<?>> ENTITIES = RegistrationProvider.get(Registry.ENTITY_TYPE_REGISTRY, BumblezoneCommon.MODID);

    public static final RegistryObject<EntityType<HoneySlimeEntity>> HONEY_SLIME = ENTITIES.register("honey_slime", () -> EntityType.Builder.<HoneySlimeEntity>of(HoneySlimeEntity::new, MobCategory.CREATURE).sized(1F, 1F).clientTrackingRange(8).build("honey_slime"));
    public static final RegistryObject<EntityType<BeehemothEntity>> BEEHEMOTH = ENTITIES.register("beehemoth", () -> EntityType.Builder.of(BeehemothEntity::new, MobCategory.CREATURE).sized(1.2F, 1.2F).clientTrackingRange(16).build("beehemoth"));
    public static final RegistryObject<EntityType<BeeQueenEntity>> BEE_QUEEN = ENTITIES.register("bee_queen", () -> EntityType.Builder.of(BeeQueenEntity::new, MobCategory.CREATURE).sized(2.9F, 2.9F).clientTrackingRange(16).build("bee_queen"));
    public static final RegistryObject<EntityType<PollenPuffEntity>> POLLEN_PUFF_ENTITY = ENTITIES.register("pollen_puff", () -> EntityType.Builder.<PollenPuffEntity>of(PollenPuffEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10).build("pollen_puff"));
    public static final RegistryObject<EntityType<ThrownStingerSpearEntity>> THROWN_STINGER_SPEAR_ENTITY = ENTITIES.register("thrown_stinger_spear", () -> EntityType.Builder.<ThrownStingerSpearEntity>of(ThrownStingerSpearEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("thrown_stinger_spear"));
    public static final RegistryObject<EntityType<BeeStingerEntity>> BEE_STINGER_ENTITY = ENTITIES.register("bee_stinger", () -> EntityType.Builder.<BeeStingerEntity>of(BeeStingerEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("bee_stinger"));
    public static final RegistryObject<EntityType<HoneyCrystalShardEntity>> HONEY_CRYSTAL_SHARD = ENTITIES.register("honey_crystal_shard", () -> EntityType.Builder.<HoneyCrystalShardEntity>of(HoneyCrystalShardEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("honey_crystal_shard"));

    public static void registerAdditionalEntityInformation() {
        registerEntitySpawnRestrictions();
    }

    private static void registerEntitySpawnRestrictions() {
        SpawnPlacements.register(HONEY_SLIME.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules);
        SpawnPlacements.register(BEEHEMOTH.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BeehemothEntity::checkMobSpawnRules);
        SpawnPlacements.register(BEE_QUEEN.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BeeQueenEntity::checkMobSpawnRules);
    }

    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(HONEY_SLIME.get(), HoneySlimeEntity.getAttributeBuilder().build());
        event.put(BEEHEMOTH.get(), BeehemothEntity.getAttributeBuilder().build());
        event.put(BEE_QUEEN.get(), BeeQueenEntity.getAttributeBuilder().build());
    }
}
