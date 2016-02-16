/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.cloning.model;

/**
 *
 * @author slwk
 */
public class Truck extends Car {
    
    private int payload;

    public Truck(String brand, String model, int maxSpeed, Engine engine) {
        super(brand, model, maxSpeed, engine);
    }

    public int getPayload() {
        return payload;
    }

    public void setPayload(int payload) {
        this.payload = payload;
    }

    @Override
    public Truck clone() {
        
//        Truck truckClone = new Truck(getBrand(), getModel(), getMaxSpeed(), engineClone);
        Truck truckClone = (Truck)super.clone();
        return truckClone;
    }
}
