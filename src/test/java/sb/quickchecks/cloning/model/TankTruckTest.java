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
public class TankTruckTest {
    
    @Test
    public void testClone() {
        TankTruck tankTruck1 = new TankTruck("TankTruck", "SCANIA", 120, new Engine(400));
        tankTruck1.setType(TankTruck.Type.GASOLINE);
        TankTruck tankTruck2 = tankTruck1.clone();
        tankTruck1.setType(TankTruck.Type.MILK);
        assertThat(tankTruck1.getType(), is(not(equalTo(tankTruck2.getType()))));
    }
    
}
