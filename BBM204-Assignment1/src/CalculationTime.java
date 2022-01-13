import java.util.*;


public class CalculationTime {
    public static ArrayList<Employees> arraylist = new ArrayList<>();//This arraylist is for use to sorting algorithms
    public static ArrayList<Employees> copy;//to record the above "arraylist"

    public static ArrayList<Long> comb_arr;//Save run times for each size in comb algorithms
    public static ArrayList<Long> gnome_arr;//Save run times for each size in gnome algorithms
    public static ArrayList<Long> shaker_arr;//Save run times for each size in shaker algorithms
    public static ArrayList<Long> stooge_arr;//Save run times for each size in stooge algorithms
    public static ArrayList<Long> bitoni_arr;//Save run times for each size in bitonic algorithms
    private static int sizeIndex;//Save which size



    public static void main(){
        // I initialized these arraylist with all 0's while I set values without getting errors
        comb_arr = new ArrayList<>(Collections.nCopies(10, 0L));
        gnome_arr = new ArrayList<>(Collections.nCopies(10, 0L));;
        shaker_arr = new ArrayList<>(Collections.nCopies(10, 0L));;
        stooge_arr = new ArrayList<>(Collections.nCopies(10, 0L));;
        bitoni_arr = new ArrayList<>(Collections.nCopies(10, 0L));
        arraylist = new ArrayList<>();
        copy = new ArrayList<>();

        System.out.println("RANDOM ORDER");// I call prepare arraylist to try in random order
        prepareArrayList("random");

        System.out.println("DESCENDING ORDER");
        prepareArrayList("descending");// I call prepare arraylist to try in descending order

        System.out.println("ASCENDING ORDER");
        prepareArrayList("ascending");// I call prepare arraylist to try in ascending order

        stability();
    }

    public static void prepareArrayList(String order) {
        Random r = new Random();

        int size[] = {8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096};


            for (int j1 = 0; j1 < size.length; j1++) {

                for (int i = 0; i < size[j1]; i++) {//I created items as many as size
                    arraylist.add(new Employees(r.nextInt(), randomString()));
                }
                sizeIndex = j1;//I save the size

                if (order.equals("descending")) {// İf we want to sort by descending, then I converted random to descending.

                    Collections.sort(arraylist, (s1, s2) ->
                            Integer.compare(s2.getYear(), s1.getYear()));

                }

                else if (order.equals("ascending")) {// İf we want to sort by ascending, then I converted random to ascending.

                    Collections.sort(arraylist, Comparator.comparingInt(Employees::getYear));
                }
                copy = new ArrayList<>(arraylist);

                callingMethods();
                printRunTimes(size[j1], sizeIndex);
                arraylist.clear();// I cleared the array to use every time for each size.
            }


    }


    public static void printRunTimes(int sizeNum, int size){//To print all algorithms in each size

        System.out.println(sizeNum);
        System.out.println("Comb " + comb_arr.get(size));
        System.out.println("gnome " + gnome_arr.get(size));
        System.out.println("shaker " + shaker_arr.get(size));
        System.out.println("stooge " + stooge_arr.get(size));
        System.out.println("bitonic "+ bitoni_arr.get(size));
        System.out.println();

    }

    public static void callingMethods(){

        CombSort cs = new CombSort();
        GnomeSort gs = new GnomeSort();
        ShakerSort ss = new ShakerSort();
        StoogeSort sos = new StoogeSort();
        BitonicSort bs = new BitonicSort();


        long startTime = System.nanoTime();
        cs.combsort(arraylist);
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;// I calculated their execution times
        comb_arr.set(sizeIndex, (timeElapsed)); // I record execution times for this size their arraylist for print to console

        /*for(Employees s:arraylist){
            System.out.print(s.getYear() + " " +s.getName() +" ");
        }
        System.out.println();*/

        arraylist = new ArrayList<>(copy);//I got back the unsorted arraylist from copy array to try on other algorithms too


        long startTime1 = System.nanoTime();
        gs.gnomeSort(arraylist);
        long endTime1 = System.nanoTime();
        long timeElapsed1 = endTime1 - startTime1;
        gnome_arr.set(sizeIndex, (timeElapsed1));
        arraylist = new ArrayList<>(copy);


        long startTime2 = System.nanoTime();
        ss.shakerSort(arraylist);
        long endTime2 = System.nanoTime();
        long timeElapsed2 = endTime2 - startTime2;
        shaker_arr.set(sizeIndex, (timeElapsed2));
        arraylist = new ArrayList<>(copy);


        long startTime3 = System.nanoTime();
        sos.stoogesort(arraylist,0, arraylist.size() - 1);
        long endTime3 = System.nanoTime();
        long timeElapsed3 = endTime3 - startTime3;
        stooge_arr.set(sizeIndex, (timeElapsed3));
        arraylist = new ArrayList<>(copy);

        long startTime4 = System.nanoTime();
        bs.sort(arraylist, arraylist.size(), 1);
        long endTime4 = System.nanoTime();
        long timeElapsed4 = endTime4 - startTime4;
        bitoni_arr.set(sizeIndex,  (timeElapsed4));
        arraylist = new ArrayList<>(copy);


    }


    public static void stability(){//To test stability
        sizeIndex = 0;// I created 8 lengths arraylist to test stability.
        arraylist.add(new Employees(2, "Rohde"));
        arraylist.add(new Employees(3, "Chen"));
        arraylist.add(new Employees(3, "Fox"));
        arraylist.add(new Employees(3, "Andrews"));
        arraylist.add(new Employees(1, "Furia"));
        arraylist.add(new Employees(4, "Battle"));
        arraylist.add(new Employees(4, "Gazsi"));
        arraylist.add(new Employees(3, "Kanaga"));
        Collections.sort(arraylist, Comparator.comparing(Employees::getName));

        copy = new ArrayList<>(arraylist);
        callingMethods();
        System.out.println(comb_arr.get(sizeIndex) + " " + gnome_arr.get(sizeIndex) + " " + shaker_arr.get(sizeIndex) + " " + stooge_arr.get(sizeIndex) + " "+ bitoni_arr.get(sizeIndex));


    }

    public static String randomString(){// I created random string which is 5 lenghts for arraylist
        int leftLimit = 97;
        int rightLimit = 122;
        int StringLength = 5;
        Random random = new Random();

        String randomstring = random.ints(leftLimit, rightLimit + 1).limit(StringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();

        return randomstring;
    }

}

