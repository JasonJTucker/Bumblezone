package com.telepathicgrunt.the_bumblezone.registration;

import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import com.telepathicgrunt.the_bumblezone.world.predicates.PieceOriginAxisAlignedLinearPosRuleTest;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.templatesystem.PosRuleTestType;
import net.minecraftforge.registries.DeferredRegister;

public class BzPredicates {
    public static final RegistrationProvider<PosRuleTestType<?>> POS_RULE_TEST = RegistrationProvider.get(Registry.POS_RULE_TEST_REGISTRY, BumblezoneCommon.MODID);

    public static final RegistryObject<PosRuleTestType<PieceOriginAxisAlignedLinearPosRuleTest>> PIECE_ORIGIN_AXIS_ALIGNED_LINEAR_POS_RULE_TEST = POS_RULE_TEST.register("piece_origin_axis_aligned_linear_pos_rule_test", () -> () -> PieceOriginAxisAlignedLinearPosRuleTest.CODEC);
}
