import java.sql.*;
import java.util.LinkedList;
/**
 * @author Michael Tuskan & Matthew Kim
 * Group Number: 23
 * Date: 05/31/2023
 * Course: TCSS 445 A - Database System Design
 * Project: Final Project Submission
 */
/** Class: LoadData -> Load Data from Microsoft SQL Server */
public class LoadData {
    /**
     * IMPORTANT DATABASE INFORMATION FOR TEACHER ASSISTANT:
     *  - Please use the SQL variables below to change all
     *    SQL server connection methods to your computer's server settings.
     *  - You only need to change these variables once in this class.
     */
    private String serverName = "DESKTOP-SCVOQLF"; //Need to re-configure to end-user's server settings
    private String databaseName = "FORAGERDB"; //Need to re-configure to end-user's server settings
    private String username = "sqlServerMT"; //Need to re-configure to end-user's server settings
    private String password = "Space11!"; //Need to re-configure to end-user's server settings
    private String connectionUrl =
            "jdbc:sqlserver://" + serverName + ":1433;"
                    + "database=" + databaseName + ";"
                    + "user=" + username + ";"
                    + "password=" + password + ";"
                    + "trustServerCertificate=true;"
                    + "loginTimeout=30;";

    /** Instance Variables for LoadData Class */
    private LinkedList<String> PlantSpecies;
    private LinkedList<String> MushroomSpecies;
    private LinkedList<String> EntityName;
    private LinkedList<String> Forager;
    private LinkedList<String> JournalPage;

    /**
     * Constructor Method for LoadData Class
     */
    public LoadData(){
        this.Forager = new LinkedList<String>();
        this.PlantSpecies = new LinkedList<String>();
        this.MushroomSpecies = new LinkedList<String>();
        this.EntityName = new LinkedList<String>();
    }

    /** fillMushroomJournalPage - Query SQL DB Data for Mushroom Journal Page Output
     * @param selectedMushroomName - selected mushroom name
     * @return journalString - journal String
     * */
    public String fillMushroomJournalPage( String selectedMushroomName) {
        String journalString = "";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            String sql = "SELECT "
                    + "JP.JournalPageID, "
                    + "JP.TheDate, "
                    + "(SELECT TD.TheMonth FROM THE_DATE TD WHERE TD.DateID = JP.TheDate) AS TheMonth, "
                    + "(SELECT TD.TheDay FROM THE_DATE TD WHERE TD.DateID = JP.TheDate) AS TheDay, "
                    + "(SELECT TD.TheYear FROM THE_DATE TD WHERE TD.DateID = JP.TheDate) AS TheYear, "
                    + "(SELECT TT.TheHours FROM THE_TIME TT WHERE TT.TimeID = JP.TheTime) AS TheHours, "
                    + "(SELECT TT.TheMinutes FROM THE_TIME TT WHERE TT.TimeID = JP.TheTime) AS TheMinutes, "
                    + "(SELECT TT.TheSeconds FROM THE_TIME TT WHERE TT.TimeID = JP.TheTime) AS TheSeconds, "
                    + "JP.Comments, "
                    + "(SELECT TL.Longitude FROM THE_LOCATION TL WHERE TL.LocationID = JP.TheLocation) AS TheLongitude, "
                    + "(SELECT TL.Latitude FROM THE_LOCATION TL WHERE TL.LocationID = JP.TheLocation) AS TheLatitude, "
                    + "(SELECT P.PhotoDescription FROM PHOTO P WHERE P.PhotoID = JP.Photo) AS PhotoDescription, "
                    + "(SELECT P.PhotoFile FROM PHOTO P WHERE P.PhotoID = JP.Photo) AS PhotoFile, "
                    + "(SELECT P.PhotoSize FROM PHOTO P WHERE P.PhotoID = JP.Photo) AS PhotoSize, "
                    + "(SELECT P.SpeciesType FROM PHOTO P WHERE P.PhotoID = JP.Photo) AS SpeciesType, "
                    + "(SELECT SeasonType FROM SEASON WHERE SeasonID = JP.Season) AS SeasonName, "
                    + "(SELECT TerrainType FROM TERRAIN WHERE TerrainID = JP.Terrain) AS TerrainType, "
                    + "(SELECT MushroomName FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) AS MushroomName, "
                    + "(SELECT Gill FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) AS Gill, "
                    + "(SELECT Stem FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) AS Stem, "
                    + "(SELECT Cap FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) AS Cap, "
                    + "(SELECT SporeColor FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) AS SporeColor, "
                    + "(SELECT PlantName FROM PLANT WHERE PlantID = JP.Plant) AS PlantName, "
                    + "(SELECT LifeHistory FROM PLANT WHERE PlantID = JP.Plant) AS LifeHistory "
                    + "FROM JOURNAL_PAGE JP "
                    + "INNER JOIN FORAGER F ON JP.ForagerID = F.ForagerID "
                    + "WHERE (SELECT MushroomName FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) = ?;";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, selectedMushroomName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Extract the values from the result set
                int month = rs.getInt("TheMonth");
                int day = rs.getInt("TheDay");
                int year = rs.getInt("TheYear");
                int hours = rs.getInt("TheHours");
                int minutes = rs.getInt("TheMinutes");
                int seconds = rs.getInt("TheSeconds");
                String comments = rs.getString("Comments");
                float longitude = rs.getFloat("TheLongitude");
                float latitude = rs.getFloat("TheLatitude");
                String photoDescription = rs.getString("PhotoDescription");
                String photoFile = rs.getString("PhotoFile");
                int photoSize = rs.getInt("PhotoSize");
                String speciesType = rs.getString("SpeciesType");
                String seasonName = rs.getString("SeasonName");
                String terrainType = rs.getString("TerrainType");
                String mushroomName = rs.getString("MushroomName");
                String gill = rs.getString("Gill");
                String stem = rs.getString("Stem");
                String cap = rs.getString("Cap");
                String sporeColor = rs.getString("SporeColor");

                // Create a string representation of the row
                journalString = "Journal Page ID: " + rs.getInt("JournalPageID") + "\n" +
                        "Date: " + month + "/" + day + "/" + year + "\n" +
                        "Time: " + hours + ":" + minutes + ":" + seconds + "\n" +
                        "Comments: " + comments + "\n" +
                        "Location: Latitude " + latitude + ", Longitude " + longitude + "\n" +
                        "Photo: Description - " + photoDescription + ", File - " + photoFile + ", Size - " + photoSize + "\n" +
                        "Species Type: " + speciesType + "\n" +
                        "Season: " + seasonName + "\n" +
                        "Terrain Type: " + terrainType + "\n" +
                        "Mushroom Name: " + mushroomName + "\n" +
                        "Gill: " + gill + "\n" +
                        "Stem: " + stem + "\n" +
                        "Cap: " + cap + "\n" +
                        "Spore Color: " + sporeColor + "\n";
            }
            System.out.println("SQL Query: " + pstmt.toString());
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(journalString);
        return journalString;
    }


    /**
     * fillPlantJournalPage - Query Microsoft SQL DB for Plant Journal Page Output
     * @param selectedMushroomName - Selected Mushroom Name
     * @return journalString - journal String
     */
    public String fillPlantJournalPage( String selectedMushroomName) {
        String journalString = "";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            String sql = "SELECT "
                    + "JP.JournalPageID, "
                    + "JP.TheDate, "
                    + "(SELECT TD.TheMonth FROM THE_DATE TD WHERE TD.DateID = JP.TheDate) AS TheMonth, "
                    + "(SELECT TD.TheDay FROM THE_DATE TD WHERE TD.DateID = JP.TheDate) AS TheDay, "
                    + "(SELECT TD.TheYear FROM THE_DATE TD WHERE TD.DateID = JP.TheDate) AS TheYear, "
                    + "(SELECT TT.TheHours FROM THE_TIME TT WHERE TT.TimeID = JP.TheTime) AS TheHours, "
                    + "(SELECT TT.TheMinutes FROM THE_TIME TT WHERE TT.TimeID = JP.TheTime) AS TheMinutes, "
                    + "(SELECT TT.TheSeconds FROM THE_TIME TT WHERE TT.TimeID = JP.TheTime) AS TheSeconds, "
                    + "JP.Comments, "
                    + "(SELECT TL.Longitude FROM THE_LOCATION TL WHERE TL.LocationID = JP.TheLocation) AS TheLongitude, "
                    + "(SELECT TL.Latitude FROM THE_LOCATION TL WHERE TL.LocationID = JP.TheLocation) AS TheLatitude, "
                    + "(SELECT P.PhotoDescription FROM PHOTO P WHERE P.PhotoID = JP.Photo) AS PhotoDescription, "
                    + "(SELECT P.PhotoFile FROM PHOTO P WHERE P.PhotoID = JP.Photo) AS PhotoFile, "
                    + "(SELECT P.PhotoSize FROM PHOTO P WHERE P.PhotoID = JP.Photo) AS PhotoSize, "
                    + "(SELECT P.SpeciesType FROM PHOTO P WHERE P.PhotoID = JP.Photo) AS SpeciesType, "
                    + "(SELECT SeasonType FROM SEASON WHERE SeasonID = JP.Season) AS SeasonName, "
                    + "(SELECT TerrainType FROM TERRAIN WHERE TerrainID = JP.Terrain) AS TerrainType, "
                    + "(SELECT MushroomName FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) AS MushroomName, "
                    + "(SELECT Gill FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) AS Gill, "
                    + "(SELECT Stem FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) AS Stem, "
                    + "(SELECT Cap FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) AS Cap, "
                    + "(SELECT SporeColor FROM MUSHROOM M WHERE MushroomID = JP.Mushroom) AS SporeColor, "
                    + "(SELECT PlantName FROM PLANT WHERE PlantID = JP.Plant) AS PlantName, "
                    + "(SELECT LifeHistory FROM PLANT WHERE PlantID = JP.Plant) AS LifeHistory "
                    + "FROM JOURNAL_PAGE JP "
                    + "INNER JOIN FORAGER F ON JP.ForagerID = F.ForagerID "
                    + "WHERE (SELECT PlantName FROM PLANT P WHERE PlantID = JP.Plant) = ?;";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, selectedMushroomName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // Extract the values from the result set
                int month = rs.getInt("TheMonth");
                int day = rs.getInt("TheDay");
                int year = rs.getInt("TheYear");
                int hours = rs.getInt("TheHours");
                int minutes = rs.getInt("TheMinutes");
                int seconds = rs.getInt("TheSeconds");
                String comments = rs.getString("Comments");
                float longitude = rs.getFloat("TheLongitude");
                float latitude = rs.getFloat("TheLatitude");
                String photoDescription = rs.getString("PhotoDescription");
                String photoFile = rs.getString("PhotoFile");
                int photoSize = rs.getInt("PhotoSize");
                String speciesType = rs.getString("SpeciesType");
                String seasonName = rs.getString("SeasonName");
                String terrainType = rs.getString("TerrainType");
                String plantName = rs.getString("PlantName");
                String lifeHistory = rs.getString("LifeHistory");

                // Create a string representation of the row
                journalString = "Journal Page ID: " + rs.getInt("JournalPageID") + "\n" +
                        "Date: " + month + "/" + day + "/" + year + "\n" +
                        "Time: " + hours + ":" + minutes + ":" + seconds + "\n" +
                        "Comments: " + comments + "\n" +
                        "Location: Latitude " + latitude + ", Longitude " + longitude + "\n" +
                        "Photo: Description - " + photoDescription + "\n" +
                        "File - " + photoFile + "\n" +
                        "Size - " + photoSize + "\n" +
                        "Species Type: " + speciesType + "\n" +
                        "Season: " + seasonName + "\n" +
                        "Terrain Type: " + terrainType + "\n" +
                        "Plant Name: " + plantName + "\n" +
                        "Life History: " + lifeHistory;
            }
            System.out.println("SQL Query: " + pstmt.toString());
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(journalString);
        return journalString;
    }


    /**
     * fillPlantSpecies - Query Microsoft SQL DB for Plant Species Name Output
     * @param selectedData - selected Data
     */
    public void fillPlantSpecies(String selectedData) {

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Execute a SQL query and get the result
            // Execute a SQL query and get the result
            String sql = "SELECT PS.PlantSpeciesName " +
                    "FROM PLANT_SPECIES AS PS " +
                    "JOIN PLANT AS P ON PS.PlantSpeciesID = P.PlantSpeciesID " +
                    "JOIN JOURNAL_PAGE AS JP ON P.PlantID = JP.Plant " +
                    "JOIN FIELD_JOURNAL AS FJ ON JP.FieldJournalID = FJ.FieldJournalID " +
                    "JOIN FORAGER AS F ON FJ.ForagerID = F.ForagerID " +
                    "JOIN THE_LOGIN AS TL ON F.LoginID = TL.LoginID " +
                    "WHERE TL.Username = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, selectedData); // Set the value for the parameter

            // Execute the query and get the result
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("PLANTSPECIESNAME");
                // Print out the info
                System.out.println("PLANTSPECIESNAME: " + name);
                PlantSpecies.add(name);
            }
            // Clean up
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    /**
     * fillMushroomSpecies - Query Microsoft SQL DB for Mushroom Species Name
     * @param selectedData - Selected Data
     */
    public void fillMushroomSpecies(String selectedData) {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            // Create a Statement object
            Statement stmt = connection.createStatement();
            // Execute a SQL query and get the result
            String sql = "SELECT MS.MushroomSpeciesName\n" +
                    "FROM MUSHROOM_SPECIES AS MS\n" +
                    "JOIN MUSHROOM AS M ON MS.MushroomSpeciesID = M.MushroomSpeciesID\n" +
                    "JOIN JOURNAL_PAGE AS JP ON M.MushroomID = JP.Mushroom\n" +
                    "JOIN FIELD_JOURNAL AS FJ ON JP.FieldJournalID = FJ.FieldJournalID\n" +
                    "JOIN FORAGER AS F ON FJ.ForagerID = F.ForagerID\n" +
                    "JOIN THE_LOGIN AS TL ON F.LoginID = TL.LoginID\n" +
                    "WHERE TL.Username = ?" ;

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, selectedData); // Set the value for the parameter

            // Execute the query and get the result
            ResultSet rs = pstmt.executeQuery();

            // Iterate over the result and print
            while (rs.next()) {
                String name = rs.getString("MUSHROOMSPECIESNAME");
                // Print out the info
                System.out.println("MUSHROOMSPECIESNAME: " + name);
                MushroomSpecies.add(name);
            }
            // Clean up
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    /**
     * fillMushroomName - Query Microsoft SQL DB for Mushroom Name Output
     * @param selectedData - Selected Data
     */
    public void fillMushroomName(String selectedData) {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Execute a SQL query and get the result
            String sql = "SELECT M.MushroomName " +
                    "FROM MUSHROOM AS M " +
                    "JOIN JOURNAL_PAGE AS JP ON M.MushroomID = JP.Mushroom " +
                    "JOIN FIELD_JOURNAL AS FJ ON JP.FieldJournalID = FJ.FieldJournalID " +
                    "JOIN FORAGER AS F ON FJ.ForagerID = F.ForagerID " +
                    "JOIN THE_LOGIN AS TL ON F.LoginID = TL.LoginID " +
                    "WHERE TL.Username = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, selectedData); // Set the value for the parameter

            // Execute the query and get the result
            ResultSet rs = pstmt.executeQuery();

            // Iterate over the result and print
            while (rs.next()) {
                String name = rs.getString("MUSHROOMNAME");
                // Print out the info
                System.out.println("MUSHROOMNAME: " + name);
                EntityName.add(name);
            }
            // Clean up
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    /**
     * fillPlantName - Query Microsoft SQL DB for Plant Name Output
     * @param selectedData - Selected Data
     */
    public void fillPlantName(String selectedData) {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Execute a SQL query and get the result
            String sql = "SELECT P.PlantName " +
                    "FROM PLANT AS P " +
                    "JOIN JOURNAL_PAGE AS JP ON P.PlantID = JP.Plant " +
                    "JOIN FIELD_JOURNAL AS FJ ON JP.FieldJournalID = FJ.FieldJournalID " +
                    "JOIN FORAGER AS F ON FJ.ForagerID = F.ForagerID " +
                    "JOIN THE_LOGIN AS TL ON F.LoginID = TL.LoginID " +
                    "WHERE TL.Username = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, selectedData); // Set the value for the parameter

            // Execute the query and get the result
            ResultSet rs = pstmt.executeQuery();

            // Iterate over the result and print
            while (rs.next()) {
                String name = rs.getString("PLANTNAME");
                // Print out the info
                System.out.println("PLANTNAME: " + name);
                EntityName.add(name);
            }
            // Clean up
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }



    /**
     * fillForager - Query Microsoft SQL DB for Forager Output
     */
    public void fillForager() {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            // Create a Statement object
            Statement stmt = connection.createStatement();

            // Execute a SQL query and get the result
            String sql = "SELECT Username\n" +
                    "FROM THE_LOGIN, FORAGER\n" +
                    "WHERE FORAGER.LoginID = THE_LOGIN.LoginID;";

            ResultSet rs = stmt.executeQuery(sql);

            // Iterate over the result and print
            while (rs.next()) {
                String name = rs.getString("Username");
                // Print out the info
                System.out.println("Username: " + name);
                    Forager.add(name);
            }
            // Clean up
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }


    /**
     * getMushroomSpecies - Get Mushroom Species
     * @return string array
     */
    public String[] getMushroomSpecies() {
        return MushroomSpecies.toArray(new String[0]);
    }


    /**
     * getPlantSpecies - Get Plant Species
     * @return string array
     */
    public String[] getPlantSpecies() {
        return PlantSpecies.toArray(new String[0]);
    }


    /**
     * getName - Get Name
     * @return string array
     */
    public String[] getName() {
        return EntityName.toArray(new String[0]);
    }


    /**
     * getForager - Get Forager
     * @return string array
     */
    public String[] getForager() {
        return Forager.toArray(new String[0]);
    }
}
