import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  runRegistration(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/users/start-author-registration');
  }

}
