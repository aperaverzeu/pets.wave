import {Component} from '@angular/core';

@Component({
  selector: 'app-user-registration',
  templateUrl: './user-registration.component.html',
  styleUrls: ['./user-registration.component.scss']
})
export class UserRegistrationComponent {
  name: string | undefined;
  username: string | undefined;
  password: string | undefined;
}
