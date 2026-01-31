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
            br.readLine(); // Skip first line
            while ((line = br.readLine()) != null){
                String[] values = line.split(";");
                if (values.length == 4){ 
                String title = values[0];
                double rating = Double.parseDouble(values[3]);
                String date = values[1];
                String genre = values[2];

                Data data = new Data(title, rating, date, genre);
                dataList[index] = data;
                }
                index++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        /* Print all Ratings
        for (Data data : dataList){
            System.out.println(data.toString());
        }
        */

        // Find highest review
        double highestRating = 0;
        String game = "";
        for (Data data : dataList){
            if (data != null){
                if (data.getUserRating() > highestRating){
                highestRating = data.getUserRating();
                game = data.getTitle();
             }
            }
        }
        System.out.println("The highest rating is " + highestRating + " for " + game);
    }
}
