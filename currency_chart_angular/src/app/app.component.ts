import { Component } from '@angular/core';
import { DataService } from './data.service';
import { Observable } from 'rxjs/Rx';
import { Chart } from 'chart.js';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  ObjectKeys = Object.keys;
  coins : Array<any> = [];
  name: Array<string> = [];
  val: Array<Number> = [];

  chart: any;
  constructor (private _data : DataService){}

  to(){
    this.coins = this._data.result;
    if(this.name.length > 0){
      this.name.splice(0, this.name.length);
      this.val.splice(0, this.val.length);
    }
    for (let i = 0; i< this.coins.length; i++) {
      this.name.push(this.coins[i].name);
      this.val.push(parseInt(this.coins[i].price_usd));
    }
    let chartdata = {
      labels: this.name,
      datasets: [
        {
          label: 'Currecy Chart',
          backgroundColor: 'rgba(200, 200, 200, 0.75)',
          borderColor: 'rgba(200, 200, 200, 0.75)',
          hoverBackgroundColor: 'rgba(200, 200, 200, 1)',
          hoverBorderColor: 'rgba(200, 200, 200, 1)',
          data: this.val
        }
      ]
    };
      this.chart = new Chart('ctx', {
      type: 'bar',
      data: chartdata,
    });
  }
  ngOnInit(){
    Observable.interval(1000*5).subscribe(x => {
      this._data.getPrices()
        .subscribe(response => {
          this.coins = response;
          // console.log(this.coins);
          // console.log(response);
          this.to();
;
        },
       error => {
         console.log("this is error");
       });
    });
  }
}
