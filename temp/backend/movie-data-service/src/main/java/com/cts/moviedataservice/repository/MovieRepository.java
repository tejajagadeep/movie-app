package com.cts.moviedataservice.repository;

import com.cts.moviedataservice.dto.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends MongoRepository<Movie, Integer> {
}
