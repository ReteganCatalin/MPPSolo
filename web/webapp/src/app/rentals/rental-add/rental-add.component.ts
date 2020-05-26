import {Component, OnInit} from '@angular/core';
import {RentalService} from "../shared/rental.service";
import {Location} from "@angular/common";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";

@Component({
  selector: 'app-rental-new',
  templateUrl: './rental-add.component.html',
  styleUrls: ['./rental-add.component.css']
})
export class RentalAddComponent implements OnInit {
  rentalForm:FormGroup
  constructor(private rentalService: RentalService,
              private location: Location,
              private router:Router
  ) {
  }

  ngOnInit(): void {

    this.rentalForm = new FormGroup({
      'clientID': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ]),
      'movieID': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ]),
      'day': new FormControl("", [
        Validators.required,
        Validators.min(1),
        Validators.max(31),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ]),
      'month': new FormControl("", [
        Validators.required,
        Validators.min(1),
        Validators.max(12),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ]),
      'year': new FormControl("", [
        Validators.required,
        Validators.min(2000),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
    });
  }
  get year() {
    return this.rentalForm.get('year');
  }
  get month() {
    return this.rentalForm.get('month');
  }

  get day() {
    return this.rentalForm.get('day');
  }

  get clientID()
  {
    return this.rentalForm.get('clientID')
  }

  get movieID()
  {
    return this.rentalForm.get('movieID')
  }

  delay(ms: number) {
    return new Promise( resolve => setTimeout(resolve, ms) );
  }

  saveRental(clientID: string, movieID: string, day: string,month:string,year:string) {
    console.log("saving rental", clientID, movieID, day,month,year);

    this.rentalService.saveRental({
      id: 0,
      clientID: Number(clientID),
      movieID: Number(movieID),
      day: Number(day),
      month: Number(month),
      year: Number(year)
    })
      .subscribe(rental => {console.log("saved rental: ", rental);this.router.navigate(["rentals"]);});
    //await delay(3000);
     // ...
  }
}
