import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {v4 as uuid} from 'uuid';
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";
import {User} from "../../models/user";

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.scss'],
})
export class UserRegistrationComponent implements OnInit {
  name: string | undefined;
  username: string | undefined;
  password: string | undefined;
  usernameIsOccupied: boolean;
  users: User[];

  constructor(private userService: UserService,
              private messageService: MessageService,
              private router: Router) {
    this.usernameIsOccupied = false;
    this.users = [];
  }

  ngOnInit() {
    setTimeout(() => {
      this.userService.getUsers()
        .subscribe(users => users.forEach(user => this.users.push(user)))
    }, 2000);
  }

  onclick() {
    this.usernameIsOccupied = false;

    if ((this.name === "" || this.username === "" || this.password === "") ||
      (this.name === undefined || this.username === undefined || this.password === undefined) ||
      (this.name === null || this.username === null || this.password === null)) {
      this.validate('Все формы должны быть зполнены');
      return;
    }

    if (this.username.length <= 4 || this.username.length >= 20) {
      this.validate('Имя пользователя должно быть больше 4 и меньше 21 символов');
      return;
    }

    if (!(/^[A-Za-z][A-Za-z0-9_]{4,29}$/g.test(this.username))) {
      this.validate('Имя пользователя введено неверно');
      return;
    }

    if (!(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{6,20}$/g.test(this.password))) {
      console.log(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{6,20}$/g.test(this.password));
      this.validate('Пароль недостаточно надёжен');
      return;
    }

    this.users.forEach(user => {
      if (user.username == this.username) {
        this.usernameIsOccupied = true;
      }
    });

    if (this.usernameIsOccupied) {
      this.validate('Имя пользователя уже занятно');
      return;
    }

    const userId = uuid();
    sessionStorage.setItem("actual-user-id", userId);
    sessionStorage.setItem("is-authenticated", "false");

    this.userService
      .createUser({
        userId: userId,
        name: this.name,
        username: this.username,
        password: this.password
      })
      .subscribe();
    this.messageService.add({severity: 'success', summary: 'Успешно', detail: 'Пользователь зарегистрирован'});

    /*
      TODO add proclamation that all the data are on user findmends
     */

    this.router.navigate(['/pet-registration']).finally();
  }

  validate(detail: string) {
    this.messageService.add({severity: 'error', summary: 'Ошибка', detail: detail});
  }
}
