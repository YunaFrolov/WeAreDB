package com.company;

public class Movie implements Comparable<Movie>  {

    private String strMovieTitle;
    private double  dPopularity;
    private String strPosterPath;


    public Movie(String strMovieTitle, double dPopularity, String strPosterPath) {
        this.strMovieTitle = strMovieTitle;
        this.dPopularity = dPopularity;
        this.strPosterPath = strPosterPath;
    }

    public String toString() {
        return strMovieTitle;
    }

    //GETTERS
    public String getMovieTitle() {
        return strMovieTitle;
    }
    public double getPopularity() {
        return dPopularity;
    }
    public String getMPosterPath() {
        return strPosterPath;
    }
    //SETTERS
    public void setMovieTitle(String newMovieTitle) {
        this.strMovieTitle = newMovieTitle;
    }
    public void setPopularity(double newPopularity) {
        this.dPopularity = newPopularity;
    }
    public void setPosterPath(String newPosterPath) {
        this.strPosterPath = newPosterPath;
    }
    
    //HELPER
    /* This function is used to compare the popularity of two movies */
    public int compareTo(Movie mov){
        if(this.dPopularity==mov.dPopularity)
            return 0;
        else if(this.dPopularity>mov.dPopularity)
            return 1;
        else
            return -1;
    }

}