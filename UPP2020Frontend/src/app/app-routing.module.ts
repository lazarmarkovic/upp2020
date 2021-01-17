import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AppComponent} from './components/main/app.component';
import {TaskListComponent} from './components/task-list/task-list.component';

const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'tasks', component: TaskListComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
