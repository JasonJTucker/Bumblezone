package com.telepathicgrunt.the_bumblezone.registration;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import com.telepathicgrunt.the_bumblezone.world.surfacerules.PollinatedSurfaceSource;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraftforge.registries.DeferredRegister;

public class BzSurfaceRules {

    public static final RegistrationProvider<Codec<? extends SurfaceRules.RuleSource>> SURFACE_RULES = RegistrationProvider.get(Registry.RULE_REGISTRY, BumblezoneCommon.MODID);

    public static final RegistryObject<Codec<? extends SurfaceRules.RuleSource>> POLLINATED_SURFACE_SOURCE = SURFACE_RULES.register("pollinated_surface_source", PollinatedSurfaceSource.CODEC::codec);
}
