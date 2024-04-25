import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationRequest } from '../../model/AuthenticationRequest';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '../../model/AuthenticationResponse';
import { API_URL } from '../../app.constants';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  constructor(private http: HttpClient) {}

  authenticate(
    auth: AuthenticationRequest
  ): Observable<AuthenticationResponse> {
    return this.http.post<AuthenticationResponse>(
      `${API_URL}/public/auth/authenticate`,
      { auth }
    );
  }

  isUserLoggedIn(username: string): Observable<String> {
    return this.http.get<String>(
      `${API_URL}/public/auth/authenticate/${username}`
    );
  }
}
