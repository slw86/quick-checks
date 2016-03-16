package sb.quickchecks.logging;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author slwk
 */
public class Slf4jLoggingCheck {

    private static final Logger logger = LoggerFactory.getLogger(Slf4jLoggingCheck.class);
    private static final org.apache.logging.log4j.Logger logger4j = LogManager.getLogger(Slf4jLoggingCheck.class);
    private static final Logger GENERIC_LOGGER = LoggerFactory.getLogger("sb.quickchecks.GenericLogger");

    public static void main(String[] args) {

//        logger.debug("My new computer: "+ createComputer());

//        if(logger.isDebugEnabled()) {
//            logger.debug("My new computer : " + createComputer());
//        }

        Computer computer = createComputer();
        logger.debug("My new computer: "+computer);
//        logger.debug("My new computer: {}",computer);

//        logger.debug("My new computer: {}",createComputer());

//        logger.debug("My new computer: {}",createComputer().getProcessor().doExpensiveOperation());

//        if(logger.isDebugEnabled()) {
//            logger.debug("My new computer: ", createComputer().getProcessor().doExpensiveOperation());
//        }

        logger4j.debug("My new computer: {}",() -> createComputer().getProcessor().doExpensiveOperation());

    }

    private static Computer createComputer() {
        GENERIC_LOGGER.trace("createComputer() invoked");
        CPU cpu = new CPU("Intel", 3);
        Computer computer = new Computer("Lenovo", cpu);
        return computer;
    }
}

class Computer {

    private static final Logger LOGGER = LoggerFactory.getLogger("sb.quickchecks.GenericLogger");
    private CPU processor;
    private String brandName;

    Computer(String brand, CPU cpu) {
        processor = cpu;
        brandName = brand;
    }

    CPU getProcessor() {
        LOGGER.trace("getProcessor() invoked");
        return processor;
    }

    public String getBrandName() {
        LOGGER.trace("getBrandName() invoked");
        return brandName;
    }

    @Override
    public String toString() {
        LOGGER.trace("toString() invoked");
        return "Computer{" +
                "processor=" + processor +
                ", brandName=" + brandName + "}";
    }
}

class CPU {

    private static final Logger LOGGER = LoggerFactory.getLogger("sb.quickchecks.GenericLogger");
    private String brandName;
    private int frequencyGhz;

    public CPU(String brandName, int freq) {
        this.brandName = brandName;
        this.frequencyGhz = freq;
    }

    String doExpensiveOperation() {
        LOGGER.trace("doExpensiveOperation() invoked");
        return "Expensive result";
    }

    public String getBrandName() {
        LOGGER.trace("getBrandName() invoked");
        return brandName;
    }

    @Override
    public String toString() {
        LOGGER.trace("toString() invoked");
        return "CPU{" +
                "brandName='" + brandName + '\'' +
                ", frequency=" + frequencyGhz +
                "GHz}";
    }
}