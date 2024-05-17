import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, throwError } from 'rxjs';
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
    return this.http
      .get<MovieResponse>(`${API_URL}/public/movie/top-100-movies`)
      .pipe(catchError(this.handleError));
  }
  searchTop100Movies(title: string): Observable<MovieResponse> {
    return this.http
      .get<MovieResponse>(
        `${API_URL}/public/movie/top-100-movies/search/${title}`
      )
      .pipe(catchError(this.handleError));
  }

  filterGenreTopMovies(genre: string): Observable<MovieResponse> {
    return this.http
      .get<MovieResponse>(
        `${API_URL}/public/movie/top-100-movies/filter-genre/${genre}`
      )
      .pipe(catchError(this.handleError));
  }

  getMovieById(movieId: string): Observable<MovieDetails> {
    return this.http
      .get<MovieDetails>(`${API_URL}/public/movie/top-100-movies/${movieId}`)
      .pipe(catchError(this.handleError));
  }

  private handleError(error: any): Observable<never> {
    console.error('An error occurred:', error); // log to console instead
    return throwError(
      () => new Error('Something bad happened; please try again later.')
    );
  }
}
