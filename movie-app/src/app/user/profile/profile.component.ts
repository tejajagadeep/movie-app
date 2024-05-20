import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../navigation/footer/footer.component';
import { CommonModule } from '@angular/common';
import { UserProfile } from '../../model/UserProfile';
import { UserprofileService } from '../../service/data/userprofile.service';
import { Router, RouterModule } from '@angular/router';
import { WishlistService } from '../../service/data/wishlist.service';
import { Movie } from '../../model/Movie';
import { TopBarComponent } from '../../navigation/top-bar/top-bar.component';
import { NotFoundComponent } from '../../errors/not-found/not-found.component';
import { MatTooltip } from '@angular/material/tooltip';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-profile',
  standalone: true,
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
  imports: [
    FooterComponent,
    CommonModule,
    RouterModule,
    TopBarComponent,
    NotFoundComponent,
    MatTooltip,
  ],
})
export class ProfileComponent implements OnInit {
  userProfile!: UserProfile;
  movies!: Movie[];
  statusCode!: number;

  constructor(
    private userService: UserprofileService,
    private wishlists: WishlistService,
    private router: Router,
    private snackBar: MatSnackBar
  ) { }
  ngOnInit(): void {
    this.users();
    this.getWishlist();
    this.serviceError(this.statusCode);
  }

  users() {
    const username = sessionStorage.getItem('username') ?? '';
    this.userService.getUserProfile(username).subscribe({
      next: (v) => {
        this.userProfile = v;
      },
      error: (e) => {
        console.log(e);
        this.statusCode = e.status;
        if (this.statusCode === 400 || this.statusCode === 401) {
          console.error(e), sessionStorage.removeItem('token');
          sessionStorage.removeItem('username');
          this.router.navigate(['/login']);
        }
      },
      complete: () => {
        this.statusCode = 200;
        console.info('user profile fetched successfully');
      },
    });
  }

  getWishlist() {
    const username = sessionStorage.getItem('username') ?? '';
    this.wishlists.getWishlist(username).subscribe({
      next: (v) => {
        this.movies = v.movies;
      },
      error: (e) => {
        console.error(e), (this.statusCode = e.status);
        if (this.statusCode === 400 || this.statusCode === 401) {
          this.snackBar.open(
            'Wrong user credentials. Please Login again.',
            'Close',
            {
              duration: 3000, // Duration in milliseconds
            }
          );
          console.error(e), sessionStorage.removeItem('token');
          sessionStorage.removeItem('username');
          this.router.navigate(['/login']);
        }
      },
      complete: () => {
        this.statusCode = 200;
        console.info('wishlist fetched successfully');
      },
    });
  }
  serviceError(code: number) {
    if (this.statusCode === 503) {
      this.snackBar.open(
        'The service is currently unavailable. Please try again later.',
        'Close',
        {
          duration: 3000,
        }
      );
    } else if (this.statusCode === 500) {
      this.snackBar.open(
        'Internal Server Error. Please try again later.',
        'Close',
        {
          duration: 3000,
        }
      );
    } else if (this.statusCode === 404) {
      this.snackBar.open(
        'Not Found. Please try again later.',
        'Close',
        {
          duration: 3000,
        }
      );
    }
  }
}
