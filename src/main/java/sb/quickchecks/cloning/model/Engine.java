package sb.quickchecks.cloning.model;

import java.util.UUID;

public class Engine implements Cloneable {
    
    private int horsePower;
//    private final String uniqueID = UUID.randomUUID().toString();

    public Engine(int horsePower) {
        this.horsePower = horsePower;
    }

    public int getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(int horsePower) {
        this.horsePower = horsePower;
    }

    @Override
    public String toString() {
        return "Engine{ horsePower=" + horsePower + "}"; //, uniqueID="+uniqueID+" }";
    }

    @Override
    public Engine clone()  {
        try { 
//            Engine e = (Engine) super.clone();
//            e.uniqueID = "test";
            return (Engine) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Unable to clone "+ex);
        }
    }  
}
