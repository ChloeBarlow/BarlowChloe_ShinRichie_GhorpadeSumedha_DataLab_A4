import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Data {
    
    // Instance Variables
    private String gameTitle;
    private double userRating;
    private String releaseDate;
    private String genre;

    // Default constructor
    public Data(){
        gameTitle = "N/A";
        userRating = 0;
        releaseDate = "";
        genre = "N/A";
    }

    // Initializing constructor
    public Data(String title, double rating, String date, String genre){
        this.gameTitle = title;
        this.userRating = rating;
        this.releaseDate = date;
        this.genre = genre;
    }

    // Accessors
    public String getTitle(){
        return gameTitle;
    }

    public double getUserRating(){
        return userRating;
    }

    public String getDate(){
        return releaseDate;
    }

    public String getGenre(){
        return genre;
    }

    // Methods
    public String toString(){
        return("Game: " + gameTitle + " | User Rating: " + userRating + " | Release date: " + releaseDate + " | Genre: " + genre);
    }

    public static void main(String[] args){
        Data[] dataList = new Data[513250];

        String csvFile = "metacritic_pc_games.csv";
        String line = "";
        int index = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            while ((line = br.readLine()) != null){
                String[] values = line.split(";", -1); // -1 to keep trailing empty strings
                if (values.length == 4){
                    String title = values[0];
                    String ratingStr = values[3].trim();
                    double rating;
                    if (ratingStr.isEmpty()) {
                        rating = 0.0;
                    } else {
                        try {
                            rating = Double.parseDouble(ratingStr);
                        } catch (NumberFormatException nfe) {
                            rating = 0.0;
                        }
                    }
                    String date = values[1];
                    String genre = values[2];

                    Data data = new Data(title, rating, date, genre);
                    dataList[index] = data;
                    index++;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        /* Print all Ratings
        for (Data data : dataList){
            System.out.println(data.toString());
        }
        */

        // Find total number of unique game titles
        String currentTitle = "";
        int numTitles = 0;
        for (Data data : dataList){
            if (data != null){
                if (!data.getTitle().equals(currentTitle)){
                    numTitles++;
                    currentTitle = data.getTitle();
                }
            }
        }
        System.out.println("The total number of unique game titles is: " + numTitles);

        // Creating variables needed for average and standard deviation calculations
        double totalRatings = 0;
        double averageRating = 0;
        double ratingDiff = 0;
        double sumRatingDiff = 0;
        double ratingStdDev = 0;
        
        // Find sums of user ratings by game titles (+ store titles, genres, and dates)
        double[] ratingSums = new double[numTitles];
        String[] titles = new String[numTitles];
        String[] genres = new String[numTitles];
        String[] dates = new String[numTitles];
        titles[0] = dataList[0].getTitle();
        genres[0] = dataList[0].getGenre();
        dates[0] = dataList[0].getDate();
        currentTitle = dataList[0].getTitle();
        int titleIndex = 0;
        for (int i = 0; i < dataList.length; i++) {
            Data data = dataList[i];
            if (data == null) continue;
            if (data.getTitle().equals(currentTitle)) {
                ratingSums[titleIndex] += data.getUserRating();
            } else {
                titleIndex++;
                if (titleIndex >= numTitles) break;
                currentTitle = data.getTitle();
                titles[titleIndex] = currentTitle;
                genres[titleIndex] = data.getGenre();
                dates[titleIndex] = data.getDate();
                ratingSums[titleIndex] += data.getUserRating();
            }
        }
        
        // Calculate average user rating by game titles
        for (double sum : ratingSums) {
            totalRatings += sum;
        }

        averageRating = totalRatings / numTitles;
        System.out.println("The average user rating by game titles is: " + averageRating);

        // Calculate standard deviation of user ratings by game titles
        for (double sum : ratingSums) {
            ratingDiff = sum - averageRating;
            sumRatingDiff += ratingDiff * ratingDiff;
        }
        ratingStdDev = Math.sqrt(sumRatingDiff / numTitles);
        System.out.println("The standard deviation of user ratings by game titles is: " + ratingStdDev);

        // Find which game title has the highest user rating sum with its release date & genre
        double highestRating = ratingSums[0];
        String highestTitle = titles[0];
        String highestDate = dates[0];
        String highestGenre = genres[0];
        for (Data data : dataList){
            if (data != null){
                for (int i = 0; i < ratingSums.length; i++){
                    if (ratingSums[i] > highestRating){
                        highestRating = ratingSums[i];
                        highestTitle = titles[i];
                        highestDate = dates[i];
                        highestGenre = genres[i];
                    }
                }
            }
        }

        // Calculate how many standard deviations above the average the highest rating is
        double numStdDevs = (highestRating - averageRating) / ratingStdDev;
        System.out.println("The game title with the highest user rating sum is: [" + highestTitle + "] with a sum of [" + highestRating + "], released in [" 
                            + highestDate + "] in the genre of [" + highestGenre + "].\nIt is [" + numStdDevs + "] standard deviations above the average.");
    }
}
