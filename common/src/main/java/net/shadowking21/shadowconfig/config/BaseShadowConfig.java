package net.shadowking21.shadowconfig.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.shadowking21.shadowconfig.ShadowConfig;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseShadowConfig<T> implements IConfigWriter<T>, IConfigReader<T> {

    protected final Path PATH;

    protected final Path FILE_PATH;

    protected ObjectMapper objectMapper;

    protected Class<T> configClass;

    protected T defaultConfig;

    protected T currentConfig;

    protected ConfigSide configSide;

    protected String modId;

    protected BaseShadowConfig(String modId, T defaults, Class<T> clazz, ConfigSide configSide, ObjectMapper mapper) {
        this(modId, ShadowConfig.getDefaultConfigPath(), defaults, clazz, configSide, mapper);
    }

    protected BaseShadowConfig(String modId, Path path, T defaults, Class<T> clazz, ConfigSide configSide, ObjectMapper mapper)
    {
        this.modId = modId;
        this.PATH = path;
        this.configSide = configSide;
        defaultConfig = defaults;
        objectMapper = mapper;
        configClass = clazz;
        FILE_PATH = Paths.get(path.toString(), getConfigName());
    }

    public String getConfigName() {
        String sideName = switch (configSide) {
            case CLIENT -> "client";
            case SERVER -> "server";
            default -> "common";
        };
        return modId + "-" + sideName + getExtension();
    }

    protected void init() {
        if (!isConfigAvailable())
            return;

        createPathsIfNotExists();
        if (!isExists())
            write(defaultConfig);
        currentConfig = read();
    }

    @Override
    public T read()
    {
        configAllowThrow();

        T value;
        try {
            value = objectMapper.readValue(FILE_PATH.toFile(), configClass); // need change this cast
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return value;
    }

    @Override
    public void write(T value)
    {
        configAllowThrow();

        try {
            objectMapper.writeValue(FILE_PATH.toFile(), defaultConfig);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public T getDefaultConfig()
    {
        configAllowThrow();
        return defaultConfig;
    }

    public T getCurrentConfig()
    {
        configAllowThrow();
        return currentConfig;
    }

    protected void createIfNotExists()
    {
        try {
            if (!FILE_PATH.toFile().exists()) {
                Files.createFile(FILE_PATH);
            }
        }
        catch (IOException e)
        {
            ShadowConfig.LOGGER.config(e.getLocalizedMessage());
        }
    }

    protected void createPathsIfNotExists()
    {
        try {
            if (!Files.exists(PATH)) {
                ShadowConfig.LOGGER.config("Directories with path " + PATH.toFile().getAbsolutePath() + " not found");
                Files.createDirectories(PATH);
            }
        }
        catch (Exception e) {
            ShadowConfig.LOGGER.config(e.getLocalizedMessage());
        }
    }

    protected boolean isExists()
    {
        return FILE_PATH.toFile().exists();
    }

    protected boolean isConfigAvailable()
    {
        return configSide == ShadowConfig.getCurrentGameSide() || configSide == ConfigSide.COMMON;
    }

    protected void configAllowThrow()
    {
        if (isConfigAvailable())
            return;

        throw new IllegalStateException("Attempt to access " + configSide + " config on the " + ShadowConfig.getCurrentGameSide() + " side");
    }

    protected abstract String getExtension();
}
