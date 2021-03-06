import {Injectable} from '@angular/core';

import {HttpClient, HttpHeaders} from "@angular/common/http";

import {Observable} from "rxjs";
import {Rental} from "./rental.model";
import {map} from "rxjs/operators";
import {Movie} from "../../movies/shared/movie.model";
import {Sort} from "../../clients/shared/sort";


@Injectable()
export class RentalService {
  private rentalsUrl = 'http://localhost:8082/api/rentals';
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'}), withCredentials: true,
  };

  constructor(private httpClient: HttpClient) {
  }

  getRentals(): Observable<Rental[]> {
    //@ts-ignore
    return this.httpClient
      .get<Array<Rental>>(this.rentalsUrl,{withCredentials:true});
  }

  getRental(id: number): Observable<Rental> {
    return this.getRentals()
      .pipe(
        map(rentals => rentals.find(rental => rental.id === id))
      );
  }

  saveRental(rental: Rental): Observable<Rental> {
    console.log("saveRental", rental);
    //@ts-ignore
    return this.httpClient
      .post<Rental>(this.rentalsUrl, rental,{withCredentials:true});
  }

  filterRentals(year:string): Observable<Rental[]>
  {
    let urlFilter=`http://localhost:8082/api/filterRentals/${year}`
    return this.httpClient
      .get<Array<Rental>>(urlFilter);
  }
  getPaginatedRentals(pageNo:string,size:string): Observable<Rental[]>
  {
    let urlPage=`http://localhost:8082/api/rentals/get-page/pageno=${pageNo},size=${size}`;

    return this.httpClient
      .get<Array<Rental>>(urlPage);
  }

  sortRentals(sort:Sort): Observable<Rental[]>
  {
    let urlSort='http://localhost:8082/api/sortRentals'
    return this.httpClient
      .post<Array<Rental>>(urlSort,sort);
  }

  update(rental): Observable<Rental> {
    const url = `${this.rentalsUrl}`;
    return this.httpClient
      .put<Rental>(url, rental,{withCredentials:true});
  }

  deleteRental(id: number): Observable<any> {
    const url = `${this.rentalsUrl}/${id}`;
    return this.httpClient
      .delete(url,{withCredentials:true});
  }

}
