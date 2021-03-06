import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {ToastrService} from 'ngx-toastr';
import {AuthService} from '../../services/auth.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  options: FormGroup;

  constructor(
    public router: Router,
    public activeRoute: ActivatedRoute,
    private formBuilder: FormBuilder,
    private userService: UserService,
    private authService: AuthService,
    private tService: ToastrService) {

    this.options = formBuilder.group({
      bottom: 0,
      fixed: true,
      top: 0
    });
  }

  ngOnInit(): void {
  }

  startAuthorRegistration(): void {
    this.userService
      .runAuthorRegistration()
      .subscribe(
        response => {
          this.tService.success('Author registration process started', 'Success');
        },
        err => {}
      );
  }

  startReaderRegistration(): void {
    this.userService
      .runReaderRegistration()
      .subscribe(
        response => {
          this.tService.success('Reader registration process started', 'Success');
        },
        err => {}
      );
  }

  startWorkPublishing(): void {
    this.userService
      .runWorkPublishing()
      .subscribe(
        response => {
          this.tService.success('Work publishing process started', 'Success');
        },
        err => {}
      );
  }

  logout(): void {
    sessionStorage.removeItem('accessToken');
    sessionStorage.removeItem('authUser');
  }

}
