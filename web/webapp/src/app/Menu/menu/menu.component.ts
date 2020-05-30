import { Component, OnInit } from '@angular/core';
import {LoginService} from "../../login/LoginService/LoginService";
import {Router} from "@angular/router";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {
  title = 'Rental Shop';
  shopkeeper_role=false;
  constructor(private loginService: LoginService,private router:Router ) { }

  ngOnInit(): void {
    this.loginService.getCurrentRole().subscribe(result => {
      console.log(result);
      this.shopkeeper_role = result === 'ROLE_SHOPKEEPER';
    })
  }

  logout()
  {
    console.log("here dsds")
    this.loginService.logout();
    this.router.navigate(["login"]);
  }



}
