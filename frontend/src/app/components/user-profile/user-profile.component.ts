import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {Router} from "@angular/router";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user: User | undefined;

  constructor(private userService: UserService,
              private messageService: MessageService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.toLogin();

    this.userService.getUser(sessionStorage.getItem("actual-user-id"))
      .subscribe((data: User) => this.user = {
          id: data.id,
          name: data.name,
          username: data.username,
          password: data.password
        }
      );
  }

  onclick() {
    this.router.navigate(["/pets-profile"]).finally();
  }

  toSettings() {
    this.router.navigate(["/user-settings"]).finally();
  }

  toLogin() {
    if (sessionStorage.getItem("actual-user-id") === null) {
      this.router.navigate(["/user-login"]).finally();
    }
  }
}
