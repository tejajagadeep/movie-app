import { TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { MovieService } from './movie.service';
import { API_URL } from '../../app.constants';
import { MovieResponse } from '../../model/MovieResponse';
import { MovieDetails } from '../../model/MovieDetails';

describe('MovieService', () => {
  let service: MovieService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MovieService],
    });
    service = TestBed.inject(MovieService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch top 100 movies', () => {
    const dummyResponse: MovieResponse = {
      data: [],
      message: '',
      status: true,
    };

    service.getTop100Movies().subscribe((response) => {
      expect(response).toEqual(dummyResponse);
    });

    const req = httpMock.expectOne(`${API_URL}/public/movie/top-100-movies`);
    expect(req.request.method).toBe('GET');
    req.flush(dummyResponse);
  });

  it('should search top 100 movies', () => {
    const dummyTitle = 'test';
    const dummyResponse: MovieResponse = {
      data: [],
      message: '',
      status: true,
    };

    service.searchTop100Movies(dummyTitle).subscribe((response) => {
      expect(response).toEqual(dummyResponse);
    });

    const req = httpMock.expectOne(
      `${API_URL}/public/movie/top-100-movies/search/${dummyTitle}`
    );
    expect(req.request.method).toBe('GET');
    req.flush(dummyResponse);
  });

  it('should filter top movies by genre', () => {
    const dummyGenre = 'action';
    const dummyResponse: MovieResponse = {
      data: [],
      message: '',
      status: true,
    };

    service.filterGenreTopMovies(dummyGenre).subscribe((response) => {
      expect(response).toEqual(dummyResponse);
    });

    const req = httpMock.expectOne(
      `${API_URL}/public/movie/top-100-movies/filter-genre/${dummyGenre}`
    );
    expect(req.request.method).toBe('GET');
    req.flush(dummyResponse);
  });

  it('should fetch movie details by ID', () => {
    const dummyMovieId = '123';
    const dummyResponse: MovieDetails = {
      id: dummyMovieId,
      title: 'Dummy Movie',
      genre: ['Action'],
      director: ['John Doe'],
      year: 2023,
      big_image: '',
      description: '',
      imdb_link: '',
      imdbid: '',
      rank: 4,
      rating: 4.5,
      trailer_embed_link: '',
      writers: [],
    };

    service.getMovieById(dummyMovieId).subscribe((response) => {
      expect(response).toEqual(dummyResponse);
    });

    const req = httpMock.expectOne(
      `${API_URL}/public/movie/top-100-movies/${dummyMovieId}`
    );
    expect(req.request.method).toBe('GET');
    req.flush(dummyResponse);
  });

  it('should handle errors', () => {
    const dummyMovieId = '123';
    const dummyError = { status: 404, statusText: 'Not Found' };

    service.getMovieById(dummyMovieId).subscribe(
      () => fail('expected an error, but did not get one'),
      (error) => {
        expect(error.status).toEqual(dummyError.status);
        expect(error.message).toEqual(dummyError.statusText);
      }
    );

    const req = httpMock.expectOne(
      `${API_URL}/public/movie/top-100-movies/${dummyMovieId}`
    );
    req.flush('Invalid request', { status: 404, statusText: 'Not Found' });
  });
});
