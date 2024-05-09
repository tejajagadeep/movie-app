import { Movie } from './Movie';

export class MovieResponse {
  status!: boolean;
  message!: string;
  data!: Movie[];
}
