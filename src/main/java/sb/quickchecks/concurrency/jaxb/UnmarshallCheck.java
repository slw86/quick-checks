package sb.quickchecks.concurrency.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;

/**
 * Created by slwk on 11.05.16.
 */
public class UnmarshallCheck {

    public static void main(String[] args) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(BusinessEntity.class);
        String marshalled = MarshallCheck.marshall(jaxbContext, MarshallCheck.getRandomBusinessEntity());
        System.out.println(marshalled);

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        StringReader stringReader = new StringReader(marshalled);

        BusinessEntity unmarshalled = (BusinessEntity) unmarshaller.unmarshal(stringReader);

        System.out.println(unmarshalled);
    }
}
