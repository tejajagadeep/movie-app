import { Component, OnInit } from '@angular/core';
import { MovieResponse } from '../model/MovieResponse';
import { MovieService } from '../service/data/movie.service';
import { WishlistService } from '../service/data/wishlist.service';
import { OpenDialogService } from '../service/component/open-dialog.service';
import { Movie } from '../model/Movie';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TopBarComponent } from '../navigation/top-bar/top-bar.component';
import { FooterComponent } from '../navigation/footer/footer.component';
import { RouterModule } from '@angular/router';
import { NoContentComponent } from '../errors/no-content/no-content.component';
import { InternalServerErrorComponent } from '../errors/internal-server-error/internal-server-error.component';

@Component({
  selector: 'app-test',
  standalone: true,
  imports: [
    HttpClientModule,
    CommonModule,
    TopBarComponent,
    FooterComponent,
    RouterModule,
    NoContentComponent,
    InternalServerErrorComponent,
  ],
  templateUrl: './test.component.html',
  styleUrl: './test.component.css',
})
export class TestComponent implements OnInit {
  constructor(
    private movieService: MovieService,
    private wishlistService: WishlistService,
    private openDialog: OpenDialogService
  ) {}

  movieResponse: MovieResponse = new MovieResponse();
  imdbIds: string[] = [];
  username = localStorage.getItem('username') ?? '';
  isFavorite: boolean = false;
  statusCode!: number;
  heartStates: { [key: string]: string } = {};

  ngOnInit(): void {
    this.getTop100Movies();
    this.getWishlist();
    this.supports_html5_storage();
  }

  toggleHeartState(movie: Movie): void {
    if (!this.imdbIds.includes(movie.imdbid)) {
      if (this.heartStates[movie.imdbid] === 'active') {
        this.heartStates[movie.imdbid as any] = 'inactive';
        this.delete(movie.imdbid);
      } else {
        this.heartStates[movie.imdbid as any] = 'active';
        this.saveWishlist(movie);
      }
    } else {
      if (this.heartStates[movie.imdbid] === 'inactive') {
        this.heartStates[movie.imdbid as any] = 'active';
        this.saveWishlist(movie);
      } else {
        this.heartStates[movie.imdbid as any] = 'inactive';
        this.delete(movie.imdbid);
      }
    }
  }
  getHeartState(imdbid: string): string {
    if (this.imdbIds.includes(imdbid)) {
      return this.heartStates[imdbid] || 'active';
    } else {
      return this.heartStates[imdbid] || 'inactive';
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
      error: (e) => console.error(e),
      complete: () => {
        console.info('wishlist fetched successfully');
      },
    });
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
      error: (e) => console.error(e),
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
      error: (e) => console.error(e),
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
