import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FooterComponent } from '../../navigation/footer/footer.component';

@Component({
  selector: 'app-no-content',
  standalone: true,
  templateUrl: './no-content.component.html',
  styleUrl: './no-content.component.css',
  imports: [RouterModule, FooterComponent],
})
export class NoContentComponent {}
