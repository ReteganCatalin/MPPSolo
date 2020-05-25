import {Component, OnInit} from '@angular/core';
import {Movie} from "../shared/movie.model";
import {MovieService} from "../shared/movie.service";
import {Router} from "@angular/router";
import {Location} from "@angular/common";
import {SortObject} from "../shared/SortObject";
import {Sort} from "../shared/sort";


@Component({
  moduleId: module.id,
  selector: 'app-movie-new',
  templateUrl: './movie-paginated.component.html',
  styleUrls: ['./movie-paginated.component.css'],
})
export class MoviePaginatedComponent implements OnInit {
  errorMessage: string;
  movies: Array<Movie>;
  selectedMovie: Movie;
  selectedDirection: string;
  selectedColumn: string;
  sort: Sort;
  private sorting: SortObject;
  constructor(private movieService: MovieService,
              private location: Location,
              private router: Router) {
  }

  ngOnInit(): void {
    this.getMoviesPaginated('0','5');
    this.sort=new Sort();
    this.sort.sort=new Array<SortObject>();
  }

  getMoviesPaginated(PageNo:string,Size:string) {

    this.movieService.getPaginatedMovies(PageNo,Size)
      .subscribe(
        movies => this.movies = movies,
        error => this.errorMessage = <any>error
      );
  }

  onSelect(movie: Movie): void {
    this.selectedMovie = movie;
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

    let sorter=this.sort.sort;

    this.movies=this.movies.sort((a,b)=> {
        let sortCond=sorter.map(cond => {
            let value = 0;

          if(cond.column=='title')
          {
            value=a.title.localeCompare(b.title)
          }
          else if(cond.column=='genre')
          {
            value=a.genre.localeCompare(b.genre)
          }
          else if(cond.column=='director')
          {
            value=a.director.localeCompare(b.director)
          }
          else if(cond.column=='mainStar')
          {
            value=a.mainStar.localeCompare(b.mainStar)
          }
          else if(cond.column=='yearOfRelease')
          {
            value=a.yearOfRelease-b.yearOfRelease;
          }
          else if(cond.column=='id')
          {
            value=a.id-b.id;
          }

            return value;
          }
        ).filter(val=>val!=0);
        if(sortCond.length==0)
        {
          return 0
        }
        else
        {
          return sortCond[0];
        }
      }
    );

  }

  filterBy(title: string) {
    this.movies=this.movies.filter(a=>a.title.includes(title));
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

