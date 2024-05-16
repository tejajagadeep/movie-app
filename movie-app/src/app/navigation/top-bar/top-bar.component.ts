import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-top-bar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.css',
})
export class TopBarComponent implements OnInit {
  constructor(private snackBar: MatSnackBar) {}

  username!: string;
  isLoggedIn = false;

  ngOnInit(): void {
    const storedUsername = sessionStorage.getItem('username');
    const storedToken = sessionStorage.getItem('token');
    if (
      storedUsername !== null &&
      storedUsername !== undefined &&
      storedUsername !== 'undefined' &&
      storedToken !== null &&
      storedToken !== undefined &&
      storedToken !== 'undefined'
    ) {
      this.username = storedUsername;
      this.isLoggedIn = true;
    } else {
      this.isLoggedIn = false;
    }
  }
  logout() {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('username');
    this.snackBar.open('You have Logged Out.', 'Close', {
      duration: 3000, // Duration in milliseconds
    });
  }
}
