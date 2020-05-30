import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable, of} from 'rxjs';

import {catchError, map, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/x-www-form-urlencoded'}),
    withCredentials: true
  };
  private url = 'http://localhost:8082/login';

  constructor(
    private http: HttpClient) {
  }

  public login(username: string, password: string): Observable<string> {
    //console.log(this.url + "?username=" + username + "&password=" + password)
    return this.http.post<Observable<string>>(this.url + "?username=" + username + "&password=" + password,{observe:'response'}, this.httpOptions)
      .pipe(
        map(result => {
          let cookie = result['Set-Cookie'];
          console.log(cookie);
          cookie = cookie.split('=')[1];
          console.log(cookie);
          return cookie;
        })
      );
  }

  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
}
