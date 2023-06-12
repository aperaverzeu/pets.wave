import {Component, OnInit} from '@angular/core';
import {UserService} from "../../services/user.service";
import {User} from "../../models/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {
  user: User | undefined;

  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.toLogin();

    this.userService.getUser(localStorage.getItem("actual-user-id"))
      .subscribe((data: User) => this.user = {
          id: data.id,
          name: data.name,
          username: data.username,
          password: data.password
        }
      );
  }

  onclick($event: MouseEvent) {
    this.router.navigate(["/pets-profile"]).finally();
  }

  toSettings($event: MouseEvent) {
    this.router.navigate(["/user-settings"]).finally();
  }

  toLogin() {
    if (localStorage.getItem("actual-user-id") == null) {
      this.router.navigate(["/user-login"]).finally();
    }
  }
}
