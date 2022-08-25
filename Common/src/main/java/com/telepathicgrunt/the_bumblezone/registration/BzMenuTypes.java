package com.telepathicgrunt.the_bumblezone.registration;

import com.telepathicgrunt.the_bumblezone.BumblezoneCommon;
import com.telepathicgrunt.the_bumblezone.screens.StrictChestMenu;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class BzMenuTypes {
    public static final RegistrationProvider<MenuType<?>> MENUS = RegistrationProvider.get(Registry.MENU_REGISTRY, BumblezoneCommon.MODID);

    public static final RegistryObject<MenuType<StrictChestMenu>> STRICT_9x1 = MENUS.register("strict_9x1", () -> new MenuType<>(StrictChestMenu::oneRow));
    public static final RegistryObject<MenuType<StrictChestMenu>> STRICT_9x2 = MENUS.register("strict_9x2", () -> new MenuType<>(StrictChestMenu::twoRows));
    public static final RegistryObject<MenuType<StrictChestMenu>> STRICT_9x3 = MENUS.register("strict_9x3", () -> new MenuType<>(StrictChestMenu::threeRows));
    public static final RegistryObject<MenuType<StrictChestMenu>> STRICT_9x4 = MENUS.register("strict_9x4", () -> new MenuType<>(StrictChestMenu::fourRows));
    public static final RegistryObject<MenuType<StrictChestMenu>> STRICT_9x5 = MENUS.register("strict_9x5", () -> new MenuType<>(StrictChestMenu::fiveRows));
    public static final RegistryObject<MenuType<StrictChestMenu>> STRICT_9x6 = MENUS.register("strict_9x6", () -> new MenuType<>(StrictChestMenu::sixRows));
}
