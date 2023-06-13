import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PetGeoComponent } from './pet-geo.component';

describe('PetGeoComponent', () => {
  let component: PetGeoComponent;
  let fixture: ComponentFixture<PetGeoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PetGeoComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PetGeoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
