package sb.quickchecks.concurrency.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by slwk on 11.05.16.
 */
public class ConcurrentUnmarshallCheck {

    public static void main(String[] args) throws JAXBException {

        JAXBContext jaxbContext = JAXBContext.newInstance(BusinessEntity.class);

        Marshaller marshaller = jaxbContext.createMarshaller();

        List<String> marshalled = generateBusinessEntities(100).stream().map(businessEntity -> {
                    try {
                        StringWriter writer = new StringWriter();
                        marshaller.marshal(businessEntity, writer);
                        return writer.toString();
                    } catch (JAXBException e) {
                        e.printStackTrace();
                        return null;
                    }
                }
        ).collect(Collectors.toList());

//        marshalled.stream().forEach(System.out::println);


//        unmarshalAll(jaxbContext, marshalled);

        unmarshallViaLegacyUtil(marshalled);
    }

    static void unmarshalAll(JAXBContext jaxbContext, List<String> marshalled) throws JAXBException {
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        marshalled.stream().forEach(s ->
                executorService.submit(() -> {

                            try {
                                BusinessEntity entity = (BusinessEntity)unmarshaller.unmarshal(new StringReader(s));

                            } catch (JAXBException e) {
                                e.printStackTrace();
                            }
                        }
                )
        );

        executorService.shutdown();
    }

    static void unmarshallViaLegacyUtil(List<String> xmlMessages) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        xmlMessages.stream().forEach(message ->
                executorService.submit(() -> {
                            BusinessEntity entity = LegacyMessageService.convert(message);
                            System.out.println(entity);
                        }
                )
        );

        executorService.shutdown();

    }

    static List<BusinessEntity> generateBusinessEntities(int size) {

        List<BusinessEntity> result = new ArrayList<>(size);


        Random rand = new Random();
        int start = rand.nextInt();

        IntStream.range(start, start+size).forEach(value -> {
            BusinessEntity businessEntity = new BusinessEntity();
            businessEntity.setId(value);
            businessEntity.setName(value+"");
            result.add(businessEntity);
        });


        return result;
    }

}

class LegacyMessageService {

    private static JAXBContext jaxbContext;
    private static Unmarshaller unmarshaller;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(BusinessEntity.class);
            unmarshaller = jaxbContext.createUnmarshaller();
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    static BusinessEntity convert(String xmlMessage) {
        try {
            return (BusinessEntity) unmarshaller.unmarshal(new StringReader(xmlMessage));
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
