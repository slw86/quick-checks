package sb.quickchecks.classloading;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sylwester.balcerek
 */
public class JarClassLoader extends MultiClassLoader {

    public JarClassLoader(String jarName) {

    }

    public JarClassLoader() {

    }

    protected byte[] loadClassBytes(String className) {
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\slwk\\Documents\\NetBeansProjects\\QuickChecks\\target\\classes\\sb\\quickchecks\\classloading\\model\\DummyClass.class");

            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);

//            fis.close();
            return bytes;
        } catch (IOException ex) {
            Logger.getLogger(JarClassLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
