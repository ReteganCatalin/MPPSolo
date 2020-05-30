import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {HttpClientModule} from '@angular/common/http';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

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
import {ClientSortComponent} from "./clients/client-sort/client-sort.component";
import {MovieSortComponent} from "./movies/movie-sort/movie-sort.component";
import {ClientPaginatedComponent} from "./clients/client-paginated/client-paginated.component";
import {MoviePaginatedComponent} from "./movies/movie-paginated/movie-paginated.component";
import {RentalSortComponent} from "./rentals/rental-sort/rental-sort.component";
import {RentalPaginatedComponent} from "./rentals/rental-paginated/rental-paginated.component";
import { MenuComponent } from './Menu/menu/menu.component';
import { LoginComponent } from './login/login.component';
import {LoginService} from './login/LoginService/LoginService';
import { NoopAnimationsModule } from '@angular/platform-browser/animations'


@NgModule({
  declarations: [
    AppComponent,
    ClientsComponent,
    ClientListComponent,
    ClientAddComponent,
    ClientDetailComponent,
    ClientFilterComponent,
    ClientSortComponent,
    ClientPaginatedComponent,
    MoviesComponent,
    MovieAddComponent,
    MovieDetailComponent,
    MovieAddComponent,
    MovieListComponent,
    MovieFilterComponent,
    MovieSortComponent,
    MoviePaginatedComponent,
    RentalsComponent,
    RentalAddComponent,
    RentalDetailComponent,
    RentalFilterComponent,
    RentalListComponent,
    RentalSortComponent,
    RentalPaginatedComponent,
    MenuComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    ReactiveFormsModule,
    NoopAnimationsModule,
  ],
  providers: [ClientService,MovieService,RentalService,LoginService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
