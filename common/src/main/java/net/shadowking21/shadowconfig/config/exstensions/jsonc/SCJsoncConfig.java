package net.shadowking21.shadowconfig.config.exstensions.jsonc;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.shadowking21.shadowconfig.ShadowConfig;
import net.shadowking21.shadowconfig.config.BaseShadowConfig;
import net.shadowking21.shadowconfig.config.ConfigSide;
import net.shadowking21.shadowconfig.config.builder.BaseConfigBuilder;
import net.shadowking21.shadowconfig.config.builder.stages.ClazzStage;
import net.shadowking21.shadowconfig.config.builder.stages.DefaultsStage;
import net.shadowking21.shadowconfig.config.builder.stages.ModIdStage;
import net.shadowking21.shadowconfig.config.builder.stages.OptionalStage;

import java.nio.file.Path;

public class SCJsoncConfig<T> extends BaseShadowConfig<T> {

    public SCJsoncConfig(String modId, Path path, T defaults, Class<T> clazz, ConfigSide configSide, ObjectMapper mapper) {
        super(modId, path, defaults, clazz, configSide, mapper);
    }

    public SCJsoncConfig(String modId, T defaults, Class<T> clazz, ConfigSide configSide, ObjectMapper mapper) {
        super(modId, defaults, clazz, configSide, mapper);
    }

    public SCJsoncConfig(String modId, T defaults, Class<T> clazz, ConfigSide configSide) {
        super(modId, defaults, clazz, configSide, ShadowConfig.getDefaultJsonMapper());
    }

    @Override
    protected String getExtension() {
        return ".jsonc";
    }

    public static class Builder<T> extends BaseConfigBuilder<T> implements ModIdStage<T>, ClazzStage<T>, DefaultsStage<T>, OptionalStage<T>
    {
        private Builder() {}

        public static <T> ModIdStage<T> builder(Class<T> clazz, T defaults) {
            var builder = new SCJsoncConfig.Builder<T>();
            builder.setDefaults(defaults);
            builder.setClass(clazz);
            return builder;
        }

        public static <T> DefaultsStage<T> builder(Class<T> clazz)
        {
            var builder = new SCJsoncConfig.Builder<T>();
            builder.setClass(clazz);
            return builder;
        }

        public static <T> ModIdStage<T> builder() {
            return new SCJsoncConfig.Builder<T>();
        }

        public static <T> ModIdStage<T> builder(T defaults) {
            var builder = new SCJsoncConfig.Builder<T>();
            builder.setDefaults(defaults);
            return builder;
        }

        @Override
        public OptionalStage<T> modId(String modId) {
            setModId(modId);
            return this;
        }

        @Override
        public DefaultsStage<T> clazz(Class<T> clazz) {
            setClass(clazz);
            return this;
        }

        @Override
        public OptionalStage<T> path(Path path) {
            setPath(path);
            return this;
        }

        @Override
        public OptionalStage<T> mapper(ObjectMapper mapper) {
            setMapper(mapper);
            return this;
        }

        @Override
        public OptionalStage<T> side(ConfigSide side) {
            setSide(side);
            return this;
        }

        @Override
        public BaseShadowConfig<T> build() {
            var config = new SCJsoncConfig<>(modId, path, defaults, clazz, side, mapper);
            config.init();
            return config;
        }

        @Override
        public ModIdStage<T> defaults(T defaults) {
            setDefaults(defaults);
            return this;
        }
    }
}
