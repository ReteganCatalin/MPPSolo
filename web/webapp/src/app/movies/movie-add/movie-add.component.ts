import {Component, OnInit} from '@angular/core';
import {MovieService} from "../shared/movie.service";
import {Location} from "@angular/common";
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-movie-new',
  templateUrl: './movie-add.component.html',
  styleUrls: ['./movie-add.component.css']
})
export class MovieAddComponent implements OnInit {
  movieForm: FormGroup;
  constructor(private movieService: MovieService,
              private location: Location
  ) {
  }

  ngOnInit(): void {

    this.movieForm = new FormGroup({
      'title': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z]+$")
      ]),
      'genre': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z ]+$")
      ]),
      'director': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z ]+$")
      ]),
      'mainStar': new FormControl("", [
        Validators.required,
        Validators.pattern("^[a-zA-Z ]+$")
      ]),
      'yearOfRelease': new FormControl("", [
        Validators.required,
        Validators.min(0),
        Validators.pattern("^0$|^[1-9]+[0-9]*$")
      ])
    });
  }
  get yearOfRelease() {
    return this.movieForm.get('yearOfRelease');
  }
  get title() {
    return this.movieForm.get('title');
  }

  get genre() {
    return this.movieForm.get('genre');
  }

  get director()
  {
    return this.movieForm.get('director')
  }

  get mainStar()
  {
    return this.movieForm.get('mainStar')
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
