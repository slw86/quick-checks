/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.cloning.model;

import static org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author slwk
 */
public class TruckTest {

    /**
     * Test of clone method, of class Truck.
     */
    @Test
    public void testClone() {
        Truck truck1 = new Truck("Truck", "MAN", 190, new Engine(400));
        Truck truck2 = (Truck) truck1.clone();
        truck1.getEngine().setHorsePower(500);
        assertThat(truck2.getEngine().getHorsePower(), is(not(equalTo(truck1.getEngine().getHorsePower()))));
    }

}
