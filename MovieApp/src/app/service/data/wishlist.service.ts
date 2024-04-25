import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Movie } from '../../model/Movie';
import { Observable } from 'rxjs';
import { API_URL } from '../../app.constants';

@Injectable({
  providedIn: 'root',
})
export class WishlistService {
  constructor(private http: HttpClient) {}

  getWishlist(username: string): Observable<Movie[]> {
    return this.http.get<Movie[]>(`${API_URL}/private/wishlist/${username}`);
  }

  saveWishlist(username: string, movie: Movie): Observable<Movie[]> {
    return this.http.post<Movie[]>(`${API_URL}/private/wishlist/${username}}`, {
      movie,
    });
  }

  deleteWishlist(username: string, movieId: string): Observable<Movie[]> {
    const params = { username, movieId };
    const options = { params };
    return this.http.delete<Movie[]>(`${API_URL}/private/wishlist`, options);
  }
}
