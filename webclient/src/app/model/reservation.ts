export class Reservation {
  constructor(
    readonly id: number,
    readonly status: string,
    readonly movie: string,
    readonly user: string,
    readonly seats: number,
    readonly price: number,
  ) {
  }
}
