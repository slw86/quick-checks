package sb.quickchecks.cloning.model;

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
        Engine engineClone = getEngine().clone();
        Truck truckClone = new Truck(getBrand(), getModel(), getMaxSpeed(), engineClone);
//        Truck truckClone = (Truck)super.clone();
        return truckClone;
    }
}
