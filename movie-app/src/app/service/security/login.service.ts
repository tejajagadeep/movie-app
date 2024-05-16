import { Injectable } from '@angular/core';
import { AuthenticationService } from '../data/authentication.service';
import { Observable } from 'rxjs/internal/Observable';
import { API_URL } from '../../app.constants';
import { map } from 'rxjs/internal/operators/map';
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/internal/operators/catchError';
import { of } from 'rxjs/internal/observable/of';

@Injectable({
  providedIn: 'root',
})
export class LoginService {
  isLoggedIn = false;

  constructor(
    private authService: AuthenticationService,
    private http: HttpClient
  ) {}

  isLogged() {
    this.isLoggedIn = true;
    let username = sessionStorage.getItem('username') ?? '';
    this.authService.isUserLoggedIn(username).pipe(
      map((response: boolean) => {
        return response; // Return true if authentication is successful, false otherwise
      })
    );
  }
  isUserLoggedIn(username: string): Observable<boolean> {
    return this.http
      .get<boolean>(`${API_URL}/public/auth/validate?username=${username}`)
      .pipe(
        catchError((error) => {
          // Handle error, e.g., if HTTP request fails
          console.error('Error validating user:', error);
          return of(false); // Return false to indicate validation failure
        })
      );
  }
}
