package net.shadowking21.shadowconfig;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import net.shadowking21.shadowconfig.config.ConfigSide;
import net.shadowking21.shadowconfig.config.exstensions.json.example.SCJsonTestConfig;
import net.shadowking21.shadowconfig.config.exstensions.jsonc.SCJsoncConfig;
import net.shadowking21.shadowconfig.config.exstensions.jsonc.example.SCJsoncTestConfig;

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
        SCJsoncTestConfig.init();
    }

    public static Path getDefaultConfigPath() {
        return GAME_DIR;
    }

    public static ObjectMapper getDefaultJsonMapper() {
        return new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT)
                .enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
    }

    public static ObjectMapper getDefaultJsoncMapper() {
        var factory = JsonFactory.builder().enable(JsonReadFeature.ALLOW_JAVA_COMMENTS).build();
        return new ObjectMapper(factory)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .enable(JsonGenerator.Feature.IGNORE_UNKNOWN);
    }

    public static ConfigSide getCurrentGameSide() {
        return currentSide;
    }

}
