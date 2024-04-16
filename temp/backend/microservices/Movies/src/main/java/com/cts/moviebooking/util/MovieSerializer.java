package com.cts.moviebooking.util;

import com.cts.moviebooking.dto.Movie;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.util.List;

public class MovieSerializer extends JsonSerializer<Movie> {

    @Override
    public void serialize(Movie movie, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", movie.getId());
        jsonGenerator.writeNumberField("rank", movie.getRank());
        jsonGenerator.writeStringField("title", movie.getTitle());
        jsonGenerator.writeStringField("description", movie.getDescription());
        jsonGenerator.writeStringField("image", movie.getImage());
        jsonGenerator.writeStringField("bigImage", movie.getBig_image());
        jsonGenerator.writeStringField("thumbnail", movie.getThumbnail());
        //jsonGenerator.writeStringField("rating", movie.getRating());

        // Serialize genre as an array
        jsonGenerator.writeFieldName("genre");
        if (movie.getGenre() != null) {
            jsonGenerator.writeStartArray();
            for (String genre : movie.getGenre()) {
                jsonGenerator.writeString(genre);
            }
            jsonGenerator.writeEndArray();
        } else {
            jsonGenerator.writeNull();
        }

        jsonGenerator.writeNumberField("year", movie.getYear());
        jsonGenerator.writeStringField("imdbid", movie.getImdbid());
        jsonGenerator.writeStringField("imdb_link", movie.getImdb_link());

        jsonGenerator.writeEndObject();
    }
}
