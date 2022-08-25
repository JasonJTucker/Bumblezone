package com.telepathicgrunt.the_bumblezone.registration;

import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import com.telepathicgrunt.the_bumblezone.items.functions.DropContainerItems;
import com.telepathicgrunt.the_bumblezone.items.functions.HoneyCompassLocateStructure;
import com.telepathicgrunt.the_bumblezone.items.functions.UniquifyIfHasItems;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraftforge.registries.DeferredRegister;

public class BzLootFunctionTypes {

    public static final RegistrationProvider<LootItemFunctionType> LOOT_ITEM_FUNCTION_TYPE = RegistrationProvider.get(Registry.LOOT_FUNCTION_REGISTRY, BumblezoneCommon.MODID);

    public static final RegistryObject<LootItemFunctionType> DROP_CONTAINER_ITEMS = LOOT_ITEM_FUNCTION_TYPE.register("drop_container_loot", () -> new LootItemFunctionType(new DropContainerItems.Serializer()));
    public static final RegistryObject<LootItemFunctionType> UNIQUIFY_IF_HAS_ITEMS = LOOT_ITEM_FUNCTION_TYPE.register("uniquify_if_has_items", () -> new LootItemFunctionType(new UniquifyIfHasItems.Serializer()));
    public static final RegistryObject<LootItemFunctionType> HONEY_COMPASS_LOCATE_STRUCTURE = LOOT_ITEM_FUNCTION_TYPE.register("honey_compass_locate_structure", () -> new LootItemFunctionType(new HoneyCompassLocateStructure.Serializer()));
}
