import { Component, OnInit } from '@angular/core';
import { TopBarComponent } from '../../navigation/top-bar/top-bar.component';
import { MovieResponse } from '../../model/MovieResponse';
import { WishlistService } from '../../service/data/wishlist.service';
import { Movie } from '../../model/Movie';
import { CommonModule } from '@angular/common';
import { NotFoundComponent } from '../../errors/not-found/not-found.component';
import { NoContentComponent } from '../../errors/no-content/no-content.component';
import { InternalServerErrorComponent } from '../../errors/internal-server-error/internal-server-error.component';
import { Router } from '@angular/router';
import { MatTooltipModule } from '@angular/material/tooltip';
import { FooterComponent } from '../../navigation/footer/footer.component';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-wishlist',
  standalone: true,
  templateUrl: './wishlist.component.html',
  styleUrl: './wishlist.component.css',
  imports: [
    TopBarComponent,
    CommonModule,
    NotFoundComponent,
    NoContentComponent,
    InternalServerErrorComponent,
    MatTooltipModule,
    FooterComponent,
  ],
})
export class WishlistComponent implements OnInit {
  constructor(
    private wishlistService: WishlistService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  movies!: Movie[];
  imdbIds: string[] = [];
  statusCode!: number;
  username: string = 'user';
  ngOnInit(): void {
    const storedUsername = localStorage.getItem('username');
    if (
      storedUsername !== null &&
      storedUsername !== undefined &&
      storedUsername !== 'undefined'
    ) {
      this.username = storedUsername;
    }
    this.getWishlist();
  }

  getWishlist() {
    this.wishlistService.getWishlist(this.username).subscribe({
      next: (v) => {
        this.movies = v.movies;
      },
      error: (e) => {
        console.error(e), (this.statusCode = e.status);
        if (this.statusCode === 500 || this.statusCode === 503) {
          this.snackBar.open(
            'The service is currently unavailable. Please try again later.',
            'Close',
            {
              duration: 3000,
            }
          );
        }
        if (this.statusCode === 400 || this.statusCode === 401) {
          this.snackBar.open(
            'Wrong user credentials. Please Login again.',
            'Close',
            {
              duration: 3000, // Duration in milliseconds
            }
          );
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

  delete(id: string) {
    this.wishlistService.deleteWishlist(this.username, id).subscribe({
      next: (v) => {
        this.movies = v.movies;
      },
      error: (e) => {
        console.error(e), (this.statusCode = e.status);
        if (this.statusCode === 500 || this.statusCode === 503) {
          this.snackBar.open(
            'The service is currently unavailable. Please try again later.',
            'Close',
            {
              duration: 3000,
            }
          );
        }
        if (this.statusCode === 400 || this.statusCode === 401) {
          this.snackBar.open(
            'Wrong user credentials. Please Login again.',
            'Close',
            {
              duration: 3000, // Duration in milliseconds
            }
          );
          console.error(e), localStorage.removeItem('token');
          localStorage.removeItem('username');
          this.router.navigate(['/login']);
        }
      },
      complete: () => {
        console.info('movie deleted successfully');
        this.snackBar.open('Movie delete From Wishlist.', 'Close', {
          duration: 3000, // Duration in milliseconds
        });
      },
    });
  }

  moreDetails(link: any) {
    window.open(link, '_blank');
  }

  play(link: any) {
    window.open(link, '_blank');
  }
}
