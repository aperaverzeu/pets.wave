import { Component } from '@angular/core';
import {Pet} from "../../models/pet";
import {v4 as uuid} from 'uuid';
import {PetService} from "../../services/pet.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-pets-profile',
  templateUrl: './pets-profile.component.html',
  styleUrls: ['./pets-profile.component.scss']
})
export class PetsProfileComponent {
  name: string = "Маджонг"

  pets: Pet[] = [
    {petId: uuid(), name: "Маджонг"}, {petId: uuid(), name: "Джордж"}
  ]

  constructor(private petService: PetService,
              private router: Router) {
  }

  toPetProfile(id?: string, name?: string) {
    localStorage.setItem("actual-pet-id", <string>id);
    localStorage.setItem("actual-pet-name", <string>name);

    this.router.navigate(["/pet-profile"]).finally();
  }

  toPetRegistration() {
    this.router.navigate(["/pet-registration"]).finally();
  }
}
