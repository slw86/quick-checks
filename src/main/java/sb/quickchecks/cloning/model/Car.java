/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.cloning.model;

public class Car implements Cloneable{

    private String brand;
    private String model;
    private int maxSpeed;
    private Engine engine;

    public Car(String brand, String model, int maxSpeed, Engine engine) {
        this.brand = brand;
        this.model = model;
        this.maxSpeed = maxSpeed;
        this.engine = engine;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public Engine getEngine() {
        return engine;
    }
    
    
    
    
//    @Override
//    public Car clone() throws CloneNotSupportedException {
//        return (Car) super.clone(); 
//    }
    
    @Override
    public Car clone()  {
        try { 
            Car clone = (Car) super.clone();
            clone.engine = this.engine.clone();
            return clone;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Unable to clone "+ex);
        }
    }

    @Override
    public String toString() {
        return "Car{" + "brand=" + brand + ", model=" + model + ", maxSpeed=" + maxSpeed + "km/h, engine=" + engine + '}';
    }
}
