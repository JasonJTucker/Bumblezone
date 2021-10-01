package com.telepathicgrunt.bumblezone.mixin.entities;

import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BeeEntity.class)
public interface BeeEntityInvoker {

    @Invoker("setHasNectar")
    void thebumblezone_callSetHasNectar(boolean value);
}
