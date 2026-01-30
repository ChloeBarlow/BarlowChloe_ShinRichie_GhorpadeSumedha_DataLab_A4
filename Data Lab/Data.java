import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Data {
    
    // Instance Variables
    private String gameTitle;
    private double userRating;
    private double metaScore;
    private String releaseDate;
    private String genre;

    // Default constructor
    public Data(){
        gameTitle = "N/A";
        userRating = 0;
        metaScore = 0;
        releaseDate = "";
        genre = "N/A";
    }

    // Initializing constructor
    public Data(String title, double rating, double meta, String date, String genre){
        this.gameTitle = title;
        this.userRating = rating;
        this.metaScore = meta;
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

    public double getMetaScore(){
        return metaScore;
    }

    public String getDate(){
        return releaseDate;
    }

    public String getGenre(){
        return genre;
    }

    // Methods

    public String toString(){
        return("Game: " + gameTitle + " | User Rating: " + userRating + " | Meta Score: " + metaScore + " | Release date: " + releaseDate + " | Genre: " + genre);
    }

    public static void main(String[] args){
        Data[] dataList = new Data[47775];

        String csvFile = "metacritic_pc_games.csv";
        String line = "";
        int index = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))){
            br.readLine(); // Skip first line
            while ((line = br.readLine()) != null){
                String[] values = line.split(",");
                String title = values[0];
                double rating = Double.parseDouble(values[8]);
                double meta = Double.parseDouble(values[7]);
                String date = values[2];
                String genre = values[4];

                Data data = new Data(title, rating, meta, date, genre);
                dataList[index] = data;
                index++;
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        /*
        for (Data data : dataList){
            System.out.println(data.toString());
        }
        */

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
