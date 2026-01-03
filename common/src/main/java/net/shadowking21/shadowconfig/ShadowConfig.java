package net.shadowking21.shadowconfig;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.shadowking21.shadowconfig.config.ConfigSide;
import net.shadowking21.shadowconfig.config.json.SCJsonTestConfig;

import java.nio.file.Path;
import java.util.logging.Logger;

public final class ShadowConfig {

    public static final String MOD_ID = "shadowconfig";

    public static final Logger LOGGER = Logger.getLogger("ShadowConfig");

    private static Path GAME_DIR;

    private static ConfigSide currentSide;

    public static void init(Path path, ConfigSide side) {
        GAME_DIR = path;
        currentSide = side;
        SCJsonTestConfig.init();
    }

    public static Path getDefaultConfigPath() {
        return GAME_DIR;
    }

    public static ObjectMapper getDefaultJsonMapper()
    {
        return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
    }

    public static ConfigSide getCurrentGameSide()
    {
        return currentSide;
    }

}
