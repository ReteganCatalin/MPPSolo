import { Component } from '@angular/core';
import {LoginService} from "./login/LoginService/LoginService";
import {Router} from "@angular/router";
import { HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Rental Shop';
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
    //  const headers = new HttpHeaders({ Authorization: 'Basic ' + btoa(this.name + ':' + this.password) });
    // return this.http.get("http://localhost:8080/%22,%7Bheaders,responseType: 'text' as 'json'})
  }
}
