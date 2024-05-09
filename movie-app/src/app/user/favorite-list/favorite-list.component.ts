import { Component, OnInit } from '@angular/core';
import { TopBarComponent } from '../../navigation/top-bar/top-bar.component';
import { MovieResponse } from '../../model/MovieResponse';
import { WishlistService } from '../../service/data/wishlist.service';
import { Movie } from '../../model/Movie';
import { CommonModule } from '@angular/common';
import { NotFoundComponent } from '../../errors/not-found/not-found.component';
import { NoContentComponent } from '../../errors/no-content/no-content.component';
import { InternalServerErrorComponent } from '../../errors/internal-server-error/internal-server-error.component';

@Component({
  selector: 'app-favorite-list',
  standalone: true,
  templateUrl: './favorite-list.component.html',
  styleUrl: './favorite-list.component.css',
  imports: [
    TopBarComponent,
    CommonModule,
    NotFoundComponent,
    NoContentComponent,
    InternalServerErrorComponent,
  ],
})
export class FavoriteListComponent implements OnInit {
  constructor(private wishlistService: WishlistService) {}

  movies!: Movie[];
  imdbIds: string[] = [];
  username = localStorage.getItem('username') ?? '';
  statusCode!: number;

  ngOnInit(): void {
    this.getWishlist();
  }

  getWishlist() {
    this.wishlistService.getWishlist(this.username).subscribe({
      next: (v) => {
        this.movies = v.movies;
      },
      error: (e) => {
        console.error(e), (this.statusCode = e.status);
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
        console.error(e);
      },
      complete: () => {
        console.info('movie deleted successfully');
      },
    });
  }

  moreDetails(link: any) {
    window.open(link, '_blank');
  }
}
