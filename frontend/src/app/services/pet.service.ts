import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Pet } from '../models/pet';
import { PetRequest } from '../models/pet-request';

@Injectable({
  providedIn: 'root',
})
export class PetService {
  constructor(private http: HttpClient) {}

  createPet(pet: PetRequest): Observable<PetRequest> {
    return this.http.post<PetRequest>('/api/data-to-kafka-service/pet/create', pet);
  }

  getPet(id: string | null) {
    return this.http.get<Pet>(`/api/query-service/pets/${id}`);
  }

  getAllPets(): Observable<Pet[]> {
    return this.http.get<Pet[]>(
      `/api/query-service/pets/user/${sessionStorage.getItem('actual-user-id')}`,
    );
  }
}
