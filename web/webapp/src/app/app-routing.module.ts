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
import {ClientSortComponent} from "./clients/client-sort/client-sort.component";
import {MovieSortComponent} from "./movies/movie-sort/movie-sort.component";
import {ClientPaginatedComponent} from "./clients/client-paginated/client-paginated.component";
import {MoviePaginatedComponent} from "./movies/movie-paginated/movie-paginated.component";
import {RentalSortComponent} from "./rentals/rental-sort/rental-sort.component";
import {RentalPaginatedComponent} from "./rentals/rental-paginated/rental-paginated.component";
import {LoginComponent} from "./login/login.component";
import {MenuComponent} from "./Menu/menu/menu.component";
import {AppComponent} from "./app.component";
import {BasicGuard} from "./basicGuard";
import {ShopKeeperGuard} from "./shopKeeperGuard";


const routes: Routes = [
  {path: 'clients', component: ClientsComponent, canActivate:[BasicGuard]},
  {path: 'client/detail/:id', component: ClientDetailComponent, canActivate:[BasicGuard]},
  {path: 'client/add', component: ClientAddComponent, canActivate:[BasicGuard]},
  {path: 'client/filter', component: ClientFilterComponent, canActivate:[BasicGuard]},
  {path: 'client/sort', component: ClientSortComponent, canActivate:[BasicGuard]},
  {path: 'client/paginated', component: ClientPaginatedComponent, canActivate:[BasicGuard]},
  {path: 'movies', component: MoviesComponent, canActivate:[BasicGuard]},
  {path: 'movie/detail/:id', component: MovieDetailComponent, canActivate:[BasicGuard]},
  {path: 'movie/add', component: MovieAddComponent, canActivate:[BasicGuard]},
  {path: 'movie/filter', component: MovieFilterComponent, canActivate:[BasicGuard]},
  {path: 'movie/sort', component:MovieSortComponent, canActivate:[BasicGuard]},
  {path: 'movie/paginated', component: MoviePaginatedComponent, canActivate:[BasicGuard]},
  {path: 'rentals', component: RentalsComponent, canActivate:[ShopKeeperGuard]},
  {path: 'rental/detail/:id', component: RentalDetailComponent, canActivate:[ShopKeeperGuard]},
  {path: 'rental/add', component: RentalAddComponent, canActivate:[ShopKeeperGuard]},
  {path: 'rental/filter', component: RentalFilterComponent, canActivate:[ShopKeeperGuard]},
  {path: 'rental/sort', component:RentalSortComponent, canActivate:[ShopKeeperGuard]},
  {path: 'rental/paginated', component: RentalPaginatedComponent, canActivate:[ShopKeeperGuard]},
  {path: 'rental/paginated', component: RentalPaginatedComponent, canActivate:[ShopKeeperGuard]},
  {path: 'home', component:MenuComponent},
  {path: 'login', component: LoginComponent},
  {path: '**', redirectTo:'login'}



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
