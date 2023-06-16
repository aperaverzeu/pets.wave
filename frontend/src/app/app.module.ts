import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { PasswordModule } from 'primeng/password';
import { InputTextModule } from 'primeng/inputtext';
import { StyleClassModule } from 'primeng/styleclass';
import { DropdownModule } from 'primeng/dropdown';
import { InputNumberModule } from 'primeng/inputnumber';
import { AvatarModule } from 'primeng/avatar';
import { ImageModule } from 'primeng/image';
import { ToastModule } from 'primeng/toast';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { PetRegistrationComponent } from './components/pet-registration/pet-registration.component';
import { UserRegistrationComponent } from './components/user-registration/user-registration.component';
import { CollarRegistrationComponent } from './components/collar-registration/collar-registration.component';
import { UserProfileComponent } from './components/user-profile/user-profile.component';
import { PetProfileComponent } from './components/pet-profile/pet-profile.component';
import { PetsProfileComponent } from './components/pets-profile/pets-profile.component';
import { UserSettingsComponent } from './components/user-settings/user-settings.component';
import { UserLoginComponent } from './components/user-login/user-login.component';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { PetGeoComponent } from './components/pet-geo/pet-geo.component';
import { PetHealthComponent } from './components/pet-health/pet-health.component';
import { PetSettingsComponent } from './components/pet-settings/pet-settings.component';
import { PetGeoMapComponent } from './components/pet-geo-map/pet-geo-map.component';
import { PetUpdateComponent } from './components/pet-update/pet-update.component';
import { UserUpdateComponent } from './components/user-update/user-update.component';

@NgModule({
  declarations: [
    AppComponent,
    PetRegistrationComponent,
    UserRegistrationComponent,
    CollarRegistrationComponent,
    UserProfileComponent,
    PetProfileComponent,
    PetsProfileComponent,
    UserSettingsComponent,
    UserLoginComponent,
    PetGeoComponent,
    PetHealthComponent,
    PetSettingsComponent,
    PetGeoMapComponent,
    PetUpdateComponent,
    UserUpdateComponent,
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
    ImageModule,
    ToastModule,
    ConfirmDialogModule,
  ],
  providers: [
    MessageService,
    { provide: MessageService, useClass: MessageService },
    ConfirmationService,
    { provide: ConfirmationService, useClass: ConfirmationService },
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
