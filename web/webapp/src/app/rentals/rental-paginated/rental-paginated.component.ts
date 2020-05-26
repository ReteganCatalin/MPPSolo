import {Component, OnInit} from '@angular/core';
import {Rental} from "../shared/rental.model";
import {RentalService} from "../shared/rental.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {SortObject} from "../shared/SortObject";
import {Sort} from "../shared/sort";


@Component({
  moduleId: module.id,
  selector: 'app-rental-new',
  templateUrl: './rental-paginated.component.html',
  styleUrls: ['./rental-paginated.component.css'],
})
export class RentalPaginatedComponent implements OnInit {
  errorMessage: string;
  rentals: Array<Rental>;
  selectedRental: Rental;
  selectedDirection: string;
  selectedColumn: string;
  sort: Sort;
  private sorting: SortObject;
  constructor(private rentalService: RentalService,
              private location: Location,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getRentalsPaginated('0','5');
    this.sort=new Sort();
    this.sort.sort=new Array<SortObject>();
  }

  getRentalsPaginated(PageNo:string,Size:string) {

    this.rentalService.getPaginatedRentals(PageNo,Size)
      .subscribe(
        rentals => this.rentals = rentals,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(rental: Rental): void {
    this.selectedRental = rental;
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

    let sorter=this.sort.sort;
    this.rentals=this.rentals.sort((a,b)=> {
        let sortCond=sorter.map(cond => {
            let value = 0;
            switch (cond.column) {
              case'clientID': {
                value = a.clientID - b.clientID;
                break;
              }
              case ('movieID') : {
                value = a.movieID - b.movieID;
                break;

              }
              case('day') : {
                value = a.day - b.day;
                break;
              }
              case('month') : {
                value = a.month - b.month;
                break;
              }
              case('yearOfRelease') : {
                value = a.year - b.year
                break;
              }
              case('id') : {
                value = a.id - b.id;
                break;
              }
            }
            if (cond.direction == "Desc") {
              value *= -1;
            }
            return value;
          }
        ).filter(val=>val!=0);
        if(sortCond.length==0)
        {
          return 0
        }
        else
        {
          return sortCond[0];
        }
      }
    );


  }

  filterBy(year: string) {

    this.rentals=this.rentals.filter(a=>a.year==+year);
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

