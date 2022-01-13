import java.util.ArrayList;

public class Path {
    //The class for using listCommands

    private final String startDate;//the start date of a path
    private final String finishDate;//finish date of a path
    private final String time;//time or duration of a path
    private final int totalPrice;//total price of a path
    private final ArrayList<Edge> airports; // the visited airports

    Path(String time, int totalPrice, ArrayList<Edge> airports1, String startDate, String finishDate){
        this.time = time;
        this.totalPrice = totalPrice;
        airports = new ArrayList<>(airports1);
        this.startDate = startDate;
        this.finishDate = finishDate;
    }

    public ArrayList<Edge> list() { return airports; }
    public String getTime() { return time; }
    public int getTotalPrice() { return totalPrice; }
    public String getStartDate() { return startDate; }

    public String getFinishDate() { return finishDate; }

}

