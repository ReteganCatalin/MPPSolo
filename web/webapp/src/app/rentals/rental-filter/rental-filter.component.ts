import {Component, OnInit} from '@angular/core';
import {RentalService} from "../shared/rental.service";
import {Location} from "@angular/common";
import {Rental} from "../shared/rental.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-rental-new',
  templateUrl: './rental-filter.component.html',
  styleUrls: ['./rental-filter.component.css']
})
export class RentalFilterComponent implements OnInit {

  errorMessage: string;
  rentals: Array<Rental>;
  selectedRental: Rental;

  constructor(private rentalService: RentalService,
              private location: Location,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    this.filterRentals("");
  }

  filterRentals(year: string) {
    console.log("filter rentals", year);

    this.rentalService.filterRentals(year).subscribe(
      rentals => this.rentals = rentals,
      error => this.errorMessage = <any>error
    );

  }
  onSelect(rental: Rental): void {
    this.selectedRental = rental;
  }

  goBack(): void {

    this.location.back();
  }

  gotoDetail(): void {
    this.router.navigate(['/rental/detail', this.selectedRental.id]);
  }

  deleteRental(rental: Rental) {
    console.log("deleting rental: ", rental);

    this.rentalService.deleteRental(rental.id)
      .subscribe(_ => {
        console.log("rental deleted");

        this.rentals = this.rentals
          .filter(s => s.id !== rental.id);
      });
  }

}
