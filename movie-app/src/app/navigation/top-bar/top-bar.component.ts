import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-top-bar',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './top-bar.component.html',
  styleUrl: './top-bar.component.css',
})
export class TopBarComponent implements OnInit {
  username!: string;

  ngOnInit(): void {
    const storedUsername = localStorage.getItem('username');
    if (
      storedUsername !== null &&
      storedUsername !== undefined &&
      storedUsername !== 'undefined'
    ) {
      this.username = storedUsername;
    }
  }
  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('username');
  }
}
