package com.cts.moviebooking.util;

import com.cts.moviebooking.dto.MovieDto;

import java.util.List;
import java.util.stream.Collectors;

public class MovieDtoUtils {

    public static List<MovieDto> removeDuplicatesByTitle(List<MovieDto> movies) {
        return movies.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
