package net.shadowking21.shadowconfig.fabric;

import net.fabricmc.api.ModInitializer;
import net.shadowking21.shadowconfig.ShadowConfig;

public final class ShadowConfigFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ShadowConfig.initPlatform(new SCFabricPlatformHook());
    }

}
