import { Component, OnInit } from '@angular/core';
import { FooterComponent } from '../../navigation/footer/footer.component';
import { CommonModule } from '@angular/common';
import { UserProfile } from '../../model/UserProfile';
import { UserprofileService } from '../../service/data/userprofile.service';
import { RouterModule } from '@angular/router';
import { WishlistService } from '../../service/data/wishlist.service';
import { Movie } from '../../model/Movie';
import { TopBarComponent } from '../../navigation/top-bar/top-bar.component';
import { NotFoundComponent } from '../../errors/not-found/not-found.component';

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
  ],
})
export class ProfileComponent implements OnInit {
  userProfile!: UserProfile;
  movies!: Movie[];

  constructor(
    private userService: UserprofileService,
    private wishlists: WishlistService
  ) {}
  ngOnInit(): void {
    this.users();
    this.getWishlist();
  }

  users() {
    const username = localStorage.getItem('username') ?? '';
    this.userService.getUserProfile(username).subscribe({
      next: (v) => {
        this.userProfile = v;
      },
      error: (e) => console.error(e),
      complete: () => {
        console.info('user profile fetched successfully');
      },
    });
  }

  getWishlist() {
    const username = localStorage.getItem('username') ?? '';
    this.wishlists.getWishlist(username).subscribe({
      next: (v) => {
        this.movies = v.movies;
      },
      error: (e) => console.error(e),
      complete: () => {
        console.info('wishlist fetched successfully');
      },
    });
  }
}
