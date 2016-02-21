package sb.quickchecks.cloning.model;

public class Car {//implements Cloneable {

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

    @Override
    public String toString() {
        return "Car{ brand=" + brand + ", model=" + model + ", maxSpeed=" + maxSpeed + "km/h, engine=" + engine + "}";
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

    @Override
    public Car clone() {
        try {
            return (Car) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Unable to clone " + ex);
        }
    }
}
