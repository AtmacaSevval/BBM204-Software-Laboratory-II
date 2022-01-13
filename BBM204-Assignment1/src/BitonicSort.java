import java.util.ArrayList;
import java.util.Collections;

public class BitonicSort {

    void compAndSwap(ArrayList<Employees> a, int i, int j, int dire){

        if ( (dire==1 && a.get(i).getYear() > a.get(j).getYear() ) || (dire==0 && a.get(i).getYear() < a.get(j).getYear())){

            Collections.swap(a, i, j);
        }
    }

    void bitonicMerge(ArrayList<Employees> a, int low, int cnt, int dire){
        if(cnt > 1) {
            int k = cnt / 2;
            for (int i = low; i < low + k; i++) {
                compAndSwap(a, i, i + k, dire);
            }
            bitonicMerge(a, low, k, dire);
            bitonicMerge(a, low + k, k, dire);
        }
    }

    void bitonicSort(ArrayList<Employees> a, int low, int cnt, int dire){
        if(cnt > 1){
            int k = cnt/2;
            bitonicSort(a, low, k, 1);
            bitonicSort(a, low+k, k, 0);
            bitonicMerge(a, low, cnt, dire);
        }
    }

    void sort(ArrayList<Employees> a, int N, int up)
    {
        bitonicSort(a, 0, N, up);
    }

}
