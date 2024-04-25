import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { UserProfile } from '../../model/UserProfile';
import { Observable } from 'rxjs';
import { API_URL } from '../../app.constants';

@Injectable({
  providedIn: 'root',
})
export class UserprofileService {
  constructor(private http: HttpClient) {}

  getUserProfile(username: string): Observable<UserProfile> {
    return this.http.get<UserProfile>(
      `${API_URL}/private/userProfile/getUserById/${username}`
    );
  }

  saveUserProfile(userProfile: UserProfile): Observable<UserProfile> {
    return this.http.post<UserProfile>(
      `${API_URL}/public/userProfile/addUser`,
      userProfile
    );
  }

  updateUserProfile(
    username: string,
    userProfile: UserProfile
  ): Observable<UserProfile> {
    return this.http.put<UserProfile>(
      `${API_URL}/private/userProfile/update/${username}`,
      userProfile
    );
  }
}
