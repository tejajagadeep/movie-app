import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Movie } from '../../model/Movie';
import { MovieDetails } from '../../model/MovieDetails';
import { API_URL } from '../../app.constants';
import { MovieResponse } from '../../model/MovieResponse';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  constructor(private http: HttpClient) {}

  getTop100Movies(): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(
      `${API_URL}/public/movie/top-100-movies`
    );
  }
  searchTop100Movies(title: string): Observable<MovieResponse> {
    return this.http.get<MovieResponse>(
      `${API_URL}/public/movie/top-100-movies/search/${title}`
    );
  }

  getMovieById(movieId: string): Observable<MovieDetails> {
    return this.http.get<MovieDetails>(
      `${API_URL}/public/movie/top-100-movies/${movieId}`
    );
  }
}
