import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MovieDetailsComponent } from '../../movie/movie-details/movie-details.component';

@Injectable({
  providedIn: 'root',
})
export class OpenDialogService {
  constructor(private dialog: MatDialog) {}

  openPlayDialog(idNumber: String): void {
    this.dialog.open(MovieDetailsComponent, {
      data: { id: idNumber },
      width: '80%',
    });
  }
}
