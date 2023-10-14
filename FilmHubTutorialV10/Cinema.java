package FilmHubTutorialV10;

import java.util.ArrayList;
import java.util.List;

public class Cinema {
    private List<Hall> halls;
    private List<Movie> movies;

    public Cinema(String name) {
        this.halls = new ArrayList<>();
        this.movies = new ArrayList<>();
    }

    public void addHall(Hall hall) {
        halls.add(hall);
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

}
