package bookmyshow;

import java.util.Date;
import java.util.List;

public class Movie {

    private String title;
    private String genre;
    private String language;
    private Date releaseDate;
    private List<Show> shows;

    public Movie(String title, String genre, String language, Date releaseDate) {
        this.title = title;
        this.genre = genre;
        this.language = language;
        this.releaseDate = releaseDate;
    }

    // Getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
