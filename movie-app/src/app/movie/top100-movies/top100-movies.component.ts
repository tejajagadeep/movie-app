import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../service/data/movie.service';
import { Movie } from '../../model/Movie';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MovieResponse } from '../../model/MovieResponse';
import { WishlistService } from '../../service/data/wishlist.service';
import { TopBarComponent } from '../../navigation/top-bar/top-bar.component';
import { OpenDialogService } from '../../service/component/open-dialog.service';
import { FooterComponent } from '../../navigation/footer/footer.component';
import { Router, RouterModule } from '@angular/router';
import { NoContentComponent } from '../../errors/no-content/no-content.component';
import { InternalServerErrorComponent } from '../../errors/internal-server-error/internal-server-error.component';
import { MatIconModule } from '@angular/material/icon';

@Component({
  selector: 'app-top100-movies',
  standalone: true,
  templateUrl: './top100-movies.component.html',
  styleUrl: './top100-movies.component.css',
  imports: [
    HttpClientModule,
    CommonModule,
    TopBarComponent,
    FooterComponent,
    RouterModule,
    NoContentComponent,
    InternalServerErrorComponent,
    MatIconModule,
  ],
})
export class Top100MoviesComponent implements OnInit {
  constructor(
    private movieService: MovieService,
    private wishlistService: WishlistService,
    private openDialog: OpenDialogService,
    private router: Router
  ) {}

  movieResponse: MovieResponse = new MovieResponse();
  imdbIds: string[] = [];
  username = localStorage.getItem('username') ?? '';
  isFavorite: boolean = false;
  statusCode!: number;

  ngOnInit(): void {
    this.getTop100Movies();
    this.getWishlist();
    this.supports_html5_storage();
  }

  toggleFavorite(movie: Movie) {
    if (this.imdbIds.includes(movie.imdbid)) {
      this.delete(movie.imdbid);
      this.isFavorite = true;
    } else {
      this.saveWishlist(movie);
      this.isFavorite = false;
    }
  }
  supports_html5_storage() {
    try {
      return 'localStorage' in window && window['localStorage'] !== null;
    } catch (e) {
      return false;
    }
  }
  getTop100Movies() {
    this.movieService.getTop100Movies().subscribe({
      next: (v) => {
        this.movieResponse = v;
      },
      error: (e) => {
        console.error(e), (this.statusCode = e.status);
      },
      complete: () => {
        console.info('top 100 movies fetched successfully');
      },
    });
  }

  getWishlist() {
    this.wishlistService.getWishlist(this.username).subscribe({
      next: (v) => {
        v.movies.forEach((movie) => {
          this.imdbIds.push(movie.imdbid);
        });
      },
      error: (e) => {
        console.error(e), (this.statusCode = e.status);
        if (this.statusCode === 400 || this.statusCode === 401) {
          console.error(e), localStorage.removeItem('token');
          localStorage.removeItem('username');
          this.router.navigate(['/login']);
        }
      },
      complete: () => {
        console.info('wishlist fetched successfully');
      },
    });
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
  }
  saveWishlist(movie: Movie) {
    console.log(this.username);
    this.wishlistService.saveWishlist(this.username, movie).subscribe({
      next: (v) => {
        this.imdbIds = [];
        v.movies.forEach((movie) => {
          this.imdbIds.push(movie.imdbid);
        });
      },
      error: (e) => {
        console.error(e), (this.statusCode = e.status);
        if (this.statusCode === 400 || this.statusCode === 401) {
          console.error(e), localStorage.removeItem('token');
          localStorage.removeItem('username');
          this.router.navigate(['/login']);
        }
      },
      complete: () => {
        console.info('movie saved successfully');
      },
    });
  }

  delete(id: string) {
    this.wishlistService.deleteWishlist(this.username, id).subscribe({
      next: (v) => {
        this.imdbIds = [];
        v.movies.forEach((movie) => {
          this.imdbIds.push(movie.imdbid);
        });
      },
      error: (e) => {
        console.error(e), (this.statusCode = e.status);
        if (this.statusCode === 400 || this.statusCode === 401) {
          console.error(e), localStorage.removeItem('token');
          localStorage.removeItem('username');
          this.router.navigate(['/login']);
        }
      },
      complete: () => {
        console.info('movie deleted successfully');
      },
    });
  }

  moreDetails(link: any) {
    window.open(link, '_blank');
  }

  playTrailer(id: String) {
    this.openDialog.openPlayDialog(id);
  }
}
