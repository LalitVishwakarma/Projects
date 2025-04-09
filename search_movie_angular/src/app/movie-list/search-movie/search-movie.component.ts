import { Component, OnInit, Input } from '@angular/core';
import { Movie } from '../../movie'
@Component({
  selector: 'app-search-movie',
  templateUrl: './search-movie.component.html',
  styleUrls: ['./search-movie.component.css']
})
export class SearchMovieComponent implements OnInit {
  @Input('movies') movies:Movie[];
  searchedName: string = "";
  filteredMovies: Movie[] = [];
  actualMovies: Movie[] = [];
  flag: boolean = false;
  moviesss: Movie[] = [];

  constructor() { }

  ngOnInit() {
  }

  searchMovies(): void {
    if (this.searchedName == "") {
      this.moviesss = [];
      return;
    }
    if (this.flag == false) {
      this.flag = true
      for (let j = 0; j < this.movies.length; j++) {
        this.actualMovies.push({ name: this.movies[j].name, image: this.movies[j].image });
      }

    }
    this.filteredMovies = [];
    for (var i = 0; i < this.actualMovies.length; i++) {
      if (this.actualMovies[i].name.toUpperCase().includes(this.searchedName.toUpperCase())) {
        this.filteredMovies.push({ name: this.actualMovies[i].name, image: this.actualMovies[i].image });
      }
    }
    this.moviesss = this.filteredMovies;
  }
}


  




