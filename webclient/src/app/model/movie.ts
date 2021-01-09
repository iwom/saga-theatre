export class Movie {
  constructor(
    readonly id: number,
    readonly name: string,
    readonly year: number,
    readonly price: number,
    readonly seats: number
  ) {
  }
}
