import {Component, OnInit} from '@angular/core';
import {ClientService} from "../shared/client.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-client-new',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {

  constructor(private clientService: ClientService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  saveClient(firstName: string, lastName: string, age: string) {
    console.log("saving client", firstName, lastName, age);

    this.clientService.saveClient({
      id: 0,
      firstName,
      lastName,
      age: Number(age)
    })
      .subscribe(client => console.log("saved client: ", client));

    this.location.back(); // ...
  }
}
