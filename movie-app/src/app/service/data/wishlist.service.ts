import {
  HttpClient,
  HttpErrorResponse,
  HttpParams,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Movie } from '../../model/Movie';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { API_URL } from '../../app.constants';
import { Wishlists } from '../../model/Wishlist';

@Injectable({
  providedIn: 'root',
})
export class WishlistService {
  constructor(private http: HttpClient) {}

  getWishlist(username: string): Observable<Wishlists> {
    return this.http
      .get<Wishlists>(`${API_URL}/private/wishlist/${username}`)
      .pipe(catchError(this.handleError));
  }

  saveWishlist(username: string, movie: Movie): Observable<Wishlists> {
    return this.http
      .post<Wishlists>(`${API_URL}/private/wishlist/${username}`, movie)
      .pipe(catchError(this.handleError));
  }

  deleteWishlist(username: string, movieId: string): Observable<Wishlists> {
    let params = new HttpParams().set('username', username).set('id', movieId);

    return this.http
      .delete<Wishlists>(`${API_URL}/private/wishlist`, {
        params,
      })
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
