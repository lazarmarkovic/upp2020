import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private httpClient: HttpClient) { }

  getTaskList(): Observable<any> {
    return this.httpClient.get('http://localhost:8080/tasks');
  }

  getTaskForm(taskId: string | undefined): Observable<any> {
    return this.httpClient.get('http://localhost:8080/tasks/' + taskId + '/form');
  }

  submit(data: any, taskId: string): Observable<any> {
    return this.httpClient.post('http://localhost:8080/tasks/submit/' + taskId, data);
  }
}
