import { ComponentFixture, TestBed } from '@angular/core/testing';
import {
  HttpClientTestingModule,
  HttpTestingController,
  provideHttpClientTesting,
} from '@angular/common/http/testing';
import { WishlistComponent } from './wishlist.component';
import { WishlistService } from '../../service/data/wishlist.service';
import { provideHttpClient, withFetch } from '@angular/common/http';
import { Movie } from '../../model/Movie';
import { ActivatedRouteSnapshot, provideRouter } from '@angular/router';
import { AuthGuard } from '../../service/security/auth.guard';

describe('WishlistComponent', () => {
  let component: WishlistComponent;
  let fixture: ComponentFixture<WishlistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WishlistComponent, HttpClientTestingModule],
      providers: [
        provideHttpClientTesting(),
        WishlistService,
        provideRouter([
          {
            path: 'wishlist',
            component: WishlistComponent,
            canActivate: [AuthGuard],
          },
        ]),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(WishlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  const dummyMovies: Movie[] = [
    {
      id: '1',
      title: 'Movie 1',
      description: '',
      genre: [],
      image: '',
      imdb_link: '',
      imdbid: '',
      rank: 3,
      rating: 4.5,
      thumbnail: 'fg',
      year: 2000,
    },
  ];
});
