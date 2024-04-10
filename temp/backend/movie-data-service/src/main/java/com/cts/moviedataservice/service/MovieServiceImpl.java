package com.cts.moviedataservice.service;

import com.cts.moviedataservice.dto.Movie;
import com.cts.moviedataservice.dto.MovieDetails;
import com.cts.moviedataservice.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MovieServiceImpl implements MovieService{

    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    @Override
    public Object topMovies(){
        return movieRepository.findAll();
    }

    @Override
    public Object topMoviesById(String id) {

        MovieDetails movieDetails = new MovieDetails();
        movieDetails.setRank(32);
        movieDetails.setTitle("Oppenheimer");
        movieDetails.setRating(8.6F);
        movieDetails.setId("top32");
        movieDetails.setYear(2023);
        movieDetails.setBigImage("https://m.media-amazon.com/images/M/MV5BMDBmYTZjNjUtN2M1MS00MTQ2LTk2ODgtNzc2M2QyZGE5NTVjXkEyXkFqcGdeQXVyNzAwMjU2MTY@._V1_QL75_UX380_CR0,0,380,562_.jpg");
        movieDetails.setDescription("The story of American scientist, J. Robert Oppenheimer, and his role in the development of the atomic bomb.");
        movieDetails.setTrailerEmbedLink("https://www.youtube.com/embed/uYPbbksJxIg");
        movieDetails.setGenre(List.of(new String[]{"Biography", "Drama", "History"}));
        movieDetails.setDirector(List.of(new String[]{"Christopher Nolan"}));
        movieDetails.setWriters(List.of(new String[]{"Christopher Nolan", "Kai Bird", "Martin Sherwin"}));
        movieDetails.setImdbLink("https://www.imdb.com/title/tt15398776");
        return movieDetails;
    }

    @Override
    public Object saveAll(List<Movie> movies) {
        return movieRepository.saveAll(movies);
    }

}
