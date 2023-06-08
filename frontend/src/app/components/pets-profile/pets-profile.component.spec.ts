import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PetsProfileComponent } from './pets-profile.component';

describe('PetsProfileComponent', () => {
  let component: PetsProfileComponent;
  let fixture: ComponentFixture<PetsProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PetsProfileComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PetsProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
