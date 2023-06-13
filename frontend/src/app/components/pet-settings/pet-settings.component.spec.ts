import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PetSettingsComponent } from './pet-settings.component';

describe('PetSettingsComponent', () => {
  let component: PetSettingsComponent;
  let fixture: ComponentFixture<PetSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PetSettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PetSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
