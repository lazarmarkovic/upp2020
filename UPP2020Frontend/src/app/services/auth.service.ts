import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) { }

  // tslint:disable-next-line:typedef
  login(username: string, password: string) {
    const user = {
      username,
      password
    };
    return this.http.post('http://localhost:8080/login', user);
  }
}
