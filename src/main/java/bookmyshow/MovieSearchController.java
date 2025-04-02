package bookmyshow;

import java.util.Date;
import java.util.List;

public class MovieSearchController {

    private final SearchService searchService;

    public MovieSearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    public List<Movie> searchByTitle(String title) {
        return searchService.searchByTitle(title);
    }

    public List<Movie> searchByGenre(String genre) {
        return searchService.searchByGenre(genre);
    }

    public List<Movie> searchByLanguage(String language) {
        return searchService.searchByLanguage(language);
    }

    public List<Movie> searchByCity(String city) {
        return searchService.searchByCity(city);
    }

    public List<Movie> searchByReleaseDate(Date releaseDate) {
        return searchService.searchByReleaseDate(releaseDate);
    }
}
