import {Component, OnInit} from '@angular/core';
import {CollarService} from "../../services/collar.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-collar-registration',
  templateUrl: './collar-registration.component.html',
  styleUrls: ['./collar-registration.component.scss']
})
export class CollarRegistrationComponent implements OnInit {
  collarId: string | undefined;
  petId: string | undefined;

  constructor(private collarService: CollarService,
              private messageService: MessageService,
              private router: Router) {
    this.collarId = sessionStorage.getItem("actual-collar-id")?.toString();
    this.petId = sessionStorage.getItem("actual-pet-id")?.toString();
  }

  ngOnInit() {
    this.toLogin();
  }

  onclick($event: MouseEvent) {
    if (this.collarId === "" || this.collarId === null || this.collarId === undefined) {
      this.validate('Все поля должны быть заполнены');
      return;
    }

    if (this.collarId != sessionStorage.getItem("actual-collar-id")) {
      this.validate('Укажите существующий ошейник');
      return;
    }

    this.collarService
      .createCollar({
        collarId: this.collarId,
        model: "Model A",
        petId: this.petId
      })
      .subscribe();
    this.messageService.add({severity: 'success', summary: 'Успешно', detail: 'Ошейник добавлен'});
    // here if auth
    if (sessionStorage.getItem("is-authenticated") == "true") {
      this.router.navigate(["/user-profile"]).finally();
    }  else {
      this.router.navigate(["/user-login"]).finally();
    }
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
