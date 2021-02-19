package net.trancool.flixster;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import net.trancool.flixster.Adapters.MovieAdapter;
import net.trancool.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public  static final String NOW_PLAYING_URL = "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed ";

    public static final String TAG = "MainActivity";
    List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvMovies = findViewById(R.id.rvMovie);
        movies  = new ArrayList<>();

        //create the adapter
      final MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        //set the adpater on the recyclerView
        rvMovies.setAdapter(movieAdapter);

        //set a layoutmanaget on the recyclerview
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        AsyncHttpClient client = new AsyncHttpClient();

        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {

                Log.d(TAG, "OnSucces" );
                JSONObject jsonObject = json.jsonObject;
                try
                {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "results" + results.toString());

                    //movies = Movie.fromJsonArray(results);
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();

                    Log.i(TAG, "Movie" + movies.size());

                } catch (JSONException e)
                {
                    Log.d(TAG, "Json Exception", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Headers headers, String s, Throwable throwable) {

                Log.d(TAG, "OnFailure");

            }
        });

    }
}