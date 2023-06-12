import { Component } from '@angular/core';
import {UserService} from "../../services/user.service";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss'],
  providers: [MessageService]
})
export class UserLoginComponent {
  username: string | undefined;
  password: string | undefined;

  constructor(private userService: UserService,
              private messageService: MessageService,
              private router: Router) {
  }

  onclick($event: MouseEvent) {
    if ((this.username === "" || this.username === null || this.username === undefined) ||
      (this.password === "" || this.password === null || this.password === undefined)) {
      this.validate('Все поля должны быть заполнены');
      return;
    }

    // if don't find, then error message


    // this.userService
    //   .createUser({
    //     userId: id,
    //     username: this.username,
    //     password: this.password
    //   })
    //   .subscribe()
    this.router.navigate(['/user-profile']).finally();
  }

  validate(detail: string) {
    this.messageService.add({severity: 'error', summary: 'Ошибка', detail: detail});
  }
}
