import java.util.ArrayList;

public class FindPath {
    private ArrayList<Path> properList;

    public ArrayList<Path> findCheapestsList(String date){

        ArrayList<Path> cheapest = new ArrayList<>();
        int minPrice = 0;

        for(int i = 0; i<Graphs.getListOfAllPaths().size(); i++){//take from listAll list

            if(Time.compareDate(date, Graphs.getListOfAllPaths().get(i).getStartDate()) >= 0 ) {//if time is suitable
                if(cheapest.isEmpty()){//in the beginnig add 0th item and add
                    minPrice = Graphs.getListOfAllPaths().get(i).getTotalPrice();
                }
                else if (Graphs.getListOfAllPaths().get(i).getTotalPrice() > minPrice) {//there is a higher price, then continue
                    continue;
                }
                else if (Graphs.getListOfAllPaths().get(i).getTotalPrice() < minPrice) {//there is a lower price, add to the list and save minprice
                    minPrice = Graphs.getListOfAllPaths().get(i).getTotalPrice();
                    cheapest.clear();// remove all of them
                }
                cheapest.add(Graphs.getListOfAllPaths().get(i));
            }

        }
        return cheapest;
    }
    public ArrayList<Path> findQuickestsList(String date){

        ArrayList<Path> quickest = new ArrayList<>();
        String minDuration = null;

        for(int i = 0; i< Graphs.getListOfAllPaths().size(); i++){//take from listAll list

            if(Time.compareDate(date, Graphs.getListOfAllPaths().get(i).getStartDate()) >= 0 ) {//if time is suitable
                if(quickest.isEmpty()){//in the beginnig add 0th item and add
                    minDuration = Graphs.getListOfAllPaths().get(i).getTime();
                }
                else if (Time.compareHours(Graphs.getListOfAllPaths().get(i).getTime(), minDuration) < 0) {//there is a higher duration, then continue
                    continue;
                }
                else if (Time.compareHours(Graphs.getListOfAllPaths().get(i).getTime(), minDuration) > 0) {//there is a lower duration, add to the list and save as a minDuration
                    minDuration = Graphs.getListOfAllPaths().get(i).getTime();
                    quickest.clear();
                }
                quickest.add(Graphs.getListOfAllPaths().get(i));
            }
        }
        return quickest;
    }

    public ArrayList<Path> findProperList(String date){
        properList = new ArrayList<>();

        int minPrice = 0;
        String minDuration = null;

        for(int i = 0; i< Graphs.getListOfAllPaths().size(); i++){// get from listAll list
            if(Time.compareDate(date, Graphs.getListOfAllPaths().get(i).getStartDate()) >= 0 ) {//if time is suitable
                if(properList.isEmpty()){//if list is empty add save as a minprice and minduration
                    properList.add(Graphs.getListOfAllPaths().get(i));
                    minPrice = Graphs.getListOfAllPaths().get(i).getTotalPrice();
                    minDuration = Graphs.getListOfAllPaths().get(i).getTime();
                }

                //if there is a flight which is lower price and lower duration, clear proper list and add it
                else if (Graphs.getListOfAllPaths().get(i).getTotalPrice() <= minPrice & Time.compareHours(Graphs.getListOfAllPaths().get(i).getTime(), minDuration) >= 0) {
                    properList.clear();
                    minPrice = Graphs.getListOfAllPaths().get(i).getTotalPrice();
                    minDuration = Graphs.getListOfAllPaths().get(i).getTime();
                    properList.add(Graphs.getListOfAllPaths().get(i));

                    //if there is a flight which is lower price or lower duration, add it
                } else if (Graphs.getListOfAllPaths().get(i).getTotalPrice() <= minPrice || Time.compareHours(Graphs.getListOfAllPaths().get(i).getTime(), minDuration) >= 0) {
                    properList.add(Graphs.getListOfAllPaths().get(i));

                }
            }
        }

        return properList;
    }

    public ArrayList<Path> findCheaper(int price, String date) {
        ArrayList<Path> list = new ArrayList<>();
        for(int i = 0; i<properList.size();i++){// get from proper list
            if(Time.compareDate(date, Graphs.getListOfAllPaths().get(i).getStartDate()) >= 0 ) {//if time is suitable
                //if it is lower than desired price then add it
                if (properList.get(i).getTotalPrice() < price) {
                    list.add(properList.get(i));
                }
            }
        }

        return list;

    }
    public ArrayList<Path> findQuicker(String startDate, String finishDate) {

        ArrayList<Path> list = new ArrayList<>();
        for (Path path : properList) {// get from proper list

            long finish = Time.compareDate(path.getFinishDate().split(" ")[0], finishDate.split(" ")[0]);
            long finishHours = Time.compareHours(path.getFinishDate().split(" ")[1], finishDate.split(" ")[1]);
            if ((Time.compareDate(path.getStartDate(), startDate) <= 0) & (finish + finishHours > 0)) {//if time is suitable and time is duration than desired duration
                list.add(path);
            }

        }
        return list;

    }
    public ArrayList<Path> findExcludingOrIncluding(String word, String date , boolean is) {
        boolean isContain;
        ArrayList<Path> list = new ArrayList<>();

        ArrayList<String> weights = new ArrayList<>();
        for(int i = 0; i<properList.size();i++){// get from proper list
            isContain = false;

            if(Time.compareDate(date, Graphs.getListOfAllPaths().get(i).getStartDate()) >= 0 ) {//if time is suitable
                for (Edge z : properList.get(i).list()) {// add all ids to the list in the proper list
                    weights.add(z.weight);
                }
                for (String we : weights) {// if there is a word which is given then isContain is true
                    if (we.contains(word)) {
                        isContain = true;
                        break;
                    }
                }
                if (isContain == is) {// if we want to include add id otherwise don't add.
                    list.add(properList.get(i));
                }
            }
            weights.clear();
        }
        return list;
    }

}


