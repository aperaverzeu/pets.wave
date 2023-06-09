import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable, from, catchError, switchMap, of, retry} from "rxjs";
import {fromFetch} from 'rxjs/fetch';
import {User} from "../models/user";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) {
  }

  getUser(): Observable<User> {
    return this.http.get<User>('/api/query-service/users/b1aff029-fe32-46b0-8fd9-ee70975e2f28')
      .pipe(retry(2));
  }
}
