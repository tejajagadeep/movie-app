import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Movie } from '../../model/Movie';
import { Observable } from 'rxjs';
import { API_URL } from '../../app.constants';
import { Wishlists } from '../../model/Wishlist';

@Injectable({
  providedIn: 'root',
})
export class WishlistService {
  constructor(private http: HttpClient) {}

  getWishlist(username: string): Observable<Wishlists> {
    return this.http.get<Wishlists>(`${API_URL}/private/wishlist/${username}`);
  }

  saveWishlist(username: string, movie: Movie): Observable<Wishlists> {
    return this.http.post<Wishlists>(
      `${API_URL}/private/wishlist/${username}`,
      movie
    );
  }

  deleteWishlist(username: string, movieId: string): Observable<Wishlists> {
    let params = new HttpParams().set('username', username).set('id', movieId);

    return this.http.delete<Wishlists>(`${API_URL}/private/wishlist`, {
      params,
    });
  }
}
