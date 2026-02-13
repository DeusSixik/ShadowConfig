package net.shadowking21.shadowconfig.forge;

import net.minecraftforge.fml.common.Mod;
import net.shadowking21.shadowconfig.ShadowConfig;

@Mod(ShadowConfig.MOD_ID)
public final class ShadowConfigForge {

    public ShadowConfigForge() {
        ShadowConfig.initPlatform(new SCForgePlatformHook());
    }
}
