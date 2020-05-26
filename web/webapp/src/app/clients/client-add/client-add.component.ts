import {Component, OnInit} from '@angular/core';
import {ClientService} from "../shared/client.service";
import { FormControl, FormGroup, Validators } from '@angular/forms';
import {Location} from "@angular/common";
import {Router} from "@angular/router";

@Component({
  selector: 'app-client-new',
  templateUrl: './client-add.component.html',
  styleUrls: ['./client-add.component.css']
})
export class ClientAddComponent implements OnInit {
  clientForm: FormGroup;
  constructor(private clientService: ClientService,
              private location: Location,
              private router:Router


  ) {
  }

  ngOnInit(): void {
    this.clientForm = new FormGroup({
      'firstName': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z]+$")
      ]),
      'lastName': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z]+$")
      ]),
      'age': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
    });
  }
  get age() {
    return this.clientForm.get('age');
  }
  get lastName() {
    return this.clientForm.get('lastName');
  }

  get firstName() {
    return this.clientForm.get('firstName');
  }

  saveClient(firstName: string, lastName: string, age: string) {
    console.log("saving client", firstName, lastName, age);

    this.clientService.saveClient({
      id: 0,
      firstName,
      lastName,
      age: Number(age)
    })
      .subscribe(client =>{ console.log("saved client: ", client);this.router.navigate(["clients"]);});
     // ...
  }
}
