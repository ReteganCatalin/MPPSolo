import { Component, OnInit,NgModule } from '@angular/core';
import {LoginService} from '../login/LoginService/LoginService';

import {Router} from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import {MatFormFieldModule} from '@angular/material/form-field';

@NgModule({
  imports: [
    MatFormFieldModule,
    MatInputModule
  ]
})

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
      result => {
        // tslint:disable-next-line:triple-equals
        this.router.navigateByUrl('/home');
      }, (error) => console.log("Bad Login")
    );
  }
}
