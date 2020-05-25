import {Component, OnInit} from '@angular/core';
import {ClientService} from "../shared/client.service";
import {Location} from "@angular/common";
import {Client} from "../shared/client.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-client-new',
  templateUrl: './client-filter.component.html',
  styleUrls: ['./client-filter.component.css']
})
export class ClientFilterComponent implements OnInit {

  errorMessage: string;
  clients: Array<Client>;
  selectedClient: Client;

  constructor(private clientService: ClientService,
              private location: Location,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    this.clientService.getClients().subscribe(
      clients=> this.clients=clients,
          error => this.errorMessage = <any>error
    );
  }

  filterClients(name: string) {
    console.log("filter clients", name);

    this.clientService.filterClients(name).subscribe(
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
