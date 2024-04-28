import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MovieService } from '../../service/data/movie.service';
import { MovieDetails } from '../../model/MovieDetails';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-movie-details',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './movie-details.component.html',
  styleUrl: './movie-details.component.css',
})
export class MovieDetailsComponent implements OnInit {
  movieDetails!: MovieDetails;

  constructor(
    public dialogRef: MatDialogRef<MovieDetailsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private movieService: MovieService
  ) {}

  ngOnInit(): void {
    this.getMovieDetails();
  }

  getMovieDetails() {
    this.movieService.getMovieById(this.data.id).subscribe({
      next: (v) => {
        this.movieDetails = v;
      },
      error: (e) => console.error(e),
      complete: () => {
        console.info('movie details fetched successfully');
      },
    });
  }
}
