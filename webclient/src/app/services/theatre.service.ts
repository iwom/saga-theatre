import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiProvider} from "./api.provider";
import {Observable} from "rxjs";
import {catchError, map} from "rxjs/operators";
import {Movie} from "../model/movie";
import {Reservation} from "../model/reservation";
import {Customer} from "../model/customer";

@Injectable({
  providedIn: 'root'
})
export class TheatreService {

  constructor(private http: HttpClient, private api: ApiProvider) {}

  public fetchMovies(): Observable<any> {
    return this.http.get(this.api.go().movies()).pipe(
      map(data => {
        const movies: Array<Movie> = [];
        data["data"].forEach(element => {
          movies.push(
            new Movie(
              element["id"], element["name"], element["year"], element["price"], element["seats"]
            )
          )
        });
        return {
          "movies": movies,
          "total": data["total"]
        }
      }),
      catchError(err => {
        console.error(err)
        return err
      })
    )
  }

  public fetchReservations(moviesById: Map<number, Movie>, customersById: Map<number, Customer>): Observable<any> {
    return this.http.get(this.api.go().reservations()).pipe(
      map(data => {
        const reservations: Array<Reservation> = [];
        console.log(data["data"])
        console.log(moviesById);
        console.log(customersById);
        data["data"].forEach(element => {
          reservations.push(
            new Reservation(
              element["id"], element["status"], moviesById.get(element["movieId"]).name, customersById.get(element["userId"]).name, element["seats"], element["price"]
            )
          )
        });
        return {
          "reservations": reservations,
          "total": data["total"]
        }
      }),
      catchError(err => {
        console.error(err)
        return err
      })
    )
  }

  public fetchCustomers(): Observable<any> {
    return this.http.get(this.api.go().customers()).pipe(
      map(data => {
        const customers: Array<Customer> = [];
        data["customers"].forEach(element => {
          customers.push(
            new Customer(
              element["id"], element["name"], element["limit"], element["total"]
            )
          )
        });
        return {
          "customers": customers,
          "total": customers.length
        }
      }),
      catchError(err => {
        console.error(err)
        return err
      })
    )
  }

  public createReservation(data: any): Observable<any> {
    console.log(data)
    return this.http.post(this.api.go().reservations(), data)
  }
}
