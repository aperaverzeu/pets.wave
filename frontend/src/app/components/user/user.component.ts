import { Component, Input } from "@angular/core";
import { IProduct } from "../../models/product";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html'
})

export class UserComponent {
  @Input() product: IProduct
}
