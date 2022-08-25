package com.telepathicgrunt.the_bumblezone.modinit;

import com.telepathicgrunt.the_bumblezone.Bumblezone;
import com.telepathicgrunt.the_bumblezone.effects.BeenergizedEffect;
import com.telepathicgrunt.the_bumblezone.effects.HiddenEffect;
import com.telepathicgrunt.the_bumblezone.effects.ParalyzedEffect;
import com.telepathicgrunt.the_bumblezone.effects.ProtectionOfTheHiveEffect;
import com.telepathicgrunt.the_bumblezone.effects.WrathOfTheHiveEffect;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class BzEffects {
    public final static MobEffect WRATH_OF_THE_HIVE = new WrathOfTheHiveEffect(MobEffectCategory.HARMFUL, 16737285);
    public final static MobEffect PROTECTION_OF_THE_HIVE = new ProtectionOfTheHiveEffect(MobEffectCategory.BENEFICIAL, 15049988);
    public final static MobEffect BEENERGIZED = new BeenergizedEffect(MobEffectCategory.BENEFICIAL, 16768000).addAttributeModifier(Attributes.FLYING_SPEED, "9ed2fcd5-061e-4e25-a033-4306b824e941", 0.04D, AttributeModifier.Operation.MULTIPLY_BASE);
    public static final MobEffect HIDDEN = new HiddenEffect(MobEffectCategory.BENEFICIAL, 5308540);
    public static final MobEffect PARALYZED = new ParalyzedEffect(MobEffectCategory.HARMFUL, 15662848);

    public static void registerEffects() {
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation(BumblezoneCommon.MODID, "wrath_of_the_hive"), WRATH_OF_THE_HIVE);
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation(BumblezoneCommon.MODID, "protection_of_the_hive"), PROTECTION_OF_THE_HIVE);
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation(BumblezoneCommon.MODID, "beenergized"), BEENERGIZED);
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation(BumblezoneCommon.MODID, "hidden"), HIDDEN);
        Registry.register(Registry.MOB_EFFECT, new ResourceLocation(BumblezoneCommon.MODID, "paralyzed"), PARALYZED);
    }
}
