import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PetRegistrationComponent} from "./components/pet-registration/pet-registration.component";
import {UserRegistrationComponent} from "./components/user-registration/user-registration.component";
import {CollarRegistrationComponent} from "./components/collar-registration/collar-registration.component";
import {UserProfileComponent} from "./components/user-profile/user-profile.component";
import {PetProfileComponent} from "./components/pet-profile/pet-profile.component";
import {PetsProfileComponent} from "./components/pets-profile/pets-profile.component";
import {UserSettingsComponent} from "./components/user-settings/user-settings.component";
import {UserLoginComponent} from "./components/user-login/user-login.component";

const routes: Routes = [
  {path: 'user-settings', component: UserSettingsComponent},
  {path: 'user-login', component: UserLoginComponent},
  {path: 'user-profile', component: UserProfileComponent},
  {path: 'pets-profile', component: PetsProfileComponent},
  {path: 'pet-profile', component: PetProfileComponent},
  {path: 'collar-registration', component: CollarRegistrationComponent},
  {path: 'user-registration', component: UserRegistrationComponent},
  {path: 'pet-registration', component: PetRegistrationComponent},
  {path: '', redirectTo: '/user-registration', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
