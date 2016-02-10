/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.cloning;

import sb.quickchecks.cloning.model.Car;
import sb.quickchecks.cloning.model.Engine;

public class CloneCheck implements Cloneable {

    public static void main(String[] args)  {
        Engine myCarEngine = new Engine(150);
        Car myFirstCar = new Car("Ford", "Focus", 210, myCarEngine);
        Car myColleagueCar = (Car) myFirstCar.clone();
        
        System.out.println("My car: "+myFirstCar);
        System.out.println("My colleague car: "+myColleagueCar);
        
        myFirstCar.getEngine().setHorsePower(200);
        
        System.out.println("My car: "+myFirstCar);
        System.out.println("My colleague car: "+myColleagueCar);
        
        System.out.println(myFirstCar.equals(myColleagueCar));
        System.out.println(myFirstCar.getEngine().equals(myColleagueCar.getEngine()));
        
        
    }
}



