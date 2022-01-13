import java.util.ArrayList;
import java.util.Collections;

public class CombSort {

     void combsort(ArrayList<Employees> input){

        int gap = input.size();
        double shrink = 1.3;
        boolean sorted = false;

       while(!sorted){
            gap = (int) Math.floor(gap/shrink);

            if(gap <= 1){
                gap = 1;
                sorted = true;
            }

            int i = 0;

            while( i + gap < input.size()){

                if(input.get(i).getYear() > input.get(i + gap).getYear()){

                    Collections.swap(input, i, i+gap);
                    sorted = false;
                }

                i = i + 1;
            }
        }
    }

}
