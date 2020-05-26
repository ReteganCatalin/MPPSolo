import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Movie} from "./movie.model";
import {map} from "rxjs/operators";
import {Sort} from "../../clients/shared/sort";



@Injectable()
export class MovieService {
  private moviesUrl = 'http://localhost:8082/api/movies';

  constructor(private httpClient: HttpClient) {
  }

  getMovies(): Observable<Movie[]> {
    return this.httpClient
      .get<Array<Movie>>(this.moviesUrl);
  }

  getMovie(id: number): Observable<Movie> {
    return this.getMovies()
      .pipe(
        map(movies => movies.find(movie => movie.id === id))
      );
  }

  saveMovie(movie: Movie): Observable<Movie> {
    console.log("saveMovie", movie);

    return this.httpClient
      .post<Movie>(this.moviesUrl, movie);
  }

  filterMovies(title:string): Observable<Movie[]>
  {
    let urlFilter=`http://localhost:8082/api/filterMovies/title=${title}`
    return this.httpClient
      .get<Array<Movie>>(urlFilter);
  }

  filterMoviesByDirector(director:string): Observable<Movie[]>
  {
    let urlFilter=`http://localhost:8082/api/filterMovies/director=${director}`
    return this.httpClient
      .get<Array<Movie>>(urlFilter);
  }

  filterMoviesByMainStar(mainStar:string): Observable<Movie[]>
  {
    let urlFilter=`http://localhost:8082/api/filterMovies/mainStar=${mainStar}`
    return this.httpClient
      .get<Array<Movie>>(urlFilter);
  }

  getPaginatedMovies(pageNo:string,size:string): Observable<Movie[]>
  {
    let urlPage=`http://localhost:8082/api/movies/get-page/pageno=${pageNo},size=${size}`;
    return this.httpClient
      .get<Array<Movie>>(urlPage);
  }

  sortMovies(sort:Sort): Observable<Movie[]>
  {
    let urlSort='http://localhost:8082/api/sortMovies'
    return this.httpClient
      .post<Array<Movie>>(urlSort,sort);
  }

  update(movie): Observable<Movie> {
    const url = `${this.moviesUrl}`;
    return this.httpClient
      .put<Movie>(url, movie);
  }

  deleteMovie(id: number): Observable<any> {
    const url = `${this.moviesUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }

}
