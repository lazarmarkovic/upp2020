import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {TaskListComponent} from './components/task-list/task-list.component';
import {TaskComponent} from './components/task/task.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'tasks', component: TaskListComponent},
  {path: 'task/:id', component: TaskComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
