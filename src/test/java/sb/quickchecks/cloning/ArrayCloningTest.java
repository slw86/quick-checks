/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.cloning;

import java.util.Arrays;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Test;
import sb.quickchecks.cloning.model.Engine;
import sb.quickchecks.cloning.model.Truck;

/**
 *
 * @author slwk
 */
public class ArrayCloningTest {

    private Truck[] getFleetCar() {
        Truck fleetCar1 = new Truck("fleetCar1", "VOLVO", 210, new Engine(400));
        Truck fleetCar2 = new Truck("fleetCar2", "SCANIA", 190, new Engine(450));
        Truck[] myCarFleet = new Truck[]{fleetCar1, fleetCar2};
        return myCarFleet;
    }

    @Test
    public void testCloneTruckArray() {
        Truck[] myCarFleet = getFleetCar();
        Truck[] myColleagueCarFleet = myCarFleet.clone();
        myCarFleet[0].getEngine().setHorsePower(300);
        System.out.println(Arrays.toString(myColleagueCarFleet));
        assertThat(myCarFleet[0].getEngine().getHorsePower(), is(equalTo(myColleagueCarFleet[0].getEngine().getHorsePower())));
    }

    @Test
    public void testCloneTruckArray2() {
        Truck[] myCarFleet = getFleetCar();
        Truck[] myColleagueCarFleet = myCarFleet.clone();
        myColleagueCarFleet[1] = new Truck("fleetCar3", "Iveco", 220, new Engine(550));
        System.out.println(Arrays.toString(myColleagueCarFleet));
        assertThat(myColleagueCarFleet[1].getModel(), is(not(equalTo(myCarFleet[1].getModel()))));
    }

    @Test
    public void testCloneIntArray() {
        int[] numbers = {1, 2, 3, 4, 5};
        int[] clonedNumbers = numbers.clone();

        assertThat(clonedNumbers[0], is(equalTo(numbers[0])));
        numbers[0] = 9;
        assertThat(clonedNumbers[0], is(not(equalTo(numbers[0]))));
    }

    @Test
    public void testCloneStringArray() {
        String[] languages = {"java", "c++", "python", "pascal"};
        String[] clonedLanguages = languages.clone();

        assertThat(clonedLanguages[0], is(equalTo(languages[0])));
        languages[0] = "javascript";
        assertThat(clonedLanguages[0], is(not(equalTo(languages[0])))); 
    }

}
