import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../service/data/movie.service';
import { Movie } from '../../model/Movie';
import { MovieResponse } from '../../model/MovieResponse';
import { WishlistService } from '../../service/data/wishlist.service';
import { TopBarComponent } from '../../navigation/top-bar/top-bar.component';
import { OpenDialogService } from '../../service/component/open-dialog.service';
import { FooterComponent } from '../../navigation/footer/footer.component';
import { Router, RouterModule } from '@angular/router';
import { NoContentComponent } from '../../errors/no-content/no-content.component';
import { InternalServerErrorComponent } from '../../errors/internal-server-error/internal-server-error.component';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatFormFieldModule } from '@angular/material/form-field';
import { FormsModule, FormControl, ReactiveFormsModule } from '@angular/forms';
import { debounceTime, distinctUntilChanged } from 'rxjs';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { AuthenticationService } from '../../service/data/authentication.service';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-top100-movies',
  standalone: true,
  templateUrl: './top100-movies.component.html',
  styleUrl: './top100-movies.component.css',
  imports: [
    TopBarComponent,
    FooterComponent,
    RouterModule,
    NoContentComponent,
    InternalServerErrorComponent,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    MatSidenavModule,
    MatIconModule,
    MatButtonModule,
    MatToolbarModule,
    MatTooltipModule,
    MatInputModule,
    MatSelectModule,
    MatPaginatorModule,
  ],
})
export class Top100MoviesComponent implements OnInit {
  constructor(
    private authService: AuthenticationService,
    private movieService: MovieService,
    private wishlistService: WishlistService,
    private openDialog: OpenDialogService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  movieResponse: MovieResponse = new MovieResponse();
  imdbIds: string[] = [];
  username = '';
  isFavorite: boolean = false;
  statusCode!: number;
  searchControl: FormControl = new FormControl('');
  options: { value: string; viewValue: string }[] = [];

  pageSize!: number; // Number of items per page
  currentPage = 1;
  totalPages!: number;
  pagedMovies: Movie[] = [];
  pageSizeOptions = [4, 8, 24, 100];

  ngOnInit(): void {
    this.username = localStorage.getItem('username') ?? '';
    this.genres();
    this.getTop100Movies();
    this.getWishlist();
    this.supports_html5_storage();
    this.searchForm();
    this.pageSize = this.pageSizeOptions[1];
  }
  paginatorPage(event: PageEvent) {
    // Update custom pagination controls based on mat-paginator
    console.log('paging paginatorPage ' + event.previousPageIndex);
    this.pageSize = event.pageSize;
    this.currentPage = event.pageIndex + 1;
    this.setPage(this.currentPage);
  }

  calculateTotalPages() {
    this.totalPages = this.movieResponse.data.length;
  }

  setPage(page: number) {
    if (page < 1 || page > this.totalPages) {
      return;
    }

    const startIndex = (page - 1) * this.pageSize;
    const endIndex = Math.min(
      startIndex + this.pageSize - 1,
      this.movieResponse.data.length - 1
    );
    this.pagedMovies = this.movieResponse.data.slice(startIndex, endIndex + 1);
    this.currentPage = page;
    console.log('paging setPage ' + this.currentPage);
  }

  prevPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.setPage(this.currentPage);
    }
  }

  nextPage() {
    if (this.currentPage <= this.totalPages) {
      this.currentPage++;
      this.setPage(this.currentPage);
    }
  }

  searchForm() {
    this.searchControl.valueChanges
      .pipe(
        debounceTime(300), // Debounce to reduce unnecessary API calls
        distinctUntilChanged() // Ensure that only distinct values trigger the search
      )
      .subscribe((searchTerm) => {
        this.searchTopMovies(searchTerm);
      });
  }

  genres() {
    const fetchedGenres = [
      'Action',
      'Adventure',
      'Animation',
      'Biography',
      'Comedy',
      'Crime',
      'Drama',
      'Experimental',
      'Family',
      'Fantasy',
      'Film-Noir',
      'History',
      'Horror',
      'Music',
      'Musical',
      'Mystery',
      'Romance',
      'Sci-Fi',
      'Thriller',
      'War',
      'Western',
    ];

    this.options = [];

    fetchedGenres.forEach((genre) => {
      this.options.push({ value: genre.toLowerCase(), viewValue: genre });
    });
    this.options.push({ value: 'uncheck', viewValue: 'Uncheck' });
  }

  getTop100Movies() {
    this.movieService.getTop100Movies().subscribe({
      next: (v) => {
        if (v) {
          this.movieResponse = v;
        } else {
          console.log('Null values while fetching top movies');
        }
      },
      error: (e) => {
        if (e) {
          console.error(e), (this.statusCode = e.status);
        } else {
          console.log('Null values while fetching error top movies');
        }
      },
      complete: () => {
        console.info('top 100 movies fetched successfully');
        this.calculateTotalPages();
        this.setPage(1);
      },
    });
  }

  searchTopMovies(movieTitle: string) {
    if (movieTitle.trim() !== '') {
      this.movieService.searchTop100Movies(movieTitle).subscribe({
        next: (v) => {
          if (v) {
            this.movieResponse = v;
          } else {
            console.log('Null values while fetching searchTopMovies');
          }
        },
        error: (e) => {
          if (e) {
            console.error(e), (this.statusCode = e.status);
          } else {
            console.log('Null values while fetching error message top search');
          }
        },
        complete: () => {
          console.info('searched ' + movieTitle + ' movies successfully');
          this.calculateTotalPages();
          this.setPage(1);
        },
      });
    } else if (movieTitle === '') {
      this.getTop100Movies();
    }
  }

  filterGenre(genre: string) {
    if (genre === 'uncheck') {
      this.getTop100Movies();
    } else {
      this.movieService.filterGenreTopMovies(genre).subscribe({
        next: (v) => {
          if (v) {
            this.movieResponse = v;
          } else {
            console.log('Null values while fetching filterGenre');
          }
        },
        error: (e) => {
          if (e) {
            console.error(e), (this.statusCode = e.status);
          } else {
            console.log('Null values while fetching error message filterGenre');
          }
        },
        complete: () => {
          console.info('filter genre ' + genre + ' fetched successfully');
          this.calculateTotalPages();
          this.setPage(1);
        },
      });
    }
  }

  getWishlist() {
    this.wishlistService.getWishlist(this.username).subscribe({
      next: (v) => {
        if (v) {
          v.movies.forEach((movie) => {
            this.imdbIds.push(movie.imdbid);
          });
        } else {
          console.log('Null values while fetching wishlist');
        }
      },
      error: (e) => {
        this.wishlistError(e);
      },
      complete: () => {
        console.info('wishlist fetched successfully');
      },
    });
  }

  saveWishlist(movie: Movie) {
    console.log(this.username);
    this.wishlistService.saveWishlist(this.username, movie).subscribe({
      next: (v) => {
        if (v) {
          this.imdbIds = [];
          v.movies.forEach((movie) => {
            this.imdbIds.push(movie.imdbid);
          });
        } else {
          console.log('Null values while fetching saving movie');
        }
      },
      error: (e) => {
        this.wishlistError(e);
      },
      complete: () => {
        console.info('movie saved successfully');
        this.openSnackBarWithOpenButton(
          'Movie added to Wishlist',
          'Open',
          '/wishlist'
        );
      },
    });
  }
  openSnackBarWithOpenButton(message: string, action: string, route: string) {
    let snackBarRef = this.snackBar.open(message, action, {
      duration: 3000, // Duration in milliseconds
    });

    // Override the action button behavior to navigate to the specified route
    snackBarRef.onAction().subscribe(() => {
      this.router.navigate([route]);
    });
  }
  delete(id: string) {
    this.wishlistService.deleteWishlist(this.username, id).subscribe({
      next: (v) => {
        if (v) {
          this.imdbIds = [];
          v.movies.forEach((movie) => {
            this.imdbIds.push(movie.imdbid);
          });
        } else {
          console.log('Null values while fetching delete movie');
        }
      },
      error: (e) => {
        this.wishlistError(e);
      },
      complete: () => {
        console.info('movie deleted successfully');
        this.snackBar.open('Movie deleted from Wishlist.', 'Close', {
          duration: 3000, // Duration in milliseconds
        });
      },
    });
  }

  wishlistError(error: any) {
    if (error) {
      console.error(error), (this.statusCode = error.status);
      if (this.statusCode === 400 || this.statusCode === 401) {
        console.error(error), localStorage.removeItem('token');
        localStorage.removeItem('username');
        this.validateUser();
      }
    } else {
      console.log('Null values while fetching error message');
    }
  }

  validateUser() {
    let result = false;
    this.authService.isUserLoggedIn(this.username).subscribe({
      next: (v) => {
        if (v) {
          result = v;
          console.log(result);
        } else {
          console.log('Null values while fetching delete movie');
        }
      },
      error: (e) => {
        if (e) {
          console.error(e), (this.statusCode = e.status);
          this.snackBar.open(
            'Wrong user credentials. Please Login again.',
            'Close',
            {
              duration: 3000, // Duration in milliseconds
            }
          );
          this.router.navigate(['/login']);
        } else {
          console.log('Null values when validating user');
        }
      },
      complete: () => {
        console.info('validation successfully');
      },
    });
  }

  moreDetails(link: any) {
    window.open(link, '_blank');
  }

  playTrailer(id: String) {
    this.openDialog.openPlayDialog(id);
  }

  supports_html5_storage() {
    try {
      return 'localStorage' in window && window['localStorage'] !== null;
    } catch (e) {
      return false;
    }
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
    this.snackBar.open('You have logged Out', 'Close', {
      duration: 3000, // Duration in milliseconds
    });
  }
}
