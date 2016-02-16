/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.classloading;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
public class CustomClassLoaderDemo extends ClassLoader {
        @Override
      public Class<?> findClass(String name) {
         byte[] bt = loadClassData(name);
         return defineClass(name, bt, 0, bt.length);
      }
      private byte[] loadClassData(String className) {
            try {
                //        //read class
//        InputStream is = getClass().getClassLoader().getResourceAsStream(className.replace(".", "/")+".class");
//        ByteArrayOutputStream byteSt = new ByteArrayOutputStream();
//        //write into byte
//        int len =0;
//        try {
//                     while((len=is.read())!=-1){
//                           byteSt.write(len);
//                      }
//               } catch (IOException e) {
//                     e.printStackTrace();
//               }
//        //convert into byte array
//        return byteSt.toByteArray();
                
                FileInputStream fis = new FileInputStream("C:\\Users\\slwk\\Documents\\NetBeansProjects\\QuickChecks\\target\\classes\\sb\\quickchecks\\classloading\\model\\DummyClass.class");
                
                byte[] bytes = new byte[fis.available()];
                fis.read(bytes);
                
                return bytes;
            } catch (IOException ex) {
                Logger.getLogger(CustomClassLoaderDemo.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
     }
    
}
