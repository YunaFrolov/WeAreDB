package com.company;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * This program requests data from "themoviedb" given a set API
 * Asking from the user for movie IDs they'd like to get information about
 * output: Movie titles and saved posters + the most popular movie from all given movies

 * @version 1

 * @author Yuna Almog
 */
public class Main {

    private static final String strAPIKey = "dc9b5c5661217f5ce9f79d9935546fb7";

    public static void main(String[] args) throws IOException {

        ArrayList<Movie> lmAllMoviesThisRound = new ArrayList<>();

        int intMovieIdFromUser;
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Movie ID (examples: 550, 69, 68), to stop enter 0: ");
        intMovieIdFromUser = sc.nextInt();

        while  (intMovieIdFromUser!= 0) {
            Movie mCurrentMovie = getMovieInfo(intMovieIdFromUser);
            System.out.println("Movie Title: "+mCurrentMovie.getMovieTitle());
            DownloadPoster(mCurrentMovie.getMPosterPath());
            lmAllMoviesThisRound.add(mCurrentMovie);

            intMovieIdFromUser = sc.nextInt();
        }

        System.out.print("Highest rated movie of these: "+ Collections.max(lmAllMoviesThisRound));
    }

    /* This function is used to get the basic info of a movie by an ID received from the user */
    private static Movie getMovieInfo(int intMovieID) throws IOException {
        URL url = new URL("http://api.themoviedb.org/3/movie/"+intMovieID+"?api_key="+strAPIKey);
        URLConnection yc = url.openConnection();
        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        yc.getInputStream()));
        String inputLine;

        Movie mThisMovie = new Movie("tempTitle", 0, "");
        while ((inputLine = in.readLine()) != null) {

            //Poster
            String strPosterUrlStart = inputLine.substring(inputLine.indexOf("poster_path\":\"")+14) ;
            String strPosterPath = strPosterUrlStart.substring(0, strPosterUrlStart.indexOf("\",\"")) ;

            //Title
            String strTitleStart = inputLine.substring(inputLine.indexOf("original_title\":\"")+17) ;
            String strTitle = strTitleStart.substring(0, strTitleStart.indexOf("\",\"")) ;

            //Popularity
            String strPopularityStart = inputLine.substring(inputLine.indexOf("popularity\":")+12) ;
            String strPopularity = strPopularityStart.substring(0, strPopularityStart.indexOf(",\"")) ;

            mThisMovie.setMovieTitle(strTitle);
            mThisMovie.setPopularity(Double.parseDouble(strPopularity));
            mThisMovie.setPosterPath(strPosterPath);

        }

        in.close();
        return mThisMovie;
    }

    /* This function is used to download the poster image of the movie */
    private static void DownloadPoster(String strMoviePosterUrl) throws MalformedURLException {
        URL website = new URL("https://image.tmdb.org/t/p/w185/"+strMoviePosterUrl);
        try (InputStream in = website.openStream()) {
            Path pPathOfPoster = Path.of("c:/temp/"+strMoviePosterUrl);
            Files.copy(in, pPathOfPoster, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Poster saved as:"+pPathOfPoster);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("There was a problem saving the poster");
        }
    }

}
