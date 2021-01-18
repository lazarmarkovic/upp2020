import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private httpClient: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    const user = {
      username,
      password,
    };
    return this.httpClient.post('/login', user);
  }

  getAuthUser(): Observable<any> {
    return this.httpClient.get('/auth-user');
  }
}
