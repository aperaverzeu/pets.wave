import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ButtonModule} from "primeng/button";
import {CardModule} from "primeng/card";
import {PasswordModule} from "primeng/password";
import {InputTextModule} from "primeng/inputtext";
import {StyleClassModule} from "primeng/styleclass";
import {PetRegistrationComponent} from './components/pet-registration/pet-registration.component';
import {UserRegistrationComponent} from './components/user-registration/user-registration.component';
import {DropdownModule} from "primeng/dropdown";
import {InputNumberModule} from "primeng/inputnumber";
import { CollarRegistrationComponent } from './components/collar-registration/collar-registration.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import {AvatarModule} from "primeng/avatar";
import {ImageModule} from "primeng/image";
import { PetProfileComponent } from './components/pet-profile/pet-profile.component';
import { PetsProfileComponent } from './components/pets-profile/pets-profile.component';


@NgModule({
  declarations: [
    AppComponent,
    PetRegistrationComponent,
    UserRegistrationComponent,
    CollarRegistrationComponent,
    UserProfileComponent,
    PetProfileComponent,
    PetsProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    BrowserAnimationsModule,
    ButtonModule,
    CardModule,
    PasswordModule,
    InputTextModule,
    StyleClassModule,
    DropdownModule,
    InputNumberModule,
    AvatarModule,
    ImageModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
