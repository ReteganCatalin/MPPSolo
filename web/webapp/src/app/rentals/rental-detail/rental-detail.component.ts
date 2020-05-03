import {Component, Input, OnInit} from '@angular/core';
import {RentalService} from "../shared/rental.service";
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';
import {Rental} from "../shared/rental.model";

import {switchMap} from "rxjs/operators";


@Component({
  selector: 'app-rental-detail',
  templateUrl: './rental-detail.component.html',
  styleUrls: ['./rental-detail.component.css'],
})

export class RentalDetailComponent implements OnInit {

  @Input() rental: Rental;

  constructor(private rentalService: RentalService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.rentalService.getRental(+params['id'])))
      .subscribe(rental => this.rental = rental);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.rentalService.update(this.rental)
      .subscribe(_ => this.goBack());
  }
}
