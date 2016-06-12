package com.ericwickstrom.uglymovies;

import java.util.Date;

/**
 * Movie object structured as defined at imdb.org
 */
public class Movie {
    //Strings used to parse JSON from themoviedb.org
    public static final String ADULT = "adult";
    public static final String POSTER_PATH = "poster_path";
    public static final String RESULTS = "results";
    public static final String OVERVIEW = "overview";
    public static final String RELEASE_DATE = "release_date";
    public static final String GENRE_IDS = "genre_ids";
    public static final String ID = "id";
    public static final String ORIGINAL_TITLE = "original_title";
    public static final String ORIGINAL_LANGUAGE = "original_language";
    public static final String TITLE = "title";
    public static final String BACKDROP_PATH = "backdrop_path";
    public static final String POPULARITY = "popularity";
    public static final String VOTE_COUNT = "vote_count";
    public static final String VIDEO = "video";
    public static final String VOTE_AVERAGE = "vote_average";

    //Base URLs for movie posters
    public static final String BASE_URL_W185 = "http://image.tmdb.org/t/p/w185/";

    private String posterPath;
    private boolean adult;
    private String overview;
    private Date releaseDate;
    private int[] genreIds;
    private long id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private double popularity;
    private long voteCount;
    private boolean video;
    private double voteAverage;

    //Creates empty movie;
    public Movie() {
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int[] getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(int[] genreIds) {
        this.genreIds = genreIds;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(long voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVideo() {
        return video;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

}
