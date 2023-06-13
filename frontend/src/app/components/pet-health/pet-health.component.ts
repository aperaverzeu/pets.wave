import {Component, OnInit} from '@angular/core';
import {Location} from "@angular/common";


@Component({
  selector: 'app-pet-health',
  templateUrl: './pet-health.component.html',
  styleUrls: ['./pet-health.component.scss']
})
export class PetHealthComponent implements OnInit {
  name:string;
  bpm: string;
  spo2: string;
  stressLevel: string;

  constructor(private location: Location) {
    this.name = "Маджонг";
    this.bpm = "67";
    this.spo2 = "98";
    this.stressLevel = "Низкий";
  }

  ngOnInit() {

  }


  back() {
    this.location.back();
  }
}
