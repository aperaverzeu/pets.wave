import {Component, OnInit} from '@angular/core';
import {PetType} from "../../models/pet-type";
import {PetService} from "../../services/pet.service";
import {v4 as uuid} from "uuid";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

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
    this.toLogin();

    this.petTypes = [
      {petType: "Собака"},
      {petType: "Кошка"}
    ]
  }

  constructor(private petService: PetService,
              private messageService: MessageService,
              private router: Router) {
  }

  onclick() {
    if ((this.name === "" || this.name === null || this.name === undefined) ||
      (this.weight === "" || this.weight === null || this.weight === undefined) ||
      (this.height === "" || this.height === null || this.height === undefined) ||
      (this.age === "" || this.age === null || this.age === undefined) ||
      (this.petType?.petType === "" || this.petType?.petType === null || this.petType?.petType === undefined)) {
      this.validate('Все поля должны быть заполнены');
      return;
    }

    if (parseInt(this.weight) < 1 || parseInt(this.weight) > 100) {
      this.validate('Укажите реальный вес питомца');
      return;
    }

    if (parseInt(this.height) < 10 || parseInt(this.height) > 170) {
      this.validate('Укажите реальный рост питомца');
      return;
    }

    if (parseInt(this.age) < 1 || parseInt(this.age) > 310) {
      this.validate('Укажите реальный возраст питомца');
      return;
    }

    const userId = sessionStorage.getItem("actual-user-id");
    const petId = uuid();
    sessionStorage.setItem("actual-pet-id", petId);
    const collarId = uuid();
    sessionStorage.setItem("actual-collar-id", collarId);

    if (this.petType.petType == "Собака") {
      this.petType.petType = "DOG";
    } else if (this.petType.petType == "Кошка") {
      this.petType.petType = "CAT";
    }

    this.petService
      .createPet({
        petId: petId,
        name: this.name,
        weight: this.weight,
        height: this.height,
        age: this.age,
        petType: this.petType?.petType,
        userId: userId?.toString(),
        collarId: collarId
      })
      .subscribe();
    this.messageService.add({severity: 'success', summary: 'Успешно', detail: 'Питомец добавлен'});
    this.router.navigate(['/collar-registration']).finally();
  }

  validate(detail: string) {
    this.messageService.add({severity: 'error', summary: 'Ошибка', detail: detail});
  }

  toLogin() {
    if (sessionStorage.getItem("actual-user-id") === null) {
      this.router.navigate(["/user-login"]).finally();
    }
  }
}
