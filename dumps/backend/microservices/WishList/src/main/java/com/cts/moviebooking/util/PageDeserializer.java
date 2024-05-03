package com.cts.moviebooking.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PageDeserializer<T> extends StdDeserializer<Page<T>> {

    private final Class<T> elementType;

    public PageDeserializer(Class<T> elementType) {
        super(Page.class);
        this.elementType = elementType;
    }

    @Override
    public Page<T> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        JsonNode contentNode = node.get("content");

        List<T> content = new ArrayList<>();
        if (contentNode.isArray()) {
            for (JsonNode elementNode : contentNode) {
                // Deserialize content elements individually
                T element = jp.getCodec().treeToValue(elementNode, elementType);
                content.add(element);
            }
        }

        int number = node.get("number").asInt();
        int size = node.get("size").asInt();
        long totalElements = node.get("totalElements").asLong();
        int totalPages = node.get("totalPages").asInt();
        boolean first = node.get("first").asBoolean();
        boolean last = node.get("last").asBoolean();

        return new PageImpl<>(content, PageRequest.of(number, size), totalElements);
    }
}
