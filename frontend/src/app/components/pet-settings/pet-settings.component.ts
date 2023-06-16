import { Component } from '@angular/core';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { Pet } from '../../models/pet';
import { PetService } from '../../services/pet.service';

@Component({
  selector: 'app-pet-settings',
  templateUrl: './pet-settings.component.html',
  styleUrls: ['./pet-settings.component.scss'],
})
export class PetSettingsComponent {
  name: string;

  pet: Pet;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private petService: PetService,
    private router: Router,
  ) {
    this.name = sessionStorage.getItem('actual-pet-name')!;
    this.pet = JSON.parse(sessionStorage.getItem('actual-pet')!);
  }

  toUpdatePet() {
    this.router.navigate(['/pet-update']).finally();
    console.log(this.pet);
  }

  toDeletePet() {
    this.confirmationService.confirm({
      message: '',
      header: 'Вы уверены?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.petService
          .deletePet({
            petId: this.pet.id,
            name: this.pet.name,
            weight: this.pet.weight,
            height: this.pet.height,
            age: this.pet.age,
            petType: this.pet.petType,
            userId: this.pet.userId,
            collarId: this.pet.collarId,
          })
          .subscribe({
            next: (value) => {
              this.messageService.add({
                severity: 'info',
                summary: 'Успех',
                detail: 'Питомец удалён',
              });
              this.router.navigate(['/pets-profile']).finally(() => console.log(value));
            },
            error: (err) => console.error(err),
          });
      },
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      reject: (type: any) => {
        console.log('Галя, отмена!');
      },
    });
  }
}
