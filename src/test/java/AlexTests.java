import clojure.java.api.Clojure;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlexTests {

    @BeforeAll
    static void requires() {
        var require = Clojure.var("clojure.core", "require");
        require.invoke(Clojure.read("Alex"));
    }





}