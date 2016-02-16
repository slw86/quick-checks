/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.cloning;

import java.util.Arrays;
import sb.quickchecks.cloning.model.Car;
import sb.quickchecks.cloning.model.Engine;
import sb.quickchecks.cloning.model.TankTruck;
import sb.quickchecks.cloning.model.Truck;

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
        
        Truck truck1 = new Truck("Truck", "MAN", 190, new Engine(400));
        Truck truck2 = (Truck)truck1.clone();
        truck1.getEngine().setHorsePower(500);
        System.out.println(truck2.getEngine().getHorsePower());
        
        TankTruck tankTruck1 = new TankTruck("TankTruck", "SCANIA", 120, new Engine(400));
        tankTruck1.setType(TankTruck.Type.GASOLINE);
        TankTruck tankTruck2 = (TankTruck)tankTruck1.clone();
        tankTruck1.setType(TankTruck.Type.MILK);
        System.out.println(tankTruck2.getType());
        
        
        Truck fleetCar1 = new Truck("fleetCar1", "VOLVO", 210, new Engine(400));
        Truck fleetCar2 = new Truck("fleetCar2", "SCANIA", 190, new Engine(450));
        Truck[] myCarFleet = new Truck[] {fleetCar1, fleetCar2 } ;
        Truck[] myColleagueCarFleet = myCarFleet.clone();
        myCarFleet[0].getEngine().setHorsePower(300);
        System.out.println(Arrays.toString(myColleagueCarFleet));
        
        int[] numbers = {1,2,3,4,5};
        int[] clonedNumbers = numbers.clone();
        numbers[0] = 9;
        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.toString(clonedNumbers));
        
        String[] languages = {"java", "c++", "ada", "pascal"};
        String[] clonedLanguages = languages.clone();
        languages[0] = "swift";
        System.out.println(Arrays.toString(clonedLanguages));
    }
}



