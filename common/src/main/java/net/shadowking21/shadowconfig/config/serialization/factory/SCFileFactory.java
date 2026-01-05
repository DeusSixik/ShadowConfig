package net.shadowking21.shadowconfig.config.serialization.factory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.io.IOContext;
import net.shadowking21.shadowconfig.config.serialization.generators.CommentGenerator;

import java.io.IOException;
import java.io.Writer;

public class SCFileFactory extends JsonFactory {

    private final Object bean;

    private final String prefix;

    public SCFileFactory(Object bean, String prefix) {
        this.bean = bean;
        this.prefix = prefix;
    }

    @Override
    protected JsonGenerator _createGenerator(Writer out, IOContext ctxt) throws IOException {
        JsonGenerator gen = super._createGenerator(out, ctxt);
        return new CommentGenerator(gen, bean, prefix);
    }
}