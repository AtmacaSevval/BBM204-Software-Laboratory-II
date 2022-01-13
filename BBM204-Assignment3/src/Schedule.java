import java.io.*;
import java.util.*;

public class Schedule {
    public static ArrayList<Airports> airportList;
    public static ArrayList<Flights> flightsList;
    public static Hashtable<String, Integer> dictOfCities = new Hashtable<>();

    public Schedule() {
        airportList = new ArrayList<>();
        flightsList = new ArrayList<>();
    }

    public void build(String commandFile) throws IOException {

        PrintWriter writer = new PrintWriter("output.txt");//create empty "output.txt"
        writer.print("");
        writer.close();

        int id = 0;
        for (Airports i : airportList) {
            for (String z : i.listAlises()) {
                dictOfCities.put(z, id);//save the cities which is in airportList, to the "dictofCities" with their ids. For example Ist:0 ,ATH:0.
                id++;
            }
        }

        Graphs g = new Graphs(dictOfCities.size());//Save the number of vertices(airports).

        //Creating a directed graph according to the given "flightlist".
        for (Flights i : flightsList) {
            g.addEdge(dictOfCities.get(i.getDept()), dictOfCities.get(i.getArr()), i.getFlight_id());
        }

        File file = new File(commandFile);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String[] line;
        String commandLine;

        FindPath f = new FindPath();

        while ((commandLine = br.readLine()) != null) {//Reading "command.txt"
            line = commandLine.split("\t");

            try
            {
                FileWriter fw = new FileWriter("output.txt",true); //the true will append the new data
                fw.write("command : " +commandLine +"\n");//appends the string to the file
                fw.close();
            }
            catch(IOException ignored)
            {
            }

            switch (line[0]) {
                case "listAll":

                    ArrayList<String> city1 = null; // source city
                    ArrayList<String> city2 = null; // destination city
                    String[] dept = line[1].split("->");
                    for (Airports i : airportList) {
                        if (i.getCity().equals(dept[0])) {
                            city1 = i.listAlises();

                        }
                    }
                    for (Airports i : airportList) {
                        if (i.getCity().equals(dept[1])) {
                            city2 = i.listAlises();
                        }
                    }
                    g.setDiameter(0);
                    g.buildPathForListAll(city1, city2, dictOfCities);//find listall paths
                    printArraylist(g.listAllPath(line[2]));//write to output

                    break;
                case "listProper":
                    printArraylist(f.findProperList(line[2]));//line2 = start date
                    break;
                case "listCheapest":
                    printArraylist(f.findCheapestsList(line[2]));//line2 = start date
                    break;
                case "listQuickest":
                    printArraylist(f.findQuickestsList(line[2]));//line2 = start date
                    break;
                case "listCheaper":
                    printArraylist(f.findCheaper(Integer.parseInt(line[3]), line[2]));//line3 = finish date, line2 = finish date
                    break;
                case "listQuicker":
                    printArraylist(f.findQuicker(line[2], line[3]));//line3 = finish date, line2 = finish date

                    break;
                case "listExcluding":
                    printArraylist(f.findExcludingOrIncluding(line[3], line[2], false));//line3 = airport, line2 = finish date
                    break;
                case "listOnlyFrom":
                    printArraylist(f.findExcludingOrIncluding(line[3], line[2], true));//line3 = airport, line2 = finish date
                    break;
                case "diameterOfGraph":
                    ArrayList<String> allAirports = new ArrayList<>();
                    for (Airports i : airportList) {
                        allAirports.addAll(i.listAlises());
                    }
                    g.setDiameter(1);
                    int diameter = g.buildPathForDiameter(allAirports,dictOfCities);
                    //print remainder
                    FileWriter fw = new FileWriter("output.txt",true);
                    fw.write("The diameter of graph : " + diameter +"\n\n\n");
                    fw.close();
                    break;
                case "pageRankOfNodes":
                    FileWriter fw1 = new FileWriter("output.txt",true);
                    Set<String> keys = g.pageRank().keySet();
                    for (String str : keys) {
                        fw1.write(str + " : " + String.format("%.3f", g.pageRank().get(str)) + "\n");
                    }
                    fw1.close();
                    break;

            }
        }
    }


    //Writing output.txt
    private void printArraylist(ArrayList<Path> p) {

        try {
            FileWriter fw = new FileWriter("output.txt", true); //the true will append the new data
            //if arraylist is empty then there is no suitable flight
            if(p.isEmpty()){
                fw.write("No suitable flight plan is found\n");
            }
            String cr = "";
            for (Path i : p) {
                for (Edge z : i.list()) {
                    if(!cr.equals("")){

                        fw.write(cr);
                    }
                    fw.write(z.weight + "\t" + getAirportName(z.source) + "->" + getAirportName(z.destination));
                    cr = "||";
                }
                cr = "";

                fw.write("\t" + i.getTime() + "/" + i.getTotalPrice() + "\n");
            }
            fw.write("\n\n");
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Get airport name given vertex
    public static String getAirportName(int index) {
        String key = null;
        for (Map.Entry<String, Integer> entry : Schedule.dictOfCities.entrySet())
            if (entry.getValue() == index) {
                key = entry.getKey();
            }
        return key;
    }

    //Get index in the flightArraylist given the name of an ariport
    public static int getIndexOfAirport(String weight){
        for (int i = 0; i<Schedule.flightsList.size();i++){
            if(Schedule.flightsList.get(i).getFlight_id().equals(weight)){
                return i;
            }
        }
        return -1;
    }

}

