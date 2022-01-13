import java.util.ArrayList;

public class Airports {

    //The class is for saving airport.txt
    private final String city; // the name of city
    private final ArrayList<String> airportAliases; // List of airports' names owned by the city

    public Airports(String city, ArrayList<String> airportAlises){
        this.city = city;
        this.airportAliases = airportAlises;
    }

    public String getCity() { return city; }
    public ArrayList<String> listAlises() { return airportAliases; }
}
