import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule} from '@angular/forms';

import {AppComponent} from './app.component';
import {AppRoutingModule} from "./app-routing.module";
import { ClientsComponent } from './clients/clients.component';
import { ClientListComponent } from './clients/client-list/client-list.component';
import {ClientService} from "./clients/shared/client.service";
import {ClientAddComponent} from "./clients/client-add/client-add.component";
import {ClientDetailComponent} from "./clients/client-detail/client-detail.component";
import {MoviesComponent} from "./movies/movies.component";
import {MovieAddComponent} from "./movies/movie-add/movie-add.component";
import {MovieDetailComponent} from "./movies/movie-detail/movie-detail.component";
import {MovieService} from "./movies/shared/movie.service";
import {MovieListComponent} from "./movies/movie-list/movie-list.component";
import {MovieFilterComponent} from "./movies/movie-filter/movie-filter.component";
import {RentalService} from "./rentals/shared/rental.service";
import {RentalsComponent} from "./rentals/rentals.component";
import {RentalAddComponent} from "./rentals/rental-add/rental-add.component";
import {RentalDetailComponent} from "./rentals/rental-detail/rental-detail.component";
import {RentalFilterComponent} from "./rentals/rental-filter/rental-filter.component";
import {RentalListComponent} from "./rentals/rental-list/rental-list.component";
import {ClientFilterComponent} from "./clients/client-filter/client-filter.component";


@NgModule({
  declarations: [
    AppComponent,
    ClientsComponent,
    ClientListComponent,
    ClientAddComponent,
    ClientDetailComponent,
    ClientFilterComponent,
    MoviesComponent,
    MovieAddComponent,
    MovieDetailComponent,
    MovieAddComponent,
    MovieListComponent,
    MovieFilterComponent,
    RentalsComponent,
    RentalAddComponent,
    RentalDetailComponent,
    RentalFilterComponent,
    RentalListComponent



  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
  ],
  providers: [ClientService,MovieService,RentalService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
