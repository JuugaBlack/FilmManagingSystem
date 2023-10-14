package com.example;

import java.util.List;

public class Movie {
    private String movieName;
    private String movieDirector;
    private String mainActor;
    private String information;
    private Hall hall;
    private String timeLength;
    private double price;
    static String moviefile = "电影信息V1.4.xlsx";
    protected static List<Movie> movieList = FileManager.readMoviesFromFile(moviefile);

    public Movie() {
    }

    public Movie(String movieName, String movieDirector, String mainActor, String information, String timeLength){
        this.movieName = movieName;
        this.movieDirector = movieDirector;
        this.mainActor = mainActor;
        this.information = information;
        this.timeLength = timeLength;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getMovieName() {
        return movieName;
    }

    public void setMovieDirector(String movieDirector) {
        this.movieDirector = movieDirector;
    }
    public String getMovieDirector() {
        return movieDirector;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }
    public String getMainActor() {
        return mainActor;
    }

    public void setInformation(String information) {
        this.information = information;
    }
    public String getInformation() {
        return information;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }

    public void setTimeLength(String timeLength) {
        this.timeLength = timeLength;
    }
    public String getTimeLength() {
        return timeLength;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void addMovie(Movie movie) {
        movieList.add(movie);
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void updateMovieInformation(String movieName, String movieDirector, String mainActor, String information) {
        this.movieName = movieName;
        this.movieDirector = movieDirector;
        this.mainActor = mainActor;
        this.information = information;
    }

    public void removeMovie(Movie movie) {
        movieList.remove(movie);
    }

}
