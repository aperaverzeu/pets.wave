import {Component} from '@angular/core';
import {UserService} from "../../services/user.service";
import {v4 as uuid} from 'uuid';
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.scss'],
  // providers: [MessageService]
})
export class UserRegistrationComponent {
  name: string | undefined;
  username: string | undefined;
  password: string | undefined;

  constructor(private userService: UserService,
              private messageService: MessageService,
              private router: Router) {
  }

  onclick($event: MouseEvent) {
    if ((this.name === "" || this.username === "" || this.password === "") ||
      (this.name === undefined || this.username === undefined || this.password === undefined) ||
      (this.name === null || this.username === null || this.password === null)) {
      this.validate('Все формы должны быть зполнены');
      return;
    }

    let id = uuid();
    localStorage.setItem("actual-user-id", id)

    // get all users
    // find unique username
    // proceed or error

    if (this.username == 'les') {
      this.validate('Имя пользователя уже занятно');
      return;
    }

    this.userService
      .createUser({
        userId: id,
        name: this.name,
        username: this.username,
        password: this.password
      })
      .subscribe()
    this.router.navigate(['/pet-registration']).finally();
  }

  validate(detail: string) {
    this.messageService.add({severity: 'error', summary: 'Ошибка', detail: detail});
  }
}
