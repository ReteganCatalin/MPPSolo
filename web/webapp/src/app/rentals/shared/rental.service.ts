import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Rental} from "./rental.model";
import {map} from "rxjs/operators";


@Injectable()
export class RentalService {
  private rentalsUrl = 'http://localhost:8082/api/rentals';

  constructor(private httpClient: HttpClient) {
  }

  getRentals(): Observable<Rental[]> {
    return this.httpClient
      .get<Array<Rental>>(this.rentalsUrl);
  }

  getRental(id: number): Observable<Rental> {
    return this.getRentals()
      .pipe(
        map(rentals => rentals.find(rental => rental.id === id))
      );
  }

  saveRental(rental: Rental): Observable<Rental> {
    console.log("saveRental", rental);

    return this.httpClient
      .post<Rental>(this.rentalsUrl, rental);
  }

  filterRentals(title:string): Observable<Rental[]>
  {
    let urlFilter='http://localhost:8082/api/filterRentals/'
    const url = urlFilter.concat(title);
    console.log(url);
    return this.httpClient
      .get<Array<Rental>>(url);
  }

  update(rental): Observable<Rental> {
    const url = `${this.rentalsUrl}`;
    return this.httpClient
      .put<Rental>(url, rental);
  }

  deleteRental(id: number): Observable<any> {
    const url = `${this.rentalsUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }

}
