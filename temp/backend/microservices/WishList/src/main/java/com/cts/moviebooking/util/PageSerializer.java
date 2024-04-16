package com.cts.moviebooking.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.springframework.data.domain.Page;

import java.io.IOException;

public class PageSerializer extends StdSerializer<Page<?>> {

    public PageSerializer() {
        this(null);
    }

    public PageSerializer(Class<Page<?>> t) {
        super(t);
    }

    @Override
    public void serialize(Page<?> page, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();

        // Serialize the content of the page
        gen.writeObjectField("content", page.getContent());

        // Serialize other page details
        gen.writeNumberField("totalElements", page.getTotalElements());
        gen.writeNumberField("totalPages", page.getTotalPages());
        gen.writeNumberField("number", page.getNumber());
        gen.writeNumberField("size", page.getSize());
        gen.writeBooleanField("first", page.isFirst());
        gen.writeBooleanField("last", page.isLast());

        gen.writeEndObject();
    }
}
