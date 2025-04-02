package bookmyshow;

import bookmyshow.SearchService.ShowInfo;
import java.util.Date;
import java.util.List;

public class MovieSearchController {

    private final SearchService searchService;

    public MovieSearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    public List<Movie> searchMovies(String searchCriteria, String value) {
        switch (searchCriteria.toLowerCase()) {
            case "title":
                return searchService.searchByTitle(value);
            case "genre":
                return searchService.searchByGenre(value);
            case "language":
                return searchService.searchByLanguage(value);
            case "city":
                return searchService.searchByCity(value);
            default:
                throw new IllegalArgumentException("Invalid search criteria");
        }
    }

    public List<Movie> searchMoviesByReleaseDate(Date releaseDate) {
        return searchService.searchByReleaseDate(releaseDate);
    }

    public List<ShowInfo> getMovieShowDetails(Movie movie, String city) {
        return searchService.getShowDetails(movie, city);
    }
}
