import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { MovieListComponent } from './movie-list/movie-list.component';
import { HttpClientModule } from '@angular/common/http';
import { MovieComponent } from './movie-list/movie/movie.component';
import { SearchMovieComponent } from './movie-list/search-movie/search-movie.component';
@NgModule({
  declarations: [
    AppComponent,
    MovieListComponent,
    MovieComponent,
    SearchMovieComponent
  ],
  imports: [
    BrowserModule,HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
