package com.telepathicgrunt.the_bumblezone.modinit;

import com.telepathicgrunt.the_bumblezone.Bumblezone;
import com.telepathicgrunt.the_bumblezone.items.functions.DropContainerItems;
import com.telepathicgrunt.the_bumblezone.items.functions.HoneyCompassLocateStructure;
import com.telepathicgrunt.the_bumblezone.items.functions.UniquifyIfHasItems;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class BzLootFunctionTypes {
    public static LootItemFunctionType DROP_CONTAINER_ITEMS;
    public static LootItemFunctionType UNIQUIFY_IF_HAS_ITEMS;
    public static LootItemFunctionType HONEY_COMPASS_LOCATE_STRUCTURE;

    public static void registerContainerLootFunctions() {
        DROP_CONTAINER_ITEMS = Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(Bumblezone.MODID, "drop_container_loot"), new LootItemFunctionType(new DropContainerItems.Serializer()));
        UNIQUIFY_IF_HAS_ITEMS = Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(Bumblezone.MODID, "uniquify_if_has_items"), new LootItemFunctionType(new UniquifyIfHasItems.Serializer()));
        HONEY_COMPASS_LOCATE_STRUCTURE = Registry.register(Registry.LOOT_FUNCTION_TYPE, new ResourceLocation(Bumblezone.MODID, "honey_compass_locate_structure"), new LootItemFunctionType(new HoneyCompassLocateStructure.Serializer()));
    }
}
