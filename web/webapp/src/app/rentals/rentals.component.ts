import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-rentals',
  templateUrl: './rentals.component.html',
  styleUrls: ['./rentals.component.css']
})
export class RentalsComponent  {

  constructor(private router: Router) {
  }

  addNewRental() {
    console.log("add new rental button clicked ");

    this.router.navigate(["rental/add"]);
  }

  filterRentals()
  {
    this.router.navigate(["rental/filter"])
  }

}
