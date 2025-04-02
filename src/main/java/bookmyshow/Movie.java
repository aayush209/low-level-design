package bookmyshow;

import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) && Objects.equals(genre, movie.genre) && Objects.equals(language,
                movie.language) && Objects.equals(releaseDate, movie.releaseDate) && Objects.equals(shows, movie.shows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, language, releaseDate, shows);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", language='" + language + '\'' +
                ", releaseDate=" + releaseDate +
                ", shows=" + shows +
                '}';
    }
}
