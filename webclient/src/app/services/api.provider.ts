import {Injectable} from "@angular/core";
import {environment} from "../../environments/environment";

@Injectable()
export class ApiProvider {
  host = environment.host;
  paths = {
    root: () => `/`,
    movies: () => `${this.host}/camel/api/movies`,
    reservations: () => `${this.host}/camel/api/reservations`
  };

  go() {
    return this.paths;
  }
}
