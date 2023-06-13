import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, retry} from "rxjs";
import {User} from "../models/user";
import {UserRequest} from "../models/user-request";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) {
  }

  getUser(userId: string | null): Observable<User> {
    return this.http.get<User>('/api/query-service/users/' + userId)
      .pipe(retry(2));
  }

  getUserByUsername(username: string | null) {
    return this.http.get<User>('/api/query-service/users/username/' + username);
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>('/api/query-service/users/');
  }

  createUser(user: UserRequest): Observable<UserRequest> {
    return this.http.post<UserRequest>('/api/data-to-kafka-service/user/create', user);
  }
}
