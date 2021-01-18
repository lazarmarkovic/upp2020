import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {UserService} from '../../services/user.service';
import {ToastrService} from 'ngx-toastr';

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
      .runRegistration()
      .subscribe(
        response => {
          this.tService.success('Author registration process started', 'Success');
        },
        err => {}
      );
  }

}
