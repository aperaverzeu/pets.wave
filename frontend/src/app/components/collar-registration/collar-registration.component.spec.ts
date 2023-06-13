import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CollarRegistrationComponent } from './collar-registration.component';

describe('CollarRegistrationComponent', () => {
  let component: CollarRegistrationComponent;
  let fixture: ComponentFixture<CollarRegistrationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CollarRegistrationComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(CollarRegistrationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
