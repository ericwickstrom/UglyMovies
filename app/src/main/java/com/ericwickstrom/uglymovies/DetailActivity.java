//TODO: make views less ugly
package com.ericwickstrom.uglymovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    TextView titleTextView;
    ImageView posterImageView;
    TextView releaseYearTextView;
    TextView lengthTextView;
    TextView releaseDateTextView;
    TextView overviewTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        titleTextView = (TextView) findViewById(R.id.detail_title_textView);
        posterImageView = (ImageView) findViewById(R.id.detail_poster_imageView);
        releaseYearTextView = (TextView) findViewById(R.id.detail_releaseYear_textView);
        releaseDateTextView = (TextView) findViewById(R.id.detail_releaseDate_textView);
        lengthTextView = (TextView) findViewById(R.id.detail_length_textView);
        overviewTextView = (TextView) findViewById(R.id.detail_overView_textView);

        Intent intent = getIntent();

        titleTextView.setText(intent.getStringExtra(Movie.TITLE));
        //TODO: set text release year
        //TODO: format date MM/DD/YYYY
        releaseDateTextView.setText(intent.getStringExtra(Movie.RELEASE_DATE));
        //TODO: length is in UX mockups, didn't see it in JSON query
        overviewTextView.setText(intent.getStringExtra(Movie.OVERVIEW));

        String posterUrl = Movie.BASE_URL_W185 + intent.getStringExtra(Movie.POSTER_PATH);

        Picasso.with(getApplicationContext())
                .load(posterUrl)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .resize(560, 800)
                .noFade()
                .into(posterImageView);

    }
}
