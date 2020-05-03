import {Component, OnInit} from '@angular/core';
import {Rental} from "../shared/rental.model";
import {RentalService} from "../shared/rental.service";
import {Router} from "@angular/router";


@Component({
  moduleId: module.id,
  selector: 'app-rental-list',
  templateUrl: './rental-list.component.html',
  styleUrls: ['./rental-list.component.css'],
})
export class RentalListComponent implements OnInit {
  errorMessage: string;
  rentals: Array<Rental>;
  selectedRental: Rental;

  constructor(private rentalService: RentalService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getRentals();
  }

  getRentals() {
    this.rentalService.getRentals()
      .subscribe(
        rentals => this.rentals = rentals,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(rental: Rental): void {
    this.selectedRental = rental;
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

