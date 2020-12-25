export class Reservation {
  constructor(
    readonly id: number,
    readonly status: string,
    readonly movieId: number,
    readonly userId: number,
    readonly seats: number
  ) {
  }
}
