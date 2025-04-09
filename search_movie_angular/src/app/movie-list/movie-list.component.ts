import { Component, OnInit } from '@angular/core';
import { MovieService } from './movie.service';


interface Movies {
  movies: Movie[];
}

interface Movie {
  name: string;
  image: string;
}

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {
  movies: Movies[];
  searchedMovies: Movies[];
  start:number; 
  end:number;
  pageSize:number = 5;
  totalPages: number;
  currentPage:number;
  constructor(private movieService:MovieService) { }

  ngOnInit() {
    this.getMovies();
  }

  getMovies(): any {
    this.movieService.getMovies()
      .subscribe(data => {
        this.movies = data.movies;
        this.loadPaging();
      })
      
  }

  loadPaging() {
    this.totalPages = Math.floor(this.movies.length / this.pageSize);
    if(this.movies.length % 5){
      this.totalPages++;
    }
    this.currentPage = 1;
    this.start = this.pageSize*(this.currentPage-1);
    this.end = this.start+5;
  }

  loadMovies(page:number){
    this.currentPage = page;
    this.start = this.pageSize*(this.currentPage-1);
    this.end = this.start+5;
  }
}
