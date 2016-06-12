package com.ericwickstrom.uglymovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final String API_ID = "";

    //JSON parser strings for movie database
    private final String ADULT = "adult";
    private final String POSTER_PATH = "poster_path";
    private final String RESULTS = "results";
    private final String OVERVIEW = "overview";
    private final String RELEASE_DATE = "release_date";
    private final String GENRE_IDS = "genre_ids";
    private final String ID = "id";
    private final String ORIGINAL_TITLE = "original_title";
    private final String ORIGINAL_LANGUAGE = "original_language";
    private final String TITLE = "title";
    private final String BACKDROP_PATH = "backdrop_path";
    private final String POPULARITY = "popularity";
    private final String VOTE_COUNT = "vote_count";
    private final String VIDEO = "video";
    private final String VOTE_AVERAGE = "vote_average";
    GridView gridView;
    ImageAdapter imageAdapter;
    private ArrayList<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridView);
        imageAdapter = new ImageAdapter(this, movies);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }
        });


        FetchMovieTask fmt = new FetchMovieTask();
        fmt.execute();
    }

    private void populateMovies(String json) {
        try {
            JSONObject reader = new JSONObject(json);
            JSONArray results = reader.getJSONArray(RESULTS);
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                Movie movie = new Movie();
                movie.setPosterPath(result.getString(POSTER_PATH).substring(1));
                movie.setAdult(result.getBoolean(ADULT));
                movie.setOverview(result.getString(OVERVIEW));

                //parse Release Date from json in to Date object
                String date = result.getString(RELEASE_DATE);
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                movie.setReleaseDate(df.parse(date));

                //create array for GenreIds from JSONArray
                JSONArray genreIds = result.getJSONArray(GENRE_IDS);
                int[] genres = new int[genreIds.length()];
                for (int j = 0; j < genreIds.length(); j++) {
                    genres[j] = genreIds.getInt(j);
                }
                movie.setGenreIds(genres);

                movie.setId(result.getLong(ID));
                movie.setOriginalTitle(result.getString(ORIGINAL_TITLE));
                movie.setOriginalLanguage(result.getString(ORIGINAL_LANGUAGE));
                movie.setTitle(result.getString(TITLE));
                movie.setBackdropPath(result.getString(BACKDROP_PATH));
                movie.setPopularity(result.getDouble(POPULARITY));
                movie.setVoteCount(result.getLong(VOTE_COUNT));
                movie.setVideo(result.getBoolean(VIDEO));
                movie.setVoteAverage(result.getDouble(VOTE_AVERAGE));

                movies.add(movie);
            }

        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        }


    }

    private class FetchMovieTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            HttpURLConnection urlConnection;
            BufferedReader reader;
            String movieJson;
            String urlString = "http://api.themoviedb.org/3/movie/top_rated?api_key=b7449954824bae384ce3314f87fc872d";

            try {
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                urlConnection.disconnect();

                movieJson = buffer.toString();
                populateMovies(movieJson);
                Log.v("MOVIE", movieJson);

            } catch (Exception e) {
                Log.e("ERROR", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            imageAdapter.notifyDataSetChanged();
        }
    }
}




