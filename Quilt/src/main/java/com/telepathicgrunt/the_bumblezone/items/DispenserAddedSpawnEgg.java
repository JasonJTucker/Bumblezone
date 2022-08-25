package com.telepathicgrunt.the_bumblezone.items;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

import java.util.function.Supplier;

public class DispenserAddedSpawnEgg extends SpawnEggItem implements BzSpawnEgg{
    public DispenserAddedSpawnEgg(Supplier<? extends EntityType<? extends Mob>> typeIn, int primaryColorIn, int secondaryColorIn, Item.Properties builder) {
        super(typeIn.get(), primaryColorIn, secondaryColorIn, builder);
    }
}