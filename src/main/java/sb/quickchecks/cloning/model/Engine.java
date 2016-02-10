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
public class Engine implements Cloneable {
    
    private int horsePower;

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
        return "Engine{" + "horsePower=" + horsePower + '}';
    }

    @Override
    public Engine clone()  {
        try { 
            return (Engine) super.clone();
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("Unable to clone "+ex);
        }
    }
    
    
    
}
