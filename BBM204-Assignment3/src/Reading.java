import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Reading {


    //Read airport.txt save "airport" arraylist
    public void readAirport(String airportFile) throws IOException {

        File file = new File(airportFile);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String[] line;
        String st;
        while ((st = br.readLine()) != null) {

            line = st.split("\t");
            ArrayList<String> strList = new ArrayList<String>(
                    Arrays.asList(line));

            Schedule.airportList.add(new Airports(strList.remove(0), strList)); // add city and its airports
        }
    }

    //Read flight.txt save "airport" flightList
    public void readFlight(String flightFile) throws IOException {

        File file = new File(flightFile);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String[] line;

        String st;
        while ((st = br.readLine()) != null) {
            line = st.split("\t");
            Schedule.flightsList.add(new Flights(line[0], line[1].split("->")[0], line[1].split("->")[1], line[2], line[3], Integer.parseInt(line[4])) );

        }

    }

}
