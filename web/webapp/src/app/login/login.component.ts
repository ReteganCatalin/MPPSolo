import { Component, OnInit } from '@angular/core';
import {LoginService} from '../login/LoginService/LoginService';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  name: string;
  password: string;
  badLogin = false;

  constructor(private loginService: LoginService, private router: Router) { }

  ngOnInit(): void {
  }

  login() {
    this.loginService.login(this.name, this.password).subscribe(
      result => this.router.navigateByUrl('/home')
    );
  }
}
