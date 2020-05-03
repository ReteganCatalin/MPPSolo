import {Component, OnInit} from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Location} from "@angular/common";

@Component({
  selector: 'app-movie-new',
  templateUrl: './movie-add.component.html',
  styleUrls: ['./movie-add.component.css']
})
export class MovieAddComponent implements OnInit {

  constructor(private movieService: MovieService,
              private location: Location
  ) {
  }

  ngOnInit(): void {
  }

  saveMovie(title: string, director: string, genre: string,mainStar:string,yearOfRelease:string) {
    console.log("saving movie", title, director, mainStar,genre,yearOfRelease);

    this.movieService.saveMovie({
      id: 0,
      title,
      genre,
      director,
      mainStar,
      yearOfRelease: Number(yearOfRelease)
    })
      .subscribe(movie => console.log("saved movie: ", movie));

    this.location.back(); // ...
  }
}
