import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../services/auth.service';
import {ToastrService} from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm = new FormGroup({
    username: new FormControl(null, [Validators.required, Validators.minLength(4)]),
    password: new FormControl(null, [Validators.required, Validators.minLength(5)]),
  });

  constructor(
    private authService: AuthService,
    private tService: ToastrService) {
  }

  ngOnInit(): void {
  }

  onLogin(): void {
    if (this.loginForm?.valid) {
      this.authService
        .login(this.loginForm.value.username, this.loginForm.value.password)
        .subscribe(
          (response: any) => {
            sessionStorage.setItem('accessToken', response.accessToken);
            this.tService.success('User successfully logged in.', 'Success');
          },
          err => {
            if (err.status === 400) {
              console.log('Errrrorrr');
              this.tService.warning('Error', 'Warning');
              this.loginForm.patchValue({
                username: '',
                password: ''
              });
              this.loginForm.markAsPristine();
              this.loginForm.markAsUntouched();
            }
            else {
              console.log(err);
            }
          }
        );
    }
  }

}
