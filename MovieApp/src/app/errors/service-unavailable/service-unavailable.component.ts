import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-service-unavailable',
  standalone: true,
  imports: [],
  templateUrl: './service-unavailable.component.html',
  styleUrl: './service-unavailable.component.css',
})
export class ServiceUnavailableComponent implements OnInit {
  ngOnInit(): void {
    console.error('Service Unavailable');
  }
}
