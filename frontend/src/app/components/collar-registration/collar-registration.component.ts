import { Component } from '@angular/core';
import {CollarService} from "../../services/collar.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-collar-registration',
  templateUrl: './collar-registration.component.html',
  styleUrls: ['./collar-registration.component.scss'],
  providers: [MessageService]
})
export class CollarRegistrationComponent {
  collarId: string | undefined;
  petId: string | undefined;

  constructor(private collarService: CollarService,
              private messageService: MessageService,
              private router: Router) {
    this.collarId = localStorage.getItem("actual-collar-id")?.toString();
    this.petId = localStorage.getItem("actual-pet-id")?.toString();
  }

  onclick($event: MouseEvent) {
    if (this.collarId === "" || this.collarId === null || this.collarId === undefined) {
      this.validate('Все поля должны быть заполнены');
      return;
    }

    if (this.collarId != localStorage.getItem("actual-collar-id")) {
      this.validate('Укажите существующий ошейник');
      return;
    }

    this.collarService.createCollar({
      collarId: this.collarId,
      model: "Model A",
      petId: this.petId
    }).subscribe();
    this.router.navigate(["/user-login"]).finally();
  }

  validate(detail: string) {
    this.messageService.add({severity: 'error', summary: 'Ошибка', detail: detail});
  }
}
