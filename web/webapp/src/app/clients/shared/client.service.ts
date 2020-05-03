import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Client} from "./client.model";
import {map} from "rxjs/operators";
import {Movie} from "../../movies/shared/movie.model";


@Injectable()
export class ClientService {
  private clientsUrl = 'http://localhost:8082/api/clients';

  constructor(private httpClient: HttpClient) {
  }

  getClients(): Observable<Client[]> {
    return this.httpClient
      .get<Array<Client>>(this.clientsUrl);
  }

  filterClients(name:string): Observable<Client[]>
  {
    let urlFilter='http://localhost:8082/api/filterClients/'
    const url = urlFilter.concat(name);
    console.log(url);
    return this.httpClient
      .get<Array<Client>>(url);
  }

  getClient(id: number): Observable<Client> {
    return this.getClients()
      .pipe(
        map(clients => clients.find(client => client.id === id))
      );
  }

  saveClient(client: Client): Observable<Client> {
    console.log("saveClient", client);

    return this.httpClient
      .post<Client>(this.clientsUrl, client);
  }

  update(client): Observable<Client> {
    const url = `${this.clientsUrl}/${client.id}`;
    return this.httpClient
      .put<Client>(url, client);
  }

  deleteClient(id: number): Observable<any> {
    const url = `${this.clientsUrl}/${id}`;
    return this.httpClient
      .delete(url);
  }

}
