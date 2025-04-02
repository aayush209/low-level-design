package bookmyshow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SearchService implements MovieSearchable {

    private final List<Movie> movies;

    public SearchService(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public List<Movie> searchByTitle(String title) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                result.add(movie);
            }
        }
        return result;
    }

    @Override
    public List<Movie> searchByGenre(String genre) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getGenre().equalsIgnoreCase(genre)) {
                result.add(movie);
            }
        }
        return result;
    }

    @Override
    public List<Movie> searchByLanguage(String language) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getLanguage().equalsIgnoreCase(language)) {
                result.add(movie);
            }
        }
        return result;
    }

    @Override
    public List<Movie> searchByCity(String city) {
//        List<Movie> result = new ArrayList<>();
//        for (Theater theater : theaters) { // Ensure theaters are initialized
//            if (theater.getCity().equalsIgnoreCase(city)) {
//                result.addAll(theater.getMovies()); // Fetch movies from theaters in the city
//            }
//        }
//        return result;
        return new ArrayList<>();
    }

    @Override
    public List<Movie> searchByReleaseDate(Date releaseDate) {
        List<Movie> result = new ArrayList<>();
        for (Movie movie : movies) {
            if (movie.getReleaseDate().equals(releaseDate)) {
                result.add(movie);
            }
        }
        return result;
    }
}
