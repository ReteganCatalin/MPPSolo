import {Component, OnInit} from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Location} from "@angular/common";
import {Movie} from "../shared/movie.model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-movie-new',
  templateUrl: './movie-filter.component.html',
  styleUrls: ['./movie-filter.component.css']
})
export class MovieFilterComponent implements OnInit {

  errorMessage: string;
  movies: Array<Movie>;
  selectedMovie: Movie;

  constructor(private movieService: MovieService,
              private location: Location,
              private router: Router
  ) {
  }

  ngOnInit(): void {
    this.movieService.getMovies().subscribe(
      movies=> this.movies=movies,
      error => this.errorMessage = <any>error
    );
  }

  filterMovies(title: string) {
    console.log("filter movies", title);

    this.movieService.filterMovies(title).subscribe(
      movies => this.movies = movies,
      error => this.errorMessage = <any>error
    );

  }

  filterMoviesByDirector(director: string) {
    console.log("filter movies", director);

    this.movieService.filterMoviesByDirector(director).subscribe(
      movies => this.movies = movies,
      error => this.errorMessage = <any>error
    );

  }

  filterMoviesByMainStar(mainStar: string) {
    console.log("filter movies", mainStar);

    this.movieService.filterMoviesByMainStar(mainStar).subscribe(
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
