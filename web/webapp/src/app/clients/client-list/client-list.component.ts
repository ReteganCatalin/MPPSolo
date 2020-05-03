import {Component, OnInit} from '@angular/core';
import {Client} from "../shared/client.model";
import {ClientService} from "../shared/client.service";
import {Router} from "@angular/router";


@Component({
  moduleId: module.id,
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css'],
})
export class ClientListComponent implements OnInit {
  errorMessage: string;
  clients: Array<Client>;
  selectedClient: Client;

  constructor(private clientService: ClientService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getClients();
  }

  getClients() {
    this.clientService.getClients()
      .subscribe(
        clients => this.clients = clients,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(client: Client): void {
    this.selectedClient = client;
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

