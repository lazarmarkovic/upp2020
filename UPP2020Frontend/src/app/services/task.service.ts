import { Injectable } from '@angular/core';
import {HttpClient, HttpEvent, HttpRequest} from '@angular/common/http';
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

  submit(data: any, taskId: string, taskName: string): Observable<any> {
    if (taskName === 'review author by committee') {
      return this.httpClient.post('/tasks/submit-member-vote/' + taskId, data);
    } else {
      return this.httpClient.post('/tasks/submit/' + taskId, data);
    }
  }

  uploadFiles(taskId: string | undefined, files: any[]): Observable<any> {
    const formData = new FormData();

    // @ts-ignore
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < files.length; i++) {
      if (files) {
        formData.append('files', files[i]);
      }
    }
    // @ts-ignore
    return this.httpClient.post('/tasks/' + taskId + '/upload', formData);
  }
}
