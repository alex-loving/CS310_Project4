package org.example.java;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;



public class Alex {
	
    //T is the type and R is the return type [called like map(x -> x*5)]
    
    public static <T, R> List<R> map(List<T> list, Function<T, R> func) {
        
        // Base
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        //List to return
        List<R> result = new ArrayList<>();

        // Get first char
        R firstMapped = func.apply(list.get(0));

        List<R> restMapped = map(list.subList(1, list.size()), func);

        
        result.add(firstMapped);   
        
        result.addAll(restMapped); 

        return result;
    }

}