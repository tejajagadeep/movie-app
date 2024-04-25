import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Top100MoviesComponent } from './top100-movies.component';

describe('Top100MoviesComponent', () => {
  let component: Top100MoviesComponent;
  let fixture: ComponentFixture<Top100MoviesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Top100MoviesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(Top100MoviesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
