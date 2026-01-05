package net.shadowking21.shadowconfig.utils;

import net.shadowking21.shadowconfig.annotation.ConfigComment;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.nodes.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlHandler {
    public static Node buildYamlNode(Object value, Class<?> beanClass) {
        if (value == null) {
            return new ScalarNode(Tag.NULL, "null", null, null, DumperOptions.ScalarStyle.PLAIN);
        }

        if (value instanceof Map<?, ?> map) {
            return buildMappingNode(map, beanClass);
        }

        if (value instanceof List<?> list) {
            return buildSequenceNode(list, beanClass);
        }

        return new ScalarNode(Tag.STR, value.toString(), null, null, DumperOptions.ScalarStyle.PLAIN);
    }

    private static MappingNode buildMappingNode(Map<?, ?> map, Class<?> beanClass) {
        List<NodeTuple> children = new ArrayList<>();

        for (var entry : map.entrySet()) {
            String key = entry.getKey().toString();
            Object val = entry.getValue();

            // комментарий над полем
            String comment = extractComment(beanClass, key);
            if (comment != null) {
                children.add(commentNode(comment));
            }

            Node keyNode = new ScalarNode(Tag.STR, key, null, null, DumperOptions.ScalarStyle.PLAIN);
            Node valueNode = buildYamlNode(val, getFieldClass(beanClass, key));

            children.add(new NodeTuple(keyNode, valueNode));
        }

        return new MappingNode(Tag.MAP, children, DumperOptions.FlowStyle.BLOCK);
    }

    private static SequenceNode buildSequenceNode(List<?> list, Class<?> beanClass) {
        List<Node> nodes = new ArrayList<>();

        for (Object item : list) {
            nodes.add(buildYamlNode(item, beanClass));
        }

        return new SequenceNode(Tag.SEQ, nodes, DumperOptions.FlowStyle.BLOCK);
    }

    private static NodeTuple commentNode(String text) {
        ScalarNode comment = new ScalarNode(
                Tag.COMMENT,
                text,
                null,
                null,
                DumperOptions.ScalarStyle.PLAIN
        );

        return new NodeTuple(comment, null);
    }

    private static String extractComment(Class<?> beanClass, String fieldName) {
        try {
            Field f = beanClass.getDeclaredField(fieldName);
            ConfigComment c = f.getAnnotation(ConfigComment.class);
            return c != null ? c.value() : null;
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    private static Class<?> getFieldClass(Class<?> beanClass, String fieldName) {
        try {
            return beanClass.getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            return Object.class;
        }
    }
}
