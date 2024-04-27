import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../service/data/movie.service';
import { Movie } from '../../model/Movie';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MovieResponse } from '../../model/MovieResponse';
import { WishlistService } from '../../service/data/wishlist.service';

@Component({
  selector: 'app-top100-movies',
  standalone: true,
  imports: [HttpClientModule, CommonModule],
  templateUrl: './top100-movies.component.html',
  styleUrl: './top100-movies.component.css',
})
export class Top100MoviesComponent implements OnInit {
  constructor(
    private movieService: MovieService,
    private wishlistService: WishlistService
  ) {}

  movieResponse: MovieResponse = new MovieResponse();
  imdbIds: string[] = [];

  ngOnInit(): void {
    this.getTop100Movies();
    this.getWishlist();
  }

  getTop100Movies() {
    this.movieService.getTop100Movies().subscribe({
      next: (v) => {
        this.movieResponse = v;
      },
      error: (e) => console.error(e),
      complete: () => {
        console.info('top 100 movies fetched successfully');
      },
    });
  }

  getWishlist() {
    const username = localStorage.getItem('username') ?? '';

    this.wishlistService.getWishlist(username).subscribe({
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

  moreDetails(link: any) {
    window.open(link, '_blank');
  }
}
