import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NoContentComponent } from './no-content.component';
import { HomeComponent } from '../../public/home/home.component';
import { provideRouter } from '@angular/router';
import { AuthGuard } from '../../service/security/auth.guard';

describe('NoContentComponent', () => {
  let component: NoContentComponent;
  let fixture: ComponentFixture<NoContentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NoContentComponent],
      providers: [
        provideRouter([
          {
            path: 'home',
            component: HomeComponent,
            canActivate: [AuthGuard],
          },
        ]),
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(NoContentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
