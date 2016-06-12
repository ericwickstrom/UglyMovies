//TODO: save state stuff for if user changes screen orientation
//TODO: move static strings to Movie class (URLs too)
//TODO: make activities less ugly
//TODO: settings to display top rated or most popular searches

package com.ericwickstrom.uglymovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

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
    private final String API_KEY = "";

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
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                Movie movie = movies.get(position);
                intent.putExtra(Movie.TITLE, movie.getTitle());
                intent.putExtra(Movie.POSTER_PATH, movie.getPosterPath());
                intent.putExtra(Movie.RELEASE_DATE, movie.getReleaseDate().toString());
                intent.putExtra(Movie.OVERVIEW, movie.getOverview());
                startActivity(intent);
            }
        });


        FetchMovieTask fmt = new FetchMovieTask();
        fmt.execute();
    }

    private void populateMovies(String json) {
        try {
            JSONObject reader = new JSONObject(json);
            JSONArray results = reader.getJSONArray(Movie.RESULTS);
            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                Movie movie = new Movie();
                movie.setPosterPath(result.getString(Movie.POSTER_PATH).substring(1));
                movie.setAdult(result.getBoolean(Movie.ADULT));
                movie.setOverview(result.getString(Movie.OVERVIEW));

                //parse Release Date from json in to Date object
                String date = result.getString(Movie.RELEASE_DATE);
                DateFormat df = new SimpleDateFormat("yyyy-mm-dd");
                movie.setReleaseDate(df.parse(date));

                //create array for GenreIds from JSONArray
                JSONArray genreIds = result.getJSONArray(Movie.GENRE_IDS);
                int[] genres = new int[genreIds.length()];
                for (int j = 0; j < genreIds.length(); j++) {
                    genres[j] = genreIds.getInt(j);
                }
                movie.setGenreIds(genres);

                movie.setId(result.getLong(Movie.ID));
                movie.setOriginalTitle(result.getString(Movie.ORIGINAL_TITLE));
                movie.setOriginalLanguage(result.getString(Movie.ORIGINAL_LANGUAGE));
                movie.setTitle(result.getString(Movie.TITLE));
                movie.setBackdropPath(result.getString(Movie.BACKDROP_PATH));
                movie.setPopularity(result.getDouble(Movie.POPULARITY));
                movie.setVoteCount(result.getLong(Movie.VOTE_COUNT));
                movie.setVideo(result.getBoolean(Movie.VIDEO));
                movie.setVoteAverage(result.getDouble(Movie.VOTE_AVERAGE));

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
            String urlString = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + API_KEY;

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




