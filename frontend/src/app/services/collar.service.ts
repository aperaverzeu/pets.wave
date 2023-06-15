import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, repeat } from 'rxjs';
import { Collar } from '../models/collar';
import { Health } from '../models/health';
import { Geo } from '../models/geo';

@Injectable({
  providedIn: 'root',
})
export class CollarService {
  constructor(private http: HttpClient) {}

  createCollar(collar: Collar): Observable<Collar> {
    return this.http.post('/api/data-to-kafka-service/collar/create', collar);
  }

  // eslint-disable-next-line class-methods-use-this
  getPetHealth(): Observable<Health> {
    return this.http
      .get<Health>('/api/query-service/collars/health/')
      .pipe(repeat({ delay: 3_000 }));
  }

  // eslint-disable-next-line class-methods-use-this
  getPetGeo(): Observable<Geo> {
    return this.http.get<Geo>('/api/query-service/collars/geo/').pipe(repeat({ delay: 300 }));
  }
}
