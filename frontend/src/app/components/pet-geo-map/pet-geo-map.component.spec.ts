import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PetGeoMapComponent } from './pet-geo-map.component';

describe('PetGeoMapComponent', () => {
  let component: PetGeoMapComponent;
  let fixture: ComponentFixture<PetGeoMapComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PetGeoMapComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(PetGeoMapComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
