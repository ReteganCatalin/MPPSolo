import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ClientsComponent} from "./clients/clients.component";
import {ClientDetailComponent} from "./clients/client-detail/client-detail.component";
import {ClientAddComponent} from "./clients/client-add/client-add.component";
import {MoviesComponent} from "./movies/movies.component";
import {MovieDetailComponent} from "./movies/movie-detail/movie-detail.component";
import {MovieAddComponent} from "./movies/movie-add/movie-add.component";
import {MovieFilterComponent} from "./movies/movie-filter/movie-filter.component";
import {RentalsComponent} from "./rentals/rentals.component";
import {RentalDetailComponent} from "./rentals/rental-detail/rental-detail.component";
import {RentalAddComponent} from "./rentals/rental-add/rental-add.component";
import {RentalFilterComponent} from "./rentals/rental-filter/rental-filter.component";
import {ClientFilterComponent} from "./clients/client-filter/client-filter.component";



const routes: Routes = [
  {path: 'clients', component: ClientsComponent},
  {path: 'client/detail/:id', component: ClientDetailComponent},
  {path: 'client/add', component: ClientAddComponent},
  {path: 'client/filter', component: ClientFilterComponent},
  {path: 'movies', component: MoviesComponent},
  {path: 'movie/detail/:id', component: MovieDetailComponent},
  {path: 'movie/add', component: MovieAddComponent},
  {path: 'movie/filter', component: MovieFilterComponent},
  {path: 'rentals', component: RentalsComponent},
  {path: 'rental/detail/:id', component: RentalDetailComponent},
  {path: 'rental/add', component: RentalAddComponent},
  {path: 'rental/filter', component: RentalFilterComponent},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
