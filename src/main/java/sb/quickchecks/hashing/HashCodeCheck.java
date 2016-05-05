package sb.quickchecks.hashing;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by sbalcerek on 2016-05-05.
 */
public class HashCodeCheck {

    public static void main(String[] args) {

        Map<KeyOfMap, String> testMap = new HashMap<>();

        IntStream intStream = IntStream.range(0, 1000);

        intStream.forEach(value -> testMap.put(new KeyOfMap(value+""), value+""));

        KeyOfMap keyOfMapForTest = new KeyOfMap("-1");
        testMap.put(keyOfMapForTest, "-1");

//        testMap.forEach((keyOfMapForTest, s) -> System.out.println("Key= "+keyOfMapForTest+", value = "+s));


        List<Long> results = new ArrayList<>();

        IntStream.range(0, 100).forEach(v -> {

                    results.add(measureTime(() -> {
                                String value = testMap.get(keyOfMapForTest);
//                                 System.out.println("value = " + value);
                            })
                    );
                }
        );

        System.out.println("AVG = "+results.stream().collect(Collectors.averagingLong(Long::longValue)));


    }

    static long measureTime(Runnable runnable) {
        long start = System.nanoTime();

        runnable.run();

        long end = System.nanoTime();

        System.out.println("Time = "+(end-start));

        return (end-start);

    }

}

class KeyOfMap {

    private String text;

    public KeyOfMap(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "KeyOfMap{" +
                "text='" + text + '\'' +
                '}';
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof KeyOfMap)) return false;
//
//        KeyOfMap keyOfMap = (KeyOfMap) o;
//
//        return getText().equals(keyOfMap.getText());
//
//    }

    @Override
    public int hashCode() {
       return getText().hashCode();

//        return 1;

//        return new Random().nextInt();

    }
}
