import {Component, OnDestroy, OnInit} from '@angular/core';
import {TheatreService} from "./services/theatre.service";
import {Movie} from "./model/movie";
import {FormControl, FormGroup} from "@angular/forms";
import {interval, Subscription} from "rxjs";
import {map, switchMap, tap} from "rxjs/operators";
import {Customer} from "./model/customer";
import {Reservation} from "./model/reservation";


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit, OnDestroy {
  movieColumns: string[] = ['id', 'name', 'year', 'price', 'seats'];
  movieList: Movie[] = [];
  staticMovieList: Movie[] = [];
  moviesById: Map<number, Movie>;
  moviesTotal: number = 0;

  reservationColumns: string[] = ['id', 'status', 'movie', 'user', 'seats', 'price'];
  reservationList: Reservation[] = [];
  reservationsTotal: number = 0;
  reservationsSubscription: Subscription

  customerColumns: string[] = ['id', 'name', 'limit', 'total'];
  customerList: Customer[] = [];
  staticCustomerList: Customer[] = [];
  customersById : Map<number, Customer>;
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
    this.theatreService.fetchCustomers().subscribe(data => {
      this.staticCustomerList = data["customers"]
    })
    this.theatreService.fetchMovies().subscribe(data => {
      this.staticMovieList = data["movies"]
    })
    this.reservationsSubscription = interval(1000)
      .pipe(
        switchMap(() => this.theatreService.fetchCustomers()),
        tap(data => {
          this.customerList = data["customers"]
          this.customersTotal = data["total"]
          this.customersById = new Map(this.customerList.map(obj => [obj.id, obj]));
        }),
        switchMap(() => this.theatreService.fetchMovies()),
        tap(data => {
          this.movieList = data["movies"]
          this.moviesTotal = data["total"]
          this.moviesById = new Map(this.movieList.map(obj =>  [obj.id, obj]));
        }),
        switchMap(() => this.theatreService.fetchReservations(this.moviesById, this.customersById)),
      )
      .subscribe(
        data => {
          this.reservationList = data["reservations"]
          this.reservationsTotal = data["total"]
          console.log(this.reservationList)
        }
      )
  }

  ngOnDestroy() {
    this.reservationsSubscription.unsubscribe()
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
