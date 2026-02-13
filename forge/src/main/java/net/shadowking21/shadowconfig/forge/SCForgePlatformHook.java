package net.shadowking21.shadowconfig.forge;

import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import net.shadowking21.shadowconfig.SCPlatformHook;
import net.shadowking21.shadowconfig.config.ConfigSide;

import java.nio.file.Path;

final class SCForgePlatformHook implements SCPlatformHook {
    @Override
    public ConfigSide getCurrentSide() {
        return FMLEnvironment.dist.isClient() ? ConfigSide.CLIENT : ConfigSide.SERVER;
    }

    @Override
    public Path getConfigPath() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public boolean isDeveloper() {
        return !FMLEnvironment.production;
    }
}
