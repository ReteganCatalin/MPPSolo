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
    this.router.navigate(["client/add"]);
  }

  filterClients()
  {
    this.router.navigate(["client/filter"])
  }

  sortClients()
  {
    this.router.navigate(["client/sort"])
  }
  paginatedClients()
  {
    this.router.navigate(["client/paginated"])
  }
  goBack()
  {
    this.router.navigate(["home"])
  }


}
