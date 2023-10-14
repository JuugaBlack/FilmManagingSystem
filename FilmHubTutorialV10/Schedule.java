package FilmHubTutorialV10;

import java.util.ArrayList;
import java.util.List;

public class Schedule {
    Movie movie;
    Hall hall;
    private String time;
    private double price;
    protected static List<Schedule> schedulesList = new ArrayList<>();

    public Schedule(Movie movie, Hall hall, String time, double price) {
        this.movie = movie;
        this.hall = hall;
        this.time = time;
        this.price = price;
    }

    public Schedule() {
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Hall getHall() {
        return hall;
    }
    public void setHall(Hall hall) {
        this.hall = hall;
    }
    

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}