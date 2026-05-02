import java.util.List;

public class Ethan {

    public static boolean member(Object check, Object[] arr, int index) {
        if(index >= arr.length){return false;}
        else{
            if(arr[index] == check){
                return true;
            }
        }
        return member(check, arr, index+1);
    }

    public static Object[] append(Object[] arr1, Object[] arr2){
        if(arr1 == null) {return arr2;}
        if(arr2 == null) {return arr1;}

        Object[] mew = new Object[arr1.length + arr2.length];
        for(int i = 0; i < mew.length; i++){
            if(i < arr1.length){
                mew[i] = arr1[i];
            }
            else{
                mew[i] = arr2[i - arr1.length];
            }
        }
        return mew;
    }
}