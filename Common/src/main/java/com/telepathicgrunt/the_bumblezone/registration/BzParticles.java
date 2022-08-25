package com.telepathicgrunt.the_bumblezone.registration;

import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BzParticles {
    public static final RegistrationProvider<ParticleType<?>> PARTICLE_TYPES = RegistrationProvider.get(Registry.PARTICLE_TYPE_REGISTRY, BumblezoneCommon.MODID);

    public static final RegistryObject<SimpleParticleType> POLLEN = PARTICLE_TYPES.register("pollen_puff", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> HONEY_PARTICLE = PARTICLE_TYPES.register("honey_particle", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> ROYAL_JELLY_PARTICLE = PARTICLE_TYPES.register("royal_jelly_particle", () -> new SimpleParticleType(false));
}
