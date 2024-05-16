import { Component, OnInit } from '@angular/core';
import { MatTooltipModule } from '@angular/material/tooltip';

@Component({
  selector: 'app-not-found',
  standalone: true,
  imports: [MatTooltipModule],
  templateUrl: './not-found.component.html',
  styleUrl: './not-found.component.css',
})
export class NotFoundComponent implements OnInit {
  ngOnInit(): void {
    console.error('Details Not Found');
  }
}
