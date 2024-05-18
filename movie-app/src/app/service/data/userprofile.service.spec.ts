import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { UserprofileService } from './userprofile.service';
import { API_URL } from '../../app.constants';
import { UserProfile } from '../../model/UserProfile';

describe('UserprofileService', () => {
  let service: UserprofileService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [UserprofileService],
    });
    service = TestBed.inject(UserprofileService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch user profile', () => {
    const dummyUsername = 'testUser';
    const dummyProfile: UserProfile = {
      username: dummyUsername,
      email: 'test@test.com',
      dateOfBirth: new Date(),
      firstName: 'jhelo',
      lastName: 'eh',
      password: 'kek',
      phoneNumber: 78946123,
    };

    service.getUserProfile(dummyUsername).subscribe((profile) => {
      expect(profile).toEqual(dummyProfile);
    });

    const req = httpMock.expectOne(
      `${API_URL}/private/userProfile/getUserById/${dummyUsername}`
    );
    expect(req.request.method).toBe('GET');
    req.flush(dummyProfile);
  });

  it('should save user profile', () => {
    const dummyProfile: UserProfile = {
      username: 'dummyUsername',
      email: 'test@test.com',
      dateOfBirth: new Date(),
      firstName: 'jhelo',
      lastName: 'eh',
      password: 'kek',
      phoneNumber: 78946123,
    };

    service.saveUserProfile(dummyProfile).subscribe((profile) => {
      expect(profile).toEqual(dummyProfile);
    });

    const req = httpMock.expectOne(`${API_URL}/public/userProfile/addUser`);
    expect(req.request.method).toBe('POST');
    req.flush(dummyProfile);
  });

  it('should update user profile', () => {
    const dummyUsername = 'testUser';
    const dummyProfile: UserProfile = {
      username: dummyUsername,
      email: 'test@test.com',
      dateOfBirth: new Date(),
      firstName: 'jhelo',
      lastName: 'eh',
      password: 'kek',
      phoneNumber: 78946123,
    };

    service
      .updateUserProfile(dummyUsername, dummyProfile)
      .subscribe((profile) => {
        expect(profile).toEqual(dummyProfile);
      });

    const req = httpMock.expectOne(
      `${API_URL}/private/userProfile/update/${dummyUsername}`
    );
    expect(req.request.method).toBe('PUT');
    req.flush(dummyProfile);
  });

  it('should handle errors', () => {
    const dummyUsername = 'testUser';
    const dummyError = { status: 404, statusText: 'Not Found' };

    service.getUserProfile(dummyUsername).subscribe(
      () => fail('expected an error, but did not get one'),
      (error) => {
        expect(error.status).toEqual(dummyError.status);
        expect(error.message).toEqual(dummyError.statusText);
      }
    );

    const req = httpMock.expectOne(
      `${API_URL}/private/userProfile/getUserById/${dummyUsername}`
    );
    req.flush('Invalid request', { status: 404, statusText: 'Not Found' });
  });
});
