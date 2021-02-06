import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  runAuthorRegistration(): Observable<any> {
    return this.httpClient.get('/users/start-author-registration');
  }

  runReaderRegistration(): Observable<any> {
    return this.httpClient.get('/users/start-reader-registration');
  }

  runWorkPublishing(): Observable<any> {
    return this.httpClient.get('/users/start-work-publishing');
  }

}
