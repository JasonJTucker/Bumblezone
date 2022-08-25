package com.telepathicgrunt.the_bumblezone.mixin.entities;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.server.PlayerAdvancements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(PlayerAdvancements.class)
public interface PlayerAdvancementsAccessor {
    @Accessor("advancements")
    Map<Advancement, AdvancementProgress> getAdvancements();
}
