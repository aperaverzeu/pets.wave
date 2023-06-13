import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-pet-geo',
  templateUrl: './pet-geo.component.html',
  styleUrls: ['./pet-geo.component.scss'],
})
export class PetGeoComponent {
  name: string;

  constructor(private location: Location) {
    this.name = 'Маджонг';
  }

  back() {
    this.location.back();
  }
}
