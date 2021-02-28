package net.trancool.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Movie
{
    String posterPath;
    String backDropPath;
    String title;
    String overview;
    double voteAverage;
    int movieID;



    public Movie() { }//No-Arg constructor required by Parceler

    public int getMovieID() {
        return movieID;
    }

    public Movie(JSONObject jsonObject) throws JSONException
    {
        posterPath = jsonObject.getString("poster_path");
        backDropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        movieID = jsonObject.getInt("id");
    }



    public  static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException
    {
        List<Movie> movies  =new ArrayList<>();
        for (int i = 0; i <movieJsonArray.length(); i++)
        {
            movies.add( new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath()
    {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath) ;
    }
    public String getBackDropPath()
    {
        return  String.format( "https://image.tmdb.org/t/p/w342/%s", backDropPath);
    }

    public String getTitle()
    {
        return title;
    }

    public String getOverview()
    {
        return overview;
    }

    public Movie(double ratings) {
        this.voteAverage = ratings;
    }

    public double getVoteAverage() {
        return voteAverage;
    }
}
