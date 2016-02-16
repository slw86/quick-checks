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
public class TankTruck extends Truck{
    
    private Type tankTrackType;

    public TankTruck(String brand, String model, int maxSpeed, Engine engine) {
        super(brand, model, maxSpeed, engine);
    }

    public Type getType() {
        return tankTrackType;
    }

    public void setType(Type tankTrackType) {
        this.tankTrackType = tankTrackType;
    }
    
    public enum Type implements Cloneable {
        GASOLINE, MILK, CHEMICALS;
        
//        public Object clone() {
//            
//        }
    }
}
