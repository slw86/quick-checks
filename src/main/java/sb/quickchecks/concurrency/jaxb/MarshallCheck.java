package sb.quickchecks.concurrency.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Random;

/**
 * Created by slwk on 11.05.16.
 */
public class MarshallCheck {

    public static void main(String[] args) throws JAXBException, IOException {

        JAXBContext jaxbContext = JAXBContext.newInstance(BusinessEntity.class);
        //Marshaller marshaller = jaxbContext.createMarshaller();

       // marshaller.marshal(getRandomBusinessEntity(), System.out);

        System.out.println(marshall(jaxbContext, getRandomBusinessEntity()));


    }

    static BusinessEntity getRandomBusinessEntity() {
        Random rand = new Random();
        int val = rand.nextInt();

        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setId(val);
        businessEntity.setName(val+"");
        return businessEntity;
    }

    static String marshall(JAXBContext context, BusinessEntity be) throws JAXBException {

        Marshaller marshaller = context.createMarshaller();

        StringWriter sw = new StringWriter();
        marshaller.marshal(be, sw);

        String result = sw.toString();

        return result;

    }
}
