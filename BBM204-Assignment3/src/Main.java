import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        /*args0 = airports
        args1= flights
        args2 = commands*/
           Reading reading = new Reading();
        Schedule sc = new Schedule();
        reading.readAirport(args[0]);
        reading.readFlight(args[1]);
        sc.build(args[2]);
    }

}
