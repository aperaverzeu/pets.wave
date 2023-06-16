import { Component, OnInit } from '@angular/core';
import { MessageService } from 'primeng/api';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-user-update',
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.scss'],
})
export class UserUpdateComponent implements OnInit {
  name: string | undefined;

  username: string | undefined;

  password: string | undefined;

  usernameIsOccupied: boolean;

  users: User[];

  user: User;

  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private router: Router,
  ) {
    this.usernameIsOccupied = false;
    this.users = [];
    this.user = JSON.parse(sessionStorage.getItem('actual-user')!);
    this.name = this.user.name;
    this.username = this.user.username;
    this.password = this.user.password;
  }

  ngOnInit() {
    setTimeout(() => {
      this.userService
        .getUsers()
        .subscribe((users) => users.forEach((user) => this.users.push(user)));
    }, 1000);
  }

  onclick() {
    this.usernameIsOccupied = false;

    if (
      this.name === '' ||
      this.username === '' ||
      this.password === '' ||
      this.name === undefined ||
      this.username === undefined ||
      this.password === undefined ||
      this.name === null ||
      this.username === null ||
      this.password === null
    ) {
      this.validate('Все формы должны быть зполнены');
      return;
    }

    if (this.username.length <= 4 || this.username.length >= 20) {
      this.validate('Имя пользователя должно быть больше 4 и меньше 21 символов');
      return;
    }

    if (!/^[A-Za-z][A-Za-z0-9_]{4,29}$/g.test(this.username)) {
      this.validate('Имя пользователя введено неверно');
      return;
    }

    if (!/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{6,20}$/g.test(this.password)) {
      console.log(/(?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\W).{6,20}$/g.test(this.password));
      this.validate('Пароль недостаточно надёжен');
      return;
    }

    if (this.user.username !== this.username) {
      this.users.forEach((user) => {
        if (user.username === this.username) {
          this.usernameIsOccupied = true;
        }
      });
    }

    if (this.usernameIsOccupied) {
      this.validate('Имя пользователя уже занятно');
      return;
    }

    this.userService
      .updateUser({
        userId: sessionStorage.getItem('actual-user-id')!,
        name: this.name,
        username: this.username,
        password: this.password,
      })
      .subscribe({
        next: (value) => {
          this.messageService.add({
            severity: 'info',
            summary: 'Успешно',
            detail: 'Пользователь изменен',
          });
          this.router.navigate(['/user-profile']).finally(() => console.log(value));
        },
        error: (err) => console.error(err),
      });
  }

  validate(detail: string) {
    this.messageService.add({ severity: 'error', summary: 'Ошибка', detail });
  }
}
