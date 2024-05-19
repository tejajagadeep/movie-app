import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, shareReplay, throwError } from 'rxjs';
import { Movie } from '../../model/Movie';
import { MovieDetails } from '../../model/MovieDetails';
import { API_URL } from '../../app.constants';
import { MovieResponse } from '../../model/MovieResponse';

@Injectable({
  providedIn: 'root',
})
export class MovieService {
  private top100MoviesCache!: Observable<MovieResponse>;
  constructor(private http: HttpClient) {}

  getTop100Movies(): Observable<MovieResponse> {
    if (!this.top100MoviesCache) {
      this.top100MoviesCache = this.http
        .get<MovieResponse>(`${API_URL}/public/movie/top-100-movies`)
        .pipe(
          catchError(this.handleError),
          shareReplay(1) // Cache the latest emitted value and replay it to new subscribers
        );
    }
    return this.top100MoviesCache;
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

  private handleError(error: HttpErrorResponse): Observable<never> {
    let errorMessage = 'Unknown error!';
    if (error.error instanceof ErrorEvent) {
      // Client-side or network error
      errorMessage = `Error: ${error.error.message}`;
    } else {
      // Backend error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    return throwError(() => ({
      status: error.status,
      message: error.statusText,
      error: error.error,
    }));
  }
}
