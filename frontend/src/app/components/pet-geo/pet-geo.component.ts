import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-pet-geo',
  templateUrl: './pet-geo.component.html',
  styleUrls: ['./pet-geo.component.scss'],
})
export class PetGeoComponent {
  name: string | null;

  constructor(private location: Location) {
    this.name = sessionStorage.getItem('actual-pet-name');
  }

  back() {
    this.location.back();
  }
}
