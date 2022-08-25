package com.telepathicgrunt.the_bumblezone.registration;

import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import com.telepathicgrunt.the_bumblezone.enchantments.CombCutterEnchantment;
import com.telepathicgrunt.the_bumblezone.enchantments.NeurotoxinsEnchantment;
import com.telepathicgrunt.the_bumblezone.enchantments.PotentPoisonEnchantment;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BzEnchantments {
    public static final RegistrationProvider<Enchantment> ENCHANTMENTS = RegistrationProvider.get(Registry.ENCHANTMENT_REGISTRY, BumblezoneCommon.MODID);

    public static final RegistryObject<CombCutterEnchantment> COMB_CUTTER = ENCHANTMENTS.register("comb_cutter", CombCutterEnchantment::new);
    public static final RegistryObject<PotentPoisonEnchantment> POTENT_POISON = ENCHANTMENTS.register("potent_poison", PotentPoisonEnchantment::new);
    public static final RegistryObject<NeurotoxinsEnchantment> NEUROTOXINS = ENCHANTMENTS.register("neurotoxins", NeurotoxinsEnchantment::new);
}
