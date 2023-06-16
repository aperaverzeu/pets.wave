import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService, MessageService } from 'primeng/api';
import { User } from '../../models/user';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-user-settings',
  templateUrl: './user-settings.component.html',
  styleUrls: ['./user-settings.component.scss'],
})
export class UserSettingsComponent {
  user: User;

  constructor(
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private userService: UserService,
    private router: Router,
  ) {
    this.user = JSON.parse(sessionStorage.getItem('actual-user')!);
  }

  toUpdateUser() {
    this.router.navigate(['/user-update']).finally();
  }

  toDeleteUser() {
    this.confirmationService.confirm({
      message: '',
      header: 'Вы уверены?',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.userService
          .deleteUser({
            userId: this.user.id,
            name: this.user.name,
            username: this.user.username,
            password: this.user.password,
          })
          .subscribe({
            next: (value) => {
              this.messageService.add({
                severity: 'info',
                summary: 'Успех',
                detail: 'Пользователь удалён',
              });
              this.router.navigate(['/user-login']).finally(() => console.log(value));
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

  toLogin() {
    this.router.navigate(['/user-login']).finally();
  }
}
