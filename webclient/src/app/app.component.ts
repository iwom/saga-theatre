import {Component, OnDestroy, OnInit} from '@angular/core';
import {TheatreService} from "./services/theatre.service";
import {Movie} from "./model/movie";
import {FormControl, FormGroup} from "@angular/forms";
import {interval, Subscription} from "rxjs";
import {switchMap} from "rxjs/operators";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  movieColumns: string[] = ['id', 'name', 'year', 'price', 'maxSeats'];
  movieList: Movie[] = [];
  moviesTotal: number = 0;
  movieSubscription: Subscription

  reservationColumns: string[] = ['id', 'status', 'movieId', 'seats'];
  reservationList: Movie[] = [];
  reservationsTotal: number = 0;
  reservationsSubscription: Subscription

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
    this.movieSubscription = interval(1000)
      .pipe(
        switchMap(() => this.theatreService.fetchMovies())
      )
      .subscribe(
        data => {
          this.movieList = data["movies"]
          this.moviesTotal = data["total"]
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
  }

  ngOnDestroy() {
    this.reservationsSubscription.unsubscribe()
    this.movieSubscription.unsubscribe()
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
