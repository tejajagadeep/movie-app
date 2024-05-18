import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { WishlistService } from './wishlist.service';
import { API_URL } from '../../app.constants';
import { Movie } from '../../model/Movie';
import { Wishlists } from '../../model/Wishlist';

describe('WishlistService', () => {
  let service: WishlistService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [WishlistService],
    });
    service = TestBed.inject(WishlistService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch wishlist', () => {
    const dummyUsername = 'testUser';
    const dummyWishlist: Wishlists = { username: dummyUsername, movies: [] };

    service.getWishlist(dummyUsername).subscribe((wishlist) => {
      expect(wishlist).toEqual(dummyWishlist);
    });

    const req = httpMock.expectOne(
      `${API_URL}/private/wishlist/${dummyUsername}`
    );
    expect(req.request.method).toBe('GET');
    req.flush(dummyWishlist);
  });

  it('should save wishlist', () => {
    const dummyUsername = 'testUser';
    const dummyMovie: Movie = {
      id: '123',
      title: 'Dummy Movie',
      description: 'helo',
      image: 'https:dumt',
      imdb_link: 'jelo',
      imdbid: 'kelo',
      rank: 2,
      rating: 5.6,
      thumbnail: 'jd',
      year: 1230,
      genre: [],
    };
    const dummyWishlist: Wishlists = {
      username: dummyUsername,
      movies: [dummyMovie],
    };

    service.saveWishlist(dummyUsername, dummyMovie).subscribe((wishlist) => {
      expect(wishlist).toEqual(dummyWishlist);
    });

    const req = httpMock.expectOne(
      `${API_URL}/private/wishlist/${dummyUsername}`
    );
    expect(req.request.method).toBe('POST');
    req.flush(dummyWishlist);
  });

  it('should delete movie from wishlist', () => {
    const dummyUsername = 'testUser';
    const dummyMovieId = '123';
    const dummyWishlist: Wishlists = { username: dummyUsername, movies: [] };

    service
      .deleteWishlist(dummyUsername, dummyMovieId)
      .subscribe((wishlist) => {
        expect(wishlist).toEqual(dummyWishlist);
      });

    const req = httpMock.expectOne(
      `${API_URL}/private/wishlist?username=${dummyUsername}&id=${dummyMovieId}`
    );
    expect(req.request.method).toBe('DELETE');
    req.flush(dummyWishlist);
  });

  it('should handle errors', () => {
    const dummyUsername = 'testUser';
    const dummyError = { status: 404, statusText: 'Not Found' };

    service.getWishlist(dummyUsername).subscribe(
      () => fail('expected an error, but did not get one'),
      (error) => {
        expect(error.status).toEqual(dummyError.status);
        expect(error.message).toEqual(dummyError.statusText);
      }
    );

    const req = httpMock.expectOne(
      `${API_URL}/private/wishlist/${dummyUsername}`
    );
    req.flush('Invalid request', { status: 404, statusText: 'Not Found' });
  });
});
