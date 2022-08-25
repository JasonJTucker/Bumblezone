package com.telepathicgrunt.the_bumblezone.platform;

import com.telepathicgrunt.the_bumblezone.platform.services.IPlatformHelper;
import org.quiltmc.loader.api.QuiltLoader;

public class QuiltPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Quilt";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return QuiltLoader.isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return QuiltLoader.isDevelopmentEnvironment();
    }
}