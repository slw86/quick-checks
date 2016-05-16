package sb.quickchecks.java8.methodreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by slwk on 14.05.16.
 */
public class MethodReferencesCheck {

    public static void main(String[] args) {

//        ExecutorService executorService = Executors.newFixedThreadPool(4);
//
//        executorService.submit(MethodReferencesCheck::doIt);
//        executorService.submit(MethodReferencesCheck::doIt);
//        executorService.submit(MethodReferencesCheck::doIt);
//        executorService.submit(MethodReferencesCheck::doIt);
//
//        executorService.shutdown();


        List<Developer> developers = new ArrayList<>();
       // IntStream.range(0, 10).forEach(value -> initList(developers, Developer::new));


        Developer adam = new Developer("Adam", "Java", "Latte");
        developers.add(adam);
        developers.add(new Developer("Paul", "C++", "Latte"));
        developers.add(new Developer("Karlene", "Java", "Espresso"));


        List<String> languages = developers.stream().map(Developer::getLanguage).distinct().collect(Collectors.toList());

        System.out.println(languages);

        println(adam::getFavouriteCoffee);


    }

    public static void doIt() {
        System.out.println("Do it "+Thread.currentThread().getName());
    }

    static void initList(List<Developer> developers, Supplier<Developer> supplier) {
        developers.add(supplier.get());
    }

    static void println(Supplier<String> supplier) {
        System.out.println(supplier.get());
    }
}

class Developer {

    String name;
    String language;
    String favouriteCoffee;

    public Developer(String name, String language, String favouriteCoffee) {
        this.name = name;
        this.language = language;
        this.favouriteCoffee = favouriteCoffee;
    }

    public Developer() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFavouriteCoffee() {
        return favouriteCoffee;
    }

    public void setFavouriteCoffee(String favouriteCoffee) {
        this.favouriteCoffee = favouriteCoffee;
    }
}
