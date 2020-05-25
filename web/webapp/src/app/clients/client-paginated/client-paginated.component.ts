import {Component, OnInit} from '@angular/core';
import {Client} from "../shared/client.model";
import {ClientService} from "../shared/client.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {SortObject} from "../shared/SortObject";
import {Sort} from "../shared/sort";


@Component({
  moduleId: module.id,
  selector: 'app-client-new',
  templateUrl: './client-paginated.component.html',
  styleUrls: ['./client-paginated.component.css'],
})
export class ClientPaginatedComponent implements OnInit {
  errorMessage: string;
  clients: Array<Client>;
  selectedClient: Client;
  selectedDirection: string;
  selectedColumn: string;
  sort: Sort;
  private sorting: SortObject;
  constructor(private clientService: ClientService,
              private location: Location,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getClientsPaginated('0','5');
    this.sort=new Sort();
    this.sort.sort=new Array<SortObject>();
  }

  getClientsPaginated(PageNo:string,Size:string) {

    this.clientService.getPaginatedClients(PageNo,Size)
      .subscribe(
        clients => this.clients = clients,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(client: Client): void {
    this.selectedClient = client;
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

    let sorter=this.sort.sort;

    this.clients=this.clients.sort((a,b)=> {
        let sortCond=sorter.map(cond => {
            let value = 0;
            switch(cond.column) {
              case'firstName': {
                value = a.firstName.localeCompare(b.firstName)
                break;
              }
              case ('lastName') : {
                value = a.lastName.localeCompare(b.lastName)
                break;

              }
              case('age') : {
                value = a.age - b.age;
                break;
              }
              case('id') : {
                value = a.id - b.id;
                break;
              }
            }
            if (cond.direction == "Desc") {
              value *= -1;
            }
            return value;
          }
        ).filter(val=>val!=0);
        if(sortCond.length==0)
        {
          return 0
        }
        else
          {
            return sortCond[0];
          }
      }
    );
  }

  filterBy(firstName: string) {
    this.clients=this.clients.filter(a=>a.firstName.includes(firstName));
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

