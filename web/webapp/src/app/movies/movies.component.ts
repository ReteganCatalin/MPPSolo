import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";

@Component({
  moduleId: module.id,
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.css']
})
export class MoviesComponent  {

  constructor(private router: Router) {
  }

  addNewMovie() {
    console.log("add new movie button clicked ");

    this.router.navigate(["movie/add"]);
  }

  filterMovies()
  {
    this.router.navigate(["movie/filter"])
  }
  sortMovies()
  {
    this.router.navigate(["movie/sort"])
  }
  paginatedMovies()
  {
    this.router.navigate(["movie/paginated"])
  }


}
