import {Component, OnInit} from '@angular/core';
import {RentalService} from "../shared/rental.service";
import {Location} from "@angular/common";
import {Rental} from "../shared/rental.model";
import {Sort} from "../shared/sort";
import {Router} from "@angular/router";
import {SortObject} from "../shared/SortObject";

@Component({
  selector: 'app-rental-sort',
  templateUrl: './rental-sort.component.html',
  styleUrls: ['./rental-sort.component.css']
})
export class RentalSortComponent implements OnInit {

  errorMessage: string;
  rentals: Array<Rental>;
  selectedRental: Rental;
  selectedDirection: string;
  selectedColumn: string;
  sort: Sort;
  private sorting: SortObject;
  constructor(private rentalService: RentalService,
              private location: Location,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    this.rentalService.getRentals();
    this.sort=new Sort();
    this.sort.sort=new Array<SortObject>();


  }
  sortBuild()
  {
    this.sorting=new SortObject();
    this.sorting.column=this.selectedColumn;
    this.sorting.direction=this.selectedDirection;
    this.sort.sort.push(this.sorting);

  }

  sortDelete()
  {
    this.sort.sort=new Array<SortObject>();

  }
  sortRentals() {
    console.log("Sort: ", this.sort)
    this.rentalService.sortRentals(this.sort).subscribe(
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
