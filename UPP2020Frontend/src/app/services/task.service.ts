import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private httpClient: HttpClient) { }

  getTaskList(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/user/tasks');
  }

  getTaskForm(taskId: string | undefined): Observable<any> {
    return this.httpClient.get('http://localhost:8080/task/' + taskId + '/form');
  }
}
