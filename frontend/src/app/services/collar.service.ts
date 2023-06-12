import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Collar} from "../models/collar";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class CollarService {

  constructor(private http: HttpClient) {
  }

  createCollar(collar: Collar): Observable<Collar> {
    return this.http.post('/api/data-to-kafka-service/collar/create', collar);
  }
}
