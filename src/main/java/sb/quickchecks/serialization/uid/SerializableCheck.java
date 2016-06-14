package sb.quickchecks.serialization.uid;

import java.io.*;

/**
 * Created by slwk on 14.06.16.
 */
public class SerializableCheck {

    public static final String FILE_NAME = "entity.test";

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Entity entity1 = new Entity();
        entity1.setName("test");
        entity1.setData("Very important data to transport from service to gui");

//        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
//        objectOutputStream.writeObject(entity1);
//        objectOutputStream.close();

        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_NAME));
        Entity object = (Entity) objectInputStream.readObject();
        objectInputStream.close();

        System.out.println(object.getData());

    }
}
