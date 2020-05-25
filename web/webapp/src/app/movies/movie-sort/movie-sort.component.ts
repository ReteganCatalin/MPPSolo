import {Component, OnInit} from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Location} from "@angular/common";
import {Movie} from "../shared/movie.model";
import {Sort} from "../shared/sort";
import {Router} from "@angular/router";
import {SortObject} from "../shared/SortObject";

@Component({
  selector: 'app-movie-sort',
  templateUrl: './movie-sort.component.html',
  styleUrls: ['./movie-sort.component.css']
})
export class MovieSortComponent implements OnInit {

  errorMessage: string;
  movies: Array<Movie>;
  selectedMovie: Movie;
  selectedDirection: string;
  selectedColumn: string;
  sort: Sort;
  private sorting: SortObject;
  constructor(private movieService: MovieService,
              private location: Location,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    this.movieService.getMovies();
    this.sort=new Sort();
    this.sort.sort=new Array<SortObject>();


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
  sortMovies() {
    console.log("Sort: ", this.sort)
    this.movieService.sortMovies(this.sort).subscribe(
      movies => this.movies = movies,
      error => this.errorMessage = <any>error
    );

  }

  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
  }

  goBack(): void {

    this.location.back();
  }

  gotoDetail(): void {
    this.router.navigate(['/movie/detail', this.selectedMovie.id]);
  }

  deleteMovie(movie: Movie) {
    console.log("deleting movie: ", movie);

    this.movieService.deleteMovie(movie.id)
      .subscribe(_ => {
        console.log("movie deleted");

        this.movies = this.movies
          .filter(s => s.id !== movie.id);
      });
  }

}
