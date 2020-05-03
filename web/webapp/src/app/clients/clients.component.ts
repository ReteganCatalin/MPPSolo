import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.css']
})
export class ClientsComponent  {

  constructor(private router: Router) {
  }

  addNewClient() {
    console.log("add new client button clicked ");

    this.router.navigate(["client/add"]);
  }

  filterClients()
  {
    this.router.navigate(["client/filter"])
  }

}
