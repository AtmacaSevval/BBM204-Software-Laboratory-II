import java.util.ArrayList;
import java.util.Collections;

public class StoogeSort {

    public ArrayList<Employees> stoogesort(ArrayList<Employees> L, int i, int j){

        if(L.get(i).getYear()> L.get(j).getYear()){
            Collections.swap(L, i, j);
        }

        if (j - i > 1) {
            int t = (int) Math.floor((j - i + 1) / 3);
            stoogesort(L, i, j - t);
            stoogesort(L, i + t, j);
            stoogesort(L, i, j - t);
        }

        return L;
    }

}
