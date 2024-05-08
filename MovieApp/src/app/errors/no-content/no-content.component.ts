import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-no-content',
  standalone: true,
  imports: [RouterModule],
  templateUrl: './no-content.component.html',
  styleUrl: './no-content.component.css',
})
export class NoContentComponent {}
