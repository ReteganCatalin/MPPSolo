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
          if(cond.column=='clientID')
          {
            value=a.clientID-b.clientID;
          }
          else if(cond.column=='movieID')
          {
              value = a.movieID-b.movieID;
          }
          else if(cond.column=='day')
          {
            value=a.day-b.day;
          }
          else if(cond.column=='month')
          {
            value=a.month-b.month;
          }
          else if(cond.column=='year')
          {
            value=a.year-b.year
          }
          else if(cond.column=='id')
          {
            value=a.id-b.id;
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

