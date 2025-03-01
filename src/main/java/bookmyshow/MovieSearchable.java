package bookmyshow;

import java.util.Date;
import java.util.List;

public interface MovieSearchable {

    List<Movie> searchByTitle(String title);

    List<Movie> searchByGenre(String genre);

    List<Movie> searchByLanguage(String language);

    List<Movie> searchByCity(String city);

    List<Movie> searchByReleaseDate(Date releaseDate);
}
