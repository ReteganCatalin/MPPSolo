import {Injectable} from '@angular/core';

import {HttpClient} from "@angular/common/http";

import {Observable} from "rxjs";
import {Client} from "./client.model";
import {Sort} from "./sort";
import {map} from "rxjs/operators";




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
    let urlFilter=`http://localhost:8082/api/filterClients/name=${name}`
    return this.httpClient
      .get<Array<Client>>(urlFilter);
  }

  filterClientsAge(age:string): Observable<Client[]>
  {
    let urlFilter=`http://localhost:8082/api/filterClients/age=${age}`
    return this.httpClient
      .get<Array<Client>>(urlFilter);
  }

  filterClientsFirstName(name:string): Observable<Client[]>
  {
    let urlFilter=`http://localhost:8082/api/filterClients/firstName=${name}`
    return this.httpClient
      .get<Array<Client>>(urlFilter);
  }

  sortClients(sort:Sort): Observable<Client[]>
  {
    let urlSort='http://localhost:8082/api/sortClients/'
    return this.httpClient
      .post<Array<Client>>(urlSort,sort);
  }

  getPaginatedClients(pageNo:string,size:string): Observable<Client[]>
  {
    let urlPage=`http://localhost:8082/api/clients/get-page/pageno=${pageNo},size=${size}`;
    console.log(urlPage);
    return this.httpClient
      .get<Array<Client>>(urlPage);
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
