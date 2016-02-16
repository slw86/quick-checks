/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.cloning.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author slwk
 */
public class CarRegistry {
    
    private Set<Car> cars = new HashSet<>();
    private static CarRegistry carRegistry;
    
    private CarRegistry() {
    }
    
    public static CarRegistry getInstance() {
        if(carRegistry == null) {
            carRegistry = new CarRegistry();
        }
        return carRegistry;
    }
}
