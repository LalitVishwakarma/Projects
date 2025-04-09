import { Injectable } from '@angular/core';
import { HttpModule } from '@angular/http';
import 'rxjs/add/operator/map';
import { Http } from '@angular/http';

@Injectable()
export class DataService {

  result : any;

  constructor(private http: Http) { }


  getPrices () {
    return this.http.get('https://api.coinmarketcap.com/v1/ticker/?limit=10').
    map(result => {
      this.result = result.json();
      return this.result;
    });
  }
}
