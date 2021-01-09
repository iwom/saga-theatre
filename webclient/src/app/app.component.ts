import {Component, OnDestroy, OnInit} from '@angular/core';
import {TheatreService} from "./services/theatre.service";
import {Movie} from "./model/movie";
import {FormControl, FormGroup} from "@angular/forms";
import {interval, Subscription} from "rxjs";
import {switchMap} from "rxjs/operators";
import {Customer} from "./model/customer";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  movieColumns: string[] = ['id', 'name', 'year', 'price', 'seats'];
  movieList: Movie[] = [];
  moviesTotal: number = 0;
  moviesSubscription: Subscription

  reservationColumns: string[] = ['id', 'status', 'movieId', 'seats'];
  reservationList: Movie[] = [];
  reservationsTotal: number = 0;
  reservationsSubscription: Subscription

  customerColumns: string[] = ['id', 'cardNo'];
  customerList: Customer[] = [];
  customersTotal: number = 0;

  reservationForm = new FormGroup({
    movieId: new FormControl(null),
    userId: new FormControl(null),
    seats: new FormControl(null)
  });

  constructor(
    private theatreService: TheatreService
  ) {
  }

  ngOnInit(): void {
    this.theatreService.fetchCustomers()
      .subscribe(
        data => {
          this.customerList = data["customers"]
          this.customersTotal = data["total"]
        }
      )

    this.reservationsSubscription = interval(1000)
      .pipe(
        switchMap(() => this.theatreService.fetchReservations())
      )
      .subscribe(
        data => {
          this.reservationList = data["reservations"]
          this.reservationsTotal = data["total"]
        }
      )
    this.moviesSubscription = interval(1000)
      .pipe(
        switchMap(() => this.theatreService.fetchMovies())
      )
      .subscribe(
        data => {
          this.movieList = data["movies"]
          this.moviesTotal = data["total"]
        }
      )
  }

  ngOnDestroy() {
    this.reservationsSubscription.unsubscribe()
    this.moviesSubscription.unsubscribe()
  }

  onSubmit(): void {
    this.theatreService.createReservation({
      "movieId": this.reservationForm.value.movieId,
      "userId": this.reservationForm.value.userId,
      "seats": this.reservationForm.value.seats
    }).subscribe(
      data => {
        console.log(data)
      },
      err => {
        console.error(err);
      }
    )
  }
}
