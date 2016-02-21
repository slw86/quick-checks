package sb.quickchecks.cloning.model;

public class TankTruck extends Truck {

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

    @Override
    public TankTruck clone() {
        return (TankTruck) super.clone();
    }
    
    public enum Type implements Cloneable {
        GASOLINE, MILK, CHEMICALS;
    }
}
