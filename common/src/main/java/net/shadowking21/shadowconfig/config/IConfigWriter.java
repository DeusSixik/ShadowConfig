package net.shadowking21.shadowconfig.config;

public interface IConfigWriter<T> {
    void write(T config);
}
