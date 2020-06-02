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

  currentRole: string = "NONE" ;

  constructor(
    private http: HttpClient) {
  }

  public login(username: string, password: string): Observable<boolean> {

    //@ts-ignore
    return this.http.post(this.url + '?username=' + username + '&password=' + password, {}, this.httpOptions)
      .pipe(
        map(result => {
          this.getCurrentRole().subscribe(credentialsLogin => this.currentRole = credentialsLogin);
        })
      );
  }
  public logout(): Observable<any> {
    console.log("here")
    //@ts-ignore
    return this.http.post("http://localhost:8082/logout", {}, this.httpOptions).subscribe();
  }

  getCurrentRole(): Observable<string> {
    return this.http.get("http://localhost:8082/api/role", {withCredentials: true})
      .pipe(
        map(response => response["role"])
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

