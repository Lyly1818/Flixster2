package net.trancool.flixster;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import net.trancool.flixster.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.parceler.Parcels;

import okhttp3.Headers;

public class MovieDetailsActivity extends YouTubeBaseActivity
{


    private String YOUTUBE_API_KEY = "AIzaSyDBBUrkTYSxIujKEoxETQFzSqhqGfyB-4o";
    public static String VIDEO_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";

     Movie movie;
    TextView tvTitle;
    TextView tvOverView;
    RatingBar rbVoteAverage;
    YouTubePlayerView youTubePlayerView;


    final String videoID = "tKodtNFpzBA";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverView = findViewById(R.id.tvOverview);
        rbVoteAverage = findViewById(R.id.rbVoteAverage);
        youTubePlayerView =(YouTubePlayerView) findViewById(R.id.player);





        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));



        tvTitle.setText(movie.getTitle());
        tvOverView.setText(movie.getOverview());
        rbVoteAverage.setRating((float)movie.getVoteAverage());


        float voteAverage = (float)movie.getVoteAverage();

        rbVoteAverage.setRating( voteAverage > 0 ? (voteAverage / 2.0f) : voteAverage);



        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(VIDEO_URL, movie.getMovieID()), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                try {
                    JSONArray results = json.jsonObject.getJSONArray("results");

                    if(results.length() == 0)
                    {
                        return;
                    }
                    String youtubeKey = results.getJSONObject(0).getString("key");

                    Log.d("MovieDailsActivity", youtubeKey);
                    initializeYoutube(youtubeKey);

                } catch (JSONException e) {

                    Log.d("MovieDailsActivity", "failed!");
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }
        });











    }

    private void initializeYoutube(final String youtubeKey) {

        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener()
        {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b)
            {
                youTubePlayer.cueVideo(youtubeKey);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult)
            {

                Log.d(getLocalClassName(), "error initializing youtube player!");

            }
        });


    }


}