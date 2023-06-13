import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PetService } from '../../services/pet.service';
import { Pet } from '../../models/pet';

@Component({
  selector: 'app-pet-profile',
  templateUrl: './pet-profile.component.html',
  styleUrls: ['./pet-profile.component.scss'],
})
export class PetProfileComponent implements OnInit {
  petId: string | null;

  name: string | null;

  pet: Pet;

  constructor(private petService: PetService, private router: Router) {
    this.petId = sessionStorage.getItem('actual-pet-id');
    this.name = sessionStorage.getItem('actual-pet-name');
    this.pet = {};
  }

  ngOnInit() {
    this.petService.getPet(this.petId).subscribe({
      next: (pet) => {
        this.pet = pet;
        sessionStorage.setItem('actual-collar-id', pet.collarId != null ? pet.collarId : '');
        this.pet.weight += ' кг';
        this.pet.height += ' см';
        this.pet.age += ' мес';
      },
      error: () => {
        console.log('error');
      },
      complete: () => {
        console.log('complete');
      },
    });
  }

  toPetGeo() {
    this.router.navigate(['/pet-geo']).finally();
  }

  toPetHealth() {
    this.router.navigate(['/pet-health']).finally();
  }

  toPetSettings() {
    this.router.navigate(['/pet-settings']).finally();
  }
}
