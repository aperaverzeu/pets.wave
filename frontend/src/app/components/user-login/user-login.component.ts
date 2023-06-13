import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss'],
})
export class UserLoginComponent implements OnInit {
  username: string | undefined;

  password: string | undefined;

  constructor(
    private userService: UserService,
    private messageService: MessageService,
    private router: Router,
  ) {}

  ngOnInit() {
    this.clearSessionStorage();
  }

  onclick() {
    if (
      this.username === '' ||
      this.username === null ||
      this.username === undefined ||
      this.password === '' ||
      this.password === null ||
      this.password === undefined
    ) {
      this.validate('Все поля должны быть заполнены');
      return;
    }

    this.userService.getUserByUsername(this.username).subscribe({
      next: (user) => {
        if (user == null) {
          this.validate('Пользотеля не существует');
          return;
        }
        if (user.username === this.username && user.password === this.password) {
          sessionStorage.setItem('actual-user-id', <string>user.id);
          sessionStorage.setItem('actual-user-name', <string>user.name);
          sessionStorage.setItem('actual-user-username', <string>user.username);
          sessionStorage.setItem('is-authenticated', 'true');
          this.toUserProfile();
        } else {
          this.validate('Неверное имя учётной записи или неверный пароль');
        }
      },
      error: () => {
        this.validate('Пользотеля не существует');
      },
      complete: () => {
        console.log('complete');
      },
    });
  }

  toUserProfile() {
    this.router.navigate(['/user-profile']).finally();
  }

  toRegistration() {
    this.router.navigate(['/user-registration']).finally();
  }

  validate(detail: string) {
    this.messageService.add({ severity: 'error', summary: 'Ошибка', detail });
  }

  // eslint-disable-next-line class-methods-use-this
  clearSessionStorage() {
    sessionStorage.clear();
  }
}
