package com.telepathicgrunt.the_bumblezone.events;

import com.telepathicgrunt.the_bumblezone.advancements.TargetAdvancementDoneTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.AdvancementEvent;

public class Advancements {
    public static void OnAdvancementGiven(AdvancementEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            TargetAdvancementDoneTrigger.OnAdvancementGiven(serverPlayer, event.getAdvancement());
        }
    }
}
