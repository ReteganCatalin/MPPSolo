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
    withCredentials: true,observe:'response'
  };
  private url = 'http://localhost:8082/login';

  constructor(
    private http: HttpClient) {
  }

  public login(username: string, password: string): Observable<boolean> {
    // @ts-ignore
    return this.http.post(this.url + "?username=" + username + "&password=" + password, {}, this.httpOptions)
      .pipe(
        tap(result => console.log(result)),
        map(result => {
          return result["status"] === 200;
        })
      );
  }
  public logout(): Observable<any> {
    console.log("here")
    //@ts-ignore
    return this.http.post("http://localhost:8082/logout", {}, this.httpOptions).subscribe();
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

