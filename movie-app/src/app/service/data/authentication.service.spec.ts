import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { AuthenticationService } from './authentication.service';
import { API_URL } from '../../app.constants';
import { AuthenticationRequest } from '../../model/AuthenticationRequest';
import { AuthenticationResponse } from '../../model/AuthenticationResponse';

describe('AuthenticationService', () => {
  let service: AuthenticationService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [AuthenticationService],
    });
    service = TestBed.inject(AuthenticationService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should authenticate user', () => {
    const dummyAuthRequest: AuthenticationRequest = {
      username: 'testUser',
      password: 'password',
    };
    const dummyResponse: AuthenticationResponse = {
      access_token: 'dummyToken',
    };

    service.authenticate(dummyAuthRequest).subscribe((response) => {
      expect(response).toEqual(dummyResponse);
    });

    const req = httpMock.expectOne(`${API_URL}/public/auth/authenticate`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(dummyAuthRequest);
    req.flush(dummyResponse);
  });

  it('should validate user login status', () => {
    const dummyUsername = 'testUser';
    const dummyResponse = true;

    service.isUserLoggedIn(dummyUsername).subscribe((response) => {
      expect(response).toEqual(dummyResponse);
    });

    const req = httpMock.expectOne(
      `${API_URL}/public/auth/validate?username=${dummyUsername}`
    );
    expect(req.request.method).toBe('GET');
    req.flush(dummyResponse);
  });

  it('should handle errors', () => {
    const dummyAuthRequest: AuthenticationRequest = {
      username: 'testUser',
      password: 'password',
    };
    const dummyError = { status: 404, statusText: 'Not Found' };

    service.authenticate(dummyAuthRequest).subscribe(
      () => fail('expected an error, but did not get one'),
      (error) => {
        expect(error.status).toEqual(dummyError.status);
        expect(error.message).toEqual(dummyError.statusText);
      }
    );

    const req = httpMock.expectOne(`${API_URL}/public/auth/authenticate`);
    req.flush('Invalid request', { status: 404, statusText: 'Not Found' });
  });
});
