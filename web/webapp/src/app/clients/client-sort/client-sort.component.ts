import {Component, OnInit} from '@angular/core';
import {ClientService} from "../shared/client.service";
import {Location} from "@angular/common";
import {Client} from "../shared/client.model";
import {Sort} from "../shared/sort";
import {Router} from "@angular/router";
import {SortObject} from "../shared/SortObject";

@Component({
  selector: 'app-client-sort',
  templateUrl: './client-sort.component.html',
  styleUrls: ['./client-sort.component.css']
})
export class ClientSortComponent implements OnInit {

  errorMessage: string;
  clients: Array<Client>;
  selectedClient: Client;
  selectedDirection: string;
  selectedColumn: string;
  sort: Sort;
  private sorting: SortObject;
  constructor(private clientService: ClientService,
              private location: Location,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    this.clientService.getClients();
    this.sort=new Sort();
    this.sort.sort=new Array<SortObject>();


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
  sortClients() {
    console.log("Sort: ", this.sort)
    this.clientService.sortClients(this.sort).subscribe(
      clients => this.clients = clients,
      error => this.errorMessage = <any>error
    );

  }
  onSelect(client: Client): void {
    this.selectedClient = client;
  }

  goBack(): void {

    this.location.back();
  }

  gotoDetail(): void {
    this.router.navigate(['/client/detail', this.selectedClient.id]);
  }

  deleteClient(client: Client) {
    console.log("deleting client: ", client);

    this.clientService.deleteClient(client.id)
      .subscribe(_ => {
        console.log("client deleted");

        this.clients = this.clients
          .filter(s => s.id !== client.id);
      });
  }

}
