import java.util.*;

public class Graphs {

    private int V; //number of vertices
    private ArrayList<Edge>[] adj; //directed graph
    private boolean[] marked; // marked edges
    private static ArrayList<Path> listOfAllPaths; //the list for the "listAll" command
    private ArrayList<Integer> prices;
    String sourceCity;
    int diameter;

    @SuppressWarnings("unchecked")
    public Graphs(int V){
        this.V = V;
	adj = new ArrayList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new ArrayList<>();
        }
    }

    //adding edge with their source, destination and weight(Id)
    public void addEdge(int s, int v, String weight){
        Edge edge = new Edge(s, v, weight);
        adj[s].add(edge);
    }

    public static ArrayList<Path> getListOfAllPaths(){
        return listOfAllPaths;
    }


    public void setDiameter(int diameter){ this.diameter = diameter;}


    //building path for "listAll command"
    public void buildPathForListAll(ArrayList<String> deptCity, ArrayList<String> arrCity, Hashtable<String, Integer> my_dict) {
        marked = new boolean[V];
        listOfAllPaths = new ArrayList<>();
        prices = new ArrayList<>();
        ArrayList<String> cityList = new ArrayList<>();
        ArrayList<String> weights = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();

        for (int i = 0; i < V; i++)
            marked[i] = false;

        for (String s : deptCity) {//Source city
            for (String s1 : arrCity) {//destination city
                path.add(my_dict.get(s));// add source airport to the list
                sourceCity = findTheCityOfAirport(my_dict.get(s));
                cityList.add(sourceCity);// add the city of source airport
                printAllPaths(my_dict.get(s), my_dict.get(s1), path, weights, cityList);
                cityList.clear();
            }
        }
    }


    //To find diameter path or diameter value
    public int buildPathForDiameter(ArrayList<String> allAirports, Hashtable<String, Integer> my_dict) {

        marked = new boolean[V];
        prices = new ArrayList<>();
        ArrayList<String> cityList = new ArrayList<>();
        ArrayList<String> weights = new ArrayList<>();
        ArrayList<Integer> path = new ArrayList<>();
        ArrayList<Integer> diameterList = new ArrayList<>();


        for (int i = 0; i < V; i++)
            marked[i] = false;


        for (String s : allAirports) {//all airports
            for (String s1 : allAirports) {
                if(!s.equals(s1)) {// You cant choose same city as a source and destination city
                    path.add(my_dict.get(s));// add source airport to the list
                    sourceCity = findTheCityOfAirport(my_dict.get(s));// add the city of source airport

                    printAllPaths(my_dict.get(s), my_dict.get(s1), path, cityList, weights);
                    if(!prices.isEmpty()){

                        Collections.sort(prices);
                        diameterList.add(prices.get(0));
                    }

                    cityList.clear();
                    prices.clear();
                    path.clear();
                }
            }
        }
        Collections.sort(diameterList);
        return diameterList.get(diameterList.size() - 1);
    }


    public void printAllPaths(int s, int d, ArrayList<Integer> path, ArrayList<String> weights, ArrayList<String> cityList){
        marked[s] = true;
        if(s == d) {

            //add path as  edges
            ArrayList<Edge> edges = new ArrayList<>();
            for(int i = 0; i<path.size() - 1;i++){
                edges.add(new Edge(path.get(i) , path.get(i+1), weights.get(i)));
            }


            int totalPrice;
            String totalTime;
            //To diameter command
            if(diameter == 1){
                totalPrice = findTotalPrices(edges);//Calculate total price of a path
                prices.add(totalPrice);
            }

            //To listAll command
            else {
                //Find start date and finish date of a path
                String startDate = Schedule.flightsList.get(Schedule.getIndexOfAirport(weights.get(0))).getDept_date().split(" ")[0];
                String finishDate = Schedule.flightsList.get(Schedule.getIndexOfAirport(weights.get(weights.size() - 1))).getDept_date().split(" ")[0] + " ";
                finishDate += Time.theSumOfTwoHours(Schedule.flightsList.get(Schedule.getIndexOfAirport(weights.get(weights.size() - 1))).getDept_date().split(" ")[1], Schedule.flightsList.get(Schedule.getIndexOfAirport(weights.get(weights.size() - 1))).getDuration());

                totalPrice = findTotalPrices(edges);//Calculate total price for a path
                totalTime = findTotalTimeForPath(edges);//Calculate total time for a path

                listOfAllPaths.add(new Path(totalTime, totalPrice, edges, startDate, finishDate));
            }
            marked[d] = false;
            return;
        }


        for(Edge w : adj[s]){

            if (!marked[w.destination]) {

                if(path.size() >= 2){


                    //To listAll command
                    if(diameter == 0) {

                        //find the id of recently added airport in city
                        String weightOfLastItem = weights.get(weights.size() - 1);
                        //find the date of recently added airport in city
                        String[] dateOfLastItem = Schedule.flightsList.get(Schedule.getIndexOfAirport(weightOfLastItem)).getDept_date().split(" ");
                        //find the duration of recently added airport in city
                        String durationOfLastItem = Schedule.flightsList.get(Schedule.getIndexOfAirport(weightOfLastItem)).getDuration();
                        //find the finishTime of recently added airport in city
                        String finishTimeOfLastItem = Time.theSumOfTwoHours(dateOfLastItem[1], durationOfLastItem);
                        //the date of item that will be added
                        String[] DateOfAfter = Schedule.flightsList.get(Schedule.getIndexOfAirport(w.weight)).getDept_date().split(" ");

                        //the city of item that will be added
                        String city = findTheCityOfAirport(w.destination);

                        //calculating whether the flight to be added is suitable for the time or not.
                        long durationOfDates = Time.compareDate(dateOfLastItem[0], DateOfAfter[0]);
                        int durationOfHours = Time.compareHours(finishTimeOfLastItem, DateOfAfter[1]);
                        boolean isCompatible = durationOfDates + durationOfHours >= 0;

                        //if time is not suitable or we visited this airport and city before, then you cant add to the path.
                        if ((!isCompatible || path.contains(w.destination) || cityList.contains(city))) {
                            continue;

                        }
                    }

                    //To diameter command
                    else{

                        //we visited this airport and city before, then you cant add to the path.
                        if(path.contains(w.destination) || cityList.contains(sourceCity)){
                            continue;
                        }
                    }
                }

                //if path size is less than 2 add directly there is no restriction

                path.add(w.destination);//add destination airports
                weights.add(w.weight);// add the weight(id) of destination airport
                cityList.add(findTheCityOfAirport(w.destination));// add the city name of destination airport
                printAllPaths(w.destination, d, path, weights, cityList);
                cityList.remove(findTheCityOfAirport(w.destination));
                path.remove(new Integer(w.destination));
                weights.remove(w.weight);


            }
        }
        marked[s] = false;
    }

    //Find the total prices of a path
    public int findTotalPrices(ArrayList<Edge> edges){
        int totalPrice = 0;
        for (Edge edge : edges) {
            int z = Schedule.getIndexOfAirport(edge.getWeight());
            totalPrice += Schedule.flightsList.get(z).getPrice();

        }
        return totalPrice;
    }


    //Find the city of a given airport
    public String findTheCityOfAirport(int destination){
        String city = "";
        for(Airports airports : Schedule.airportList){
            if(airports.listAlises().contains(Schedule.getAirportName(destination)))
                city = airports.getCity();
        }
        return city;
    }


    //Find total time for a path
    public String findTotalTimeForPath(ArrayList<Edge> edges){

        String totalTime = "00:00";
        totalTime = Time.theSumOfTwoHours(totalTime, Schedule.flightsList.get(Schedule.getIndexOfAirport(edges.get(0).getWeight())).getDuration());// add duration of first flight

        //calculate date and finish hours of first flight
        String beforeDurationDate = Schedule.flightsList.get(Schedule.getIndexOfAirport(edges.get(0).getWeight())).getDept_date().split(" ")[0];
        String beforeDurationTime = Time.theSumOfTwoHours(Schedule.flightsList.get(Schedule.getIndexOfAirport(edges.get(0).getWeight())).getDept_date().split(" ")[1], Schedule.flightsList.get(Schedule.getIndexOfAirport(edges.get(0).getWeight())).getDuration());

        if(edges.size() >2) {
            for (int z = 1; z < edges.size(); z++) {
                int i = Schedule.getIndexOfAirport(edges.get(z).getWeight());
                totalTime = Time.theSumOfTwoHours(totalTime, Schedule.flightsList.get(i).getDuration());//add duration of flight
                long durationOfDates = Time.compareDate(beforeDurationDate, Schedule.flightsList.get(i).getDept_date().split(" ")[0]) ;
                int durationOfHours = Time.compareHours(beforeDurationTime, Schedule.flightsList.get(i).getDept_date().split(" ")[1]);
                totalTime = Time.theSumOfTwoHours(totalTime, (durationOfDates + durationOfHours)/60 + ":" + (durationOfDates + durationOfHours)%60);

                beforeDurationDate = Schedule.flightsList.get(i).getDept_date().split(" ")[0];
                beforeDurationTime = Time.theSumOfTwoHours(Schedule.flightsList.get(i).getDept_date().split(" ")[1], Schedule.flightsList.get(i).getDuration());

            }
        }
        return totalTime;
    }

    //"ListAll" command
    public ArrayList<Path> listAllPath(String date){

        ArrayList<Path> listAll = new ArrayList<>();

        //Take the listoffAllpath which we made before and add if is suitable for given date
        for(int i = 0; i<getListOfAllPaths().size(); i++){
            if(Time.compareDate(date, getListOfAllPaths().get(i).getStartDate()) >= 0 ){
                listAll.add(getListOfAllPaths().get(i));
            }
        }
        return listAll;

    }

    public Hashtable<String, Float>  pageRank(){

        Hashtable<String, Integer> cValuesOfAllAirports = new Hashtable<>();//c values for formula
        Set<String> allAirports = new HashSet<>();//all airports in flight.txt
        Hashtable<String, Float> pageRank = new Hashtable<>();//To save page rank values
        Hashtable<String, String> PsOfAllAirports = new Hashtable<>();//page rank of pages Ti which link to page A,


        //add all airports
        for (int i = 0; i<adj.length; i++) {
            for (Edge z : adj[i]) {
                if(!allAirports.contains(Schedule.getAirportName(z.source))){
                    allAirports.add(Schedule.getAirportName(z.source));
                    cValuesOfAllAirports.put(Schedule.getAirportName(z.source), 0);
                    PsOfAllAirports.put(Schedule.getAirportName(z.source), "");
                    pageRank.put(Schedule.getAirportName(z.source) , 1.0F);

                }
                if(!allAirports.contains(Schedule.getAirportName(z.destination))){
                    allAirports.add(Schedule.getAirportName(z.destination));
                    cValuesOfAllAirports.put(Schedule.getAirportName(z.destination), 0);
                    PsOfAllAirports.put(Schedule.getAirportName(z.destination), "");
                    pageRank.put(Schedule.getAirportName(z.destination) , 1.0F);//initiliaza as 1 all pagerank values

                }
            }
        }

        //add c values and page rank of pages T which link to a page
        for (int i = 0; i<adj.length; i++) {
            for (Edge z : adj[i]) {
                cValuesOfAllAirports.replace(Schedule.getAirportName(z.source), cValuesOfAllAirports.get(Schedule.getAirportName(z.source)) + 1);
                PsOfAllAirports.replace(Schedule.getAirportName(z.destination), PsOfAllAirports.get(Schedule.getAirportName(z.destination))+ " " + Schedule.getAirportName(z.source));
            }
        }

        float oldPageRankValue = 0;

        //continue the loop until the values doesn't change
        while (oldPageRankValue != theSumOfValues(pageRank))
        {
            oldPageRankValue = theSumOfValues(pageRank);//save the pageRank value
            //The calculate for all airports
            for (String airport : allAirports) {

                float page = 0.0F;

                String linkToPage = PsOfAllAirports.get(airport).replaceFirst(" ", "");//page rank of pages T which link to a page

                if(linkToPage.isEmpty()){// if there is no page rank of pages T which link to a page, then the page rank values of page is 0.15.

                    page = 0.15F;
                    pageRank.replace(airport, page);//change the page rank value of the airport
                    continue;
                }

                String[] linkToPageArray = linkToPage.split(" ");

                for (String k : linkToPageArray) {
                    page +=  pageRank.get(k) / cValuesOfAllAirports.get(k);//(PR(T1)/C(T1)
                }
                page = page * 0.85F;
                page += 0.15F;
                pageRank.replace(airport, page);
            }
        }
        return pageRank;
    }


    //The sum of values of a given hashtable
    public float theSumOfValues(Hashtable<String, Float> pageRank){
        float sum = 0.0F;

        for (float value : pageRank.values()) {
            sum += value;
        }
        return sum;
    }

}

