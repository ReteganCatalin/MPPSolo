import {Component, OnInit} from '@angular/core';
import {RentalService} from "../shared/rental.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-rental-new',
  templateUrl: './rental-add.component.html',
  styleUrls: ['./rental-add.component.css']
})
export class RentalAddComponent implements OnInit {

  constructor(private rentalService: RentalService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
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
      .subscribe(rental => console.log("saved rental: ", rental));

    this.location.back(); // ...
  }
}
