import {Component} from '@angular/core';

@Component({
  selector: 'app-pet-profile',
  templateUrl: './pet-profile.component.html',
  styleUrls: ['./pet-profile.component.scss']
})
export class PetProfileComponent {
  name: string | undefined = "Маджонг";
  weight: string | undefined = "12 кг";
  height: string | undefined = "35 см";
  age: string | undefined = "3 мес";
}
