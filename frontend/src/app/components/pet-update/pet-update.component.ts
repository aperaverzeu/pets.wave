import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { validate } from 'uuid';
import { PetService } from '../../services/pet.service';
import { Pet } from '../../models/pet';

@Component({
  selector: 'app-pet-update',
  templateUrl: './pet-update.component.html',
  styleUrls: ['./pet-update.component.scss'],
})
export class PetUpdateComponent {
  name: string | undefined;

  weight: string | undefined;

  height: string | undefined;

  age: string | undefined;

  collarId: string | undefined;

  pet: Pet;

  userId: string;

  constructor(
    private petService: PetService,
    private messageService: MessageService,
    private router: Router,
  ) {
    this.pet = JSON.parse(sessionStorage.getItem('actual-pet')!);
    this.name = this.pet.name;
    this.weight = this.pet.weight;
    this.height = this.pet.height;
    this.age = this.pet.age;
    this.collarId = this.pet.collarId;
    this.userId = sessionStorage.getItem('actual-user-id')!;
  }

  onclick() {
    if (
      this.name === '' ||
      this.name === null ||
      this.name === undefined ||
      this.weight === '' ||
      this.weight === null ||
      this.weight === undefined ||
      this.height === '' ||
      this.height === null ||
      this.height === undefined ||
      this.age === '' ||
      this.age === null ||
      this.age === undefined ||
      this.collarId === '' ||
      this.collarId === null ||
      this.collarId === undefined
    ) {
      this.validate('Все поля должны быть заполнены');
      return;
    }

    if (parseInt(this.weight, 10) < 1 || parseInt(this.weight, 10) > 100) {
      this.validate('Укажите реальный вес питомца');
      return;
    }

    if (parseInt(this.height, 10) < 10 || parseInt(this.height, 10) > 170) {
      this.validate('Укажите реальный рост питомца');
      return;
    }

    if (parseInt(this.age, 10) < 1 || parseInt(this.age, 10) > 310) {
      this.validate('Укажите реальный возраст питомца');
      return;
    }

    if (this.pet.age! > this.age) {
      this.validate('Возраст питомца не может быть меньше, чем он был');
      return;
    }

    if (!validate(this.collarId)) {
      this.validate('Неверный номер ошейника');
      return;
    }

    this.petService
      .updatePet({
        petId: this.pet.id,
        name: this.name,
        weight: this.weight,
        height: this.height,
        age: this.age,
        petType: this.pet.petType,
        userId: this.userId,
        collarId: this.pet.collarId,
      })
      .subscribe({
        next: (value) => this.router.navigate(['/pets-profile']).finally(() => console.log(value)),
        error: (err) => console.error(err),
      });
  }

  validate(detail: string) {
    this.messageService.add({ severity: 'error', summary: 'Ошибка', detail });
  }
}
