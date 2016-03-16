package sb.quickchecks.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author slwk
 */
public class Slf4jLoggingCheck2 {

    private static final Logger logger = LoggerFactory.getLogger(Slf4jLoggingCheck.class);
    private static final org.apache.logging.log4j.Logger logger4j = LogManager.getLogger(Slf4jLoggingCheck.class);

    public static void main(String[] args) {



//        logger.debug("Hello");
//
//        logger.debug("Thing is: {}",getThing());
//        logger.debug("Thing name is {}", getThing().getName());
//
//        logger.debug("First part: {} ", getThing().getParts().get(0).getName());

//        logger.trace("First part again: {} ", getThing().getParts().get(0).getName());

//        DummyThing thing = getThing();
//        logger.trace("First thing: ",getThing());
        logger.trace("First part again: {} ", getThing().getParts().get(0).getName());
//        logger4j.trace("First thing with Java 8: {}", () -> getThing().getParts().get(0).getName());
    }

    private static DummyThing getThing() {
        logger.debug("Method getThing() invoked");
        System.out.println("DT: Method getThing() invoked");
        DummyPart part1 = new DummyPart("Part 1");
        DummyPart part2 = new DummyPart("Part 2");

        DummyThing thing = new DummyThing("Simple thing", Arrays.asList(part1, part2));
        return thing;
    }
}

class DummyThing {

    private final String name;
    private List<DummyPart> parts;
    private static final Logger logger = LoggerFactory.getLogger("sb.quickchecks.GenericLogger");

    List<DummyPart> getParts() {
        logger.trace("Method getParts() invoked");
        return parts;
    }

    DummyThing(String name, List<DummyPart> parts) {
        this.parts = parts;
        this.name = name;
    }

    public String getName() {
        logger.trace("Method getName() invoked");
        return name;
    }

    @Override
    public String toString() {
        return "DummyThing{" +
                "name='" + name + '\'' +
                '}';
    }
}

class DummyPart {

    private final String name;
    private static final Logger logger = LoggerFactory.getLogger("sb.quickchecks.GenericLogger");

    DummyPart(String name) {
        this.name = name;
    }

    public String getName() {
        logger.trace("Method getName() invoked");
        return name;
    }
}
