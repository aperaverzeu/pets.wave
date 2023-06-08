import {Component, OnInit} from '@angular/core';
import {PetType} from "../../models/pet-type";

@Component({
  selector: 'app-pet-registration',
  templateUrl: './pet-registration.component.html',
  styleUrls: ['./pet-registration.component.scss']
})
export class PetRegistrationComponent implements OnInit {
  name: string | undefined;
  weight: string | undefined;
  height: string | undefined;
  age: string | undefined;
  petType: PetType | undefined;
  petTypes: PetType[] = [];

  ngOnInit(): void {
    this.petTypes = [
      {petType: "Собака"},
      {petType: "Кошка"}
    ]
  }
}
