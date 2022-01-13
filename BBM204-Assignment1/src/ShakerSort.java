import java.util.ArrayList;
import java.util.Collections;

public class ShakerSort {

    void shakerSort(ArrayList<Employees> A){

        boolean swapped;
        do{
            swapped = false;
            for (int i=0; i <= A.size() - 2; i++)
            {
                if(A.get(i).getYear() > A.get(i + 1).getYear()){

                    Collections.swap(A, i, i+1);
                    swapped = true;

                }
            }

            if(!swapped){
                break;
            }
            
            swapped = false;

            for (int i =A.size() - 2; i >= 0 ; i--)
            {
                if(A.get(i).getYear() > A.get(i + 1).getYear()){

                    Collections.swap(A, i, i+1);
                    swapped = true;

                }
            }

        }
        while(swapped);
    }
}
