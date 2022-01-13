import java.util.ArrayList;
import java.util.Collections;

public class GnomeSort {

    void gnomeSort(ArrayList<Employees> a){
        int pos = 0;

        while(pos < a.size()){
            if (pos == 0 || a.get(pos).getYear() >= a.get(pos-1).getYear()){
                pos = pos + 1;
            }

            else{

                Collections.swap(a, pos, pos-1);
                pos = pos - 1;
            }
        }

    }
}
