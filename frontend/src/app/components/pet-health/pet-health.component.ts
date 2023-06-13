import { Component } from '@angular/core';
import { Location } from '@angular/common';

@Component({
  selector: 'app-pet-health',
  templateUrl: './pet-health.component.html',
  styleUrls: ['./pet-health.component.scss'],
})
export class PetHealthComponent {
  name: string;

  bpm: string;

  spo2: string;

  stressLevel: string;

  constructor(private location: Location) {
    this.name = 'Маджонг';
    this.bpm = '67';
    this.spo2 = '98';
    this.stressLevel = 'Низкий';
  }

  back() {
    this.location.back();
  }
}
