import { Component, Inject, OnInit } from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogModule,
  MatDialogRef,
} from '@angular/material/dialog';
import { MovieService } from '../../service/data/movie.service';
import { MovieDetails } from '../../model/MovieDetails';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-movie-details',
  standalone: true,
  imports: [CommonModule, MatDialogModule],
  templateUrl: './movie-details.component.html',
  styleUrl: './movie-details.component.css',
})
export class MovieDetailsComponent implements OnInit {
  movieDetails!: MovieDetails;
  id!: string;
  safeTrailerUrl!: SafeResourceUrl;

  constructor(
    // public dialogRef: MatDialogRef<MovieDetailsComponent>,
    // @Inject(MAT_DIALOG_DATA) public data: { id: string },
    private movieService: MovieService,
    private route: ActivatedRoute,
    private sanitizer: DomSanitizer
  ) {}

  ngOnInit(): void {
    this.id = this.route.snapshot.params['id'];
    this.getMovieDetails(this.id);
    this.safeTrailerUrl = this.sanitizer.bypassSecurityTrustResourceUrl(
      this.movieDetails.trailer_embed_link
    );
  }

  getMovieDetails(id: string) {
    this.movieService.getMovieById(id).subscribe({
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
