import { Component, OnInit } from '@angular/core';
import {LoginService} from "../../login/LoginService/LoginService";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  title = 'Rental Shop';
  constructor(private loginService: LoginService) { }

  ngOnInit(): void {

  }

  logout()
  {
    console.log("here dsds")
    this.loginService.logout();
  }



}
