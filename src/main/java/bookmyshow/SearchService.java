package bookmyshow;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SearchService implements MovieSearchable {

    private final List<Movie> movies;
    private final List<Theater> theaters;
    private final Map<String, List<Movie>> cityMovieCache;
    private final Map<String, List<ShowInfo>> movieShowCache;

    public SearchService(List<Movie> movies, List<Theater> theaters) {
        this.movies = movies;
        this.theaters = theaters;
        this.cityMovieCache = new ConcurrentHashMap<>();
        this.movieShowCache = new ConcurrentHashMap<>();
    }

    @Override
    public List<Movie> searchByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchTerm = title.toLowerCase().trim();
        
        // First try exact match
        List<Movie> exactMatches = movies.stream()
                .filter(movie -> movie.getTitle().toLowerCase().equals(searchTerm))
                .collect(Collectors.toList());
        
        if (!exactMatches.isEmpty()) {
            return exactMatches;
        }
        
        // Then try contains
        List<Movie> containsMatches = movies.stream()
                .filter(movie -> movie.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
        
        if (!containsMatches.isEmpty()) {
            return containsMatches;
        }
        
        // Finally try word matching
        return movies.stream()
                .filter(movie -> {
                    String[] movieWords = movie.getTitle().toLowerCase().split("\\s+");
                    String[] searchWords = searchTerm.split("\\s+");
                    
                    for (String searchWord : searchWords) {
                        for (String movieWord : movieWords) {
                            if (movieWord.startsWith(searchWord)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> searchByGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchTerm = genre.toLowerCase().trim();
        
        // First try exact match
        List<Movie> exactMatches = movies.stream()
                .filter(movie -> movie.getGenre().toLowerCase().equals(searchTerm))
                .collect(Collectors.toList());
        
        if (!exactMatches.isEmpty()) {
            return exactMatches;
        }
        
        // Then try contains
        List<Movie> containsMatches = movies.stream()
                .filter(movie -> movie.getGenre().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
        
        if (!containsMatches.isEmpty()) {
            return containsMatches;
        }
        
        // Try word matching for multi-genre movies (e.g., "Action Adventure")
        return movies.stream()
                .filter(movie -> {
                    String[] movieGenres = movie.getGenre().toLowerCase().split("\\s+");
                    String[] searchGenres = searchTerm.split("\\s+");
                    
                    for (String searchGenre : searchGenres) {
                        for (String movieGenre : movieGenres) {
                            if (movieGenre.startsWith(searchGenre)) {
                                return true;
                            }
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> searchByLanguage(String language) {
        if (language == null || language.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String searchTerm = language.toLowerCase().trim();
        
        // First try exact match
        List<Movie> exactMatches = movies.stream()
                .filter(movie -> movie.getLanguage().toLowerCase().equals(searchTerm))
                .collect(Collectors.toList());
        
        if (!exactMatches.isEmpty()) {
            return exactMatches;
        }
        
        // Then try contains
        return movies.stream()
                .filter(movie -> movie.getLanguage().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    @Override
    public List<Movie> searchByCity(String city) {
        if (city == null || city.trim().isEmpty()) {
            return new ArrayList<>();
        }
        
        // Check cache first
        String cacheKey = city.toLowerCase().trim();
        return cityMovieCache.computeIfAbsent(cacheKey, k -> {
            List<Movie> result = new ArrayList<>();
            for (Theater theater : theaters) {
                if (theater.getCity().toLowerCase().contains(k)) {
                    addMoviesFromTheater(theater, result);
                }
            }
            return result;
        });
    }

    private void addMoviesFromTheater(Theater theater, List<Movie> result) {
        for (CinemaHall hall : theater.getCinemaHalls()) {
            for (Show show : hall.getShows()) {
                Movie movie = show.getMovie();
                if (!result.contains(movie)) {
                    result.add(movie);
                }
            }
        }
    }

    @Override
    public List<Movie> searchByReleaseDate(Date releaseDate) {
        if (releaseDate == null) {
            return new ArrayList<>();
        }
        
        // First try exact date match
        List<Movie> exactMatches = movies.stream()
                .filter(movie -> isSameDate(movie.getReleaseDate(), releaseDate))
                .collect(Collectors.toList());
        
        if (!exactMatches.isEmpty()) {
            return exactMatches;
        }
        
        // If no exact matches, try movies within a week range
        return movies.stream()
                .filter(movie -> isWithinDateRange(movie.getReleaseDate(), releaseDate, 7))
                .collect(Collectors.toList());
    }

    private boolean isSameDate(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    private boolean isWithinDateRange(Date movieDate, Date searchDate, int daysRange) {
        if (movieDate == null || searchDate == null) {
            return false;
        }
        
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(searchDate);
        cal2.setTime(movieDate);
        
        // Calculate the difference in days
        long diffInMillis = Math.abs(cal2.getTimeInMillis() - cal1.getTimeInMillis());
        long diffInDays = diffInMillis / (24 * 60 * 60 * 1000);
        
        return diffInDays <= daysRange;
    }

    // Update the ShowInfo class to handle the combined name and address
    public static class ShowInfo {
        private final Show show;
        private final Theater theater;
        private final CinemaHall cinemaHall;
        private final int availableSeats;

        public ShowInfo(Show show, Theater theater, CinemaHall cinemaHall, int availableSeats) {
            this.show = show;
            this.theater = theater;
            this.cinemaHall = cinemaHall;
            this.availableSeats = availableSeats;
        }

        public Show getShow() {
            return show;
        }

        public Theater getTheater() {
            return theater;
        }

        public CinemaHall getCinemaHall() {
            return cinemaHall;
        }

        public int getAvailableSeats() {
            return availableSeats;
        }

        @Override
        public String toString() {
            // Split theater name into name and address if it contains a hyphen
            String theaterName = theater.getName();
            String theaterAddress = "";
            
            int hyphenIndex = theaterName.indexOf(" - ");
            if (hyphenIndex != -1) {
                theaterAddress = theaterName.substring(hyphenIndex + 3);
                theaterName = theaterName.substring(0, hyphenIndex);
            }

            return String.format(
                "Theater: %s\n" +
                "Address: %s\n" +
                "City: %s\n" +
                "Hall: %s\n" +
                "Movie: %s\n" +
                "Show Time: %s\n" +
                "Available Seats: %d",
                theaterName,
                theaterAddress,
                theater.getCity(),
                cinemaHall.getName(),
                show.getMovie().getTitle(),
                show.getShowTime(),
                availableSeats
            );
        }
    }

    public List<ShowInfo> getShowDetails(Movie movie, String city) {
        String cacheKey = movie.getTitle() + "|" + (city != null ? city : "all");
        
        return movieShowCache.computeIfAbsent(cacheKey, k -> {
            List<ShowInfo> showInfoList = new ArrayList<>();
            for (Theater theater : theaters) {
                if (city == null || theater.getCity().equalsIgnoreCase(city)) {
                    addShowInfoFromTheater(movie, theater, showInfoList);
                }
            }
            return showInfoList;
        });
    }

    private void addShowInfoFromTheater(Movie movie, Theater theater, List<ShowInfo> showInfoList) {
        for (CinemaHall hall : theater.getCinemaHalls()) {
            for (Show show : hall.getShows()) {
                if (show.getMovie().equals(movie)) {
                    showInfoList.add(new ShowInfo(show, theater, hall, show.getAvailableSeats().size()));
                }
            }
        }
    }
}
