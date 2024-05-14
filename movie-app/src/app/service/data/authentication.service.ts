import { HttpBackend, HttpClient, HttpHandler } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthenticationRequest } from '../../model/AuthenticationRequest';
import { Observable } from 'rxjs';
import { AuthenticationResponse } from '../../model/AuthenticationResponse';
import { API_URL } from '../../app.constants';

@Injectable({
  providedIn: 'root',
})
export class AuthenticationService {
  private httpHandler: HttpClient;

  constructor(private http: HttpClient, private handler: HttpBackend) {
    this.httpHandler = new HttpClient(handler);
  }

  authenticate(
    auth: AuthenticationRequest
  ): Observable<AuthenticationResponse> {
    return this.httpHandler.post<AuthenticationResponse>(
      `${API_URL}/public/auth/authenticate`,
      auth
    );
  }

  isUserLoggedIn(username: string): Observable<boolean> {
    return this.http.get<boolean>(
      `${API_URL}/public/auth/validate?username=${username}`
    );
  }
}
