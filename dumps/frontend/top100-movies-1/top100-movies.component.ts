import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../service/data/movie.service';
import { Movie } from '../../model/Movie';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { MovieResponse } from '../../model/MovieResponse';

@Component({
  selector: 'app-top100-movies',
  standalone: true,
  imports: [HttpClientModule, CommonModule],
  templateUrl: './top100-movies.component.html',
  styleUrl: './top100-movies.component.css',
})
export class Top100MoviesComponent implements OnInit {
  constructor(private movieService: MovieService) {}

  movieResponse: MovieResponse = new MovieResponse();

  ngOnInit(): void {
    this.getTop100Movies();
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
}
