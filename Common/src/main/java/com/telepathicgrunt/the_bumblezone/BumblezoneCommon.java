package com.telepathicgrunt.the_bumblezone;

import com.telepathicgrunt.the_bumblezone.registration.BzTags;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BumblezoneCommon {
    public static final String MODID = "the_bumblezone";
    public static final ResourceLocation MOD_DIMENSION_ID = new ResourceLocation(BumblezoneCommon.MODID, BumblezoneCommon.MODID);
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static void init() {
        BzTags.initTags();
    }
}