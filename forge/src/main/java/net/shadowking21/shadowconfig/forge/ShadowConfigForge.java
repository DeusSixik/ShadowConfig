package net.shadowking21.shadowconfig.forge;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;
import net.shadowking21.shadowconfig.ShadowConfig;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.shadowking21.shadowconfig.config.ConfigSide;

@Mod(ShadowConfig.MOD_ID)
public final class ShadowConfigForge {

    public ShadowConfigForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(ShadowConfig.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        // Run our common setup.
        ShadowConfig.init(FMLPaths.CONFIGDIR.get(), isClient() ? ConfigSide.CLIENT : ConfigSide.SERVER );
    }

    public static boolean isClient()
    {
        return FMLEnvironment.dist == Dist.CLIENT;
    }

}
