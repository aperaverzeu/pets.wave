import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PetService } from '../../services/pet.service';
import { Pet } from '../../models/pet';

@Component({
  selector: 'app-pets-profile',
  templateUrl: './pets-profile.component.html',
  styleUrls: ['./pets-profile.component.scss'],
})
export class PetsProfileComponent implements OnInit {
  name = 'Маджонг';

  pets: Pet[];

  constructor(private petService: PetService, private router: Router) {
    this.pets = [];
  }

  ngOnInit() {
    setTimeout(() => {
      this.petService.getAllPets().subscribe({
        next: (pets) => {
          pets.forEach((pet) => {
            this.pets.push(pet);
          });
        },
        error: () => {
          console.log('error');
        },
        complete: () => {
          console.log('complete');
        },
      });
    }, 300);
  }

  toPetProfile(pet: Pet) {
    sessionStorage.setItem('actual-pet-id', pet.id != null ? pet.id : '');
    sessionStorage.setItem('actual-pet-name', pet.name != null ? pet.name : '');
    this.router.navigate(['/pet-profile']).finally();
  }

  toPetRegistration() {
    this.router.navigate(['/pet-registration']).finally();
  }

  toUserProfile() {
    this.router.navigate(['/user-profile']).finally();
  }
}
