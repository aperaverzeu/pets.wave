import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Pet} from "../models/pet";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PetService {

  constructor(private http: HttpClient) {
  }

  createPet(pet: Pet): Observable<Pet> {
    return this.http.post<Pet>('/api/data-to-kafka-service/pet/create', pet);
  }

  getAllPets(): Observable<Pet[]> {
    return this.http.get<Pet[]>('/api/query-service/pets/user/' + localStorage.getItem("actual-user-id"));
  }
}
