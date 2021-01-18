import { Component, OnInit, AfterViewInit, ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatTableDataSource} from '@angular/material/table';
import {TaskService} from '../../services/task.service';
import {Router} from '@angular/router';

export interface TaskElement {
  taskId: string;
  name: string;
  assignee: string;
  task: string;
  creationTime: Date;
}

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit, AfterViewInit {

  displayedColumns: string[] = ['name', 'assignee', 'task', 'creationTime', 'action'];
  dataSource = new MatTableDataSource<TaskElement>([]);

  @ViewChild(MatPaginator) paginator: MatPaginator | undefined;

  ngAfterViewInit(): void {
    // @ts-ignore
    this.dataSource.paginator = this.paginator;
  }

  constructor(
    private taskService: TaskService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.getTasks();
  }

  getTasks(): void {
    this.taskService
      .getTaskList()
      .subscribe(
        (response: TaskElement[]) => {
          this.dataSource = new MatTableDataSource<TaskElement>(response);
        },
        error => {
          console.log('Task load error:');
          console.log(error);
        }
      );
  }

  viewForm(taskId: string): void {
    this.router.navigate(['/task/' + taskId]);
  }

  refreshTable(): void {
    this.getTasks();
  }
}
