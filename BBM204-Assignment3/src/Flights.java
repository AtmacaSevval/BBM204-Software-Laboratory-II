public class Flights {

    //The class is for saving flight.txt

    private final String flight_id;
    private final String dept;
    private final String arr;
    private final String dept_date;
    private final String duration;
    private final int price;

    public Flights(String flight_id, String dept, String arr, String dept_date, String duration, int price){
        this.flight_id = flight_id;
        this.dept = dept;
        this.arr = arr;
        this.dept_date = dept_date;
        this.duration = duration;
        this.price = price;
    }

    public String getFlight_id() { return flight_id; }
    public String getDept() { return dept; }
    public String getArr() { return arr; }
    public String getDept_date() { return dept_date; }
    public String getDuration() { return duration; }
    public int getPrice() { return price; }

}

