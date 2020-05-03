import {Component, Input, OnInit} from '@angular/core';
import {ClientService} from "../shared/client.service";
import {ActivatedRoute, Params} from "@angular/router";
import {Location} from '@angular/common';
import {Client} from "../shared/client.model";

import {switchMap} from "rxjs/operators";


@Component({
  selector: 'app-client-detail',
  templateUrl: './client-detail.component.html',
  styleUrls: ['./client-detail.component.css'],
})

export class ClientDetailComponent implements OnInit {

  @Input() client: Client;

  constructor(private clientService: ClientService,
              private route: ActivatedRoute,
              private location: Location) {
  }

  ngOnInit(): void {
    this.route.params
      .pipe(switchMap((params: Params) => this.clientService.getClient(+params['id'])))
      .subscribe(client => this.client = client);
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.clientService.update(this.client)
      .subscribe(_ => this.goBack());
  }
}
