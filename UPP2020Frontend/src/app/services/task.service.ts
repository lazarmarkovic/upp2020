import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  constructor(private httpClient: HttpClient) { }

  getTaskList(): Observable<any> {
    return this.httpClient.get('/tasks');
  }

  getTaskForm(taskId: string | undefined): Observable<any> {
    return this.httpClient.get('/tasks/' + taskId + '/form');
  }

  submit(data: any, taskId: string): Observable<any> {
    return this.httpClient.post('/tasks/submit/' + taskId, data);
  }

  uploadFile(taskId: string, data: FormData): Observable<any>{
    return this.httpClient.post('/tasks/' + taskId + '/upload', data);
  }
}
