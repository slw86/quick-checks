/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.classloading;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.util.converter.ShortStringConverter;
import sb.quickchecks.classloading.model.DummyClass;

/**
 *
 * @author slwk
 */
public class ClassLoaderCheck3 {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
//        MyClassLoader classLoader = new MyClassLoader(ShortStringConverter.class.getClassLoader());
        MyClassLoader classLoader = new MyClassLoader();
        
//        MultiClassLoader classLoader = new JarClassLoader();
        
//        MultiClassLoader classLoader = new MultiClassLoader();
        System.out.println(ShortStringConverter.class.getClassLoader());
        System.out.println(classLoader);
        Class d = classLoader.findClass("sb.quickchecks.classloading.model.DummyClass");
//        Class<DummyClass> d = classLoader.loadClass("sb.quickchecks.classloading.model.DummyClass",true);
//        DummyClass dc =  d.newInstance();
//        System.out.println(dc.getName());
        System.out.println(d.getClassLoader());

    }

   
}

class MyClassLoader extends ClassLoader {

        public MyClassLoader() {
            
        }
        
//        public MyClassLoader(ClassLoader parent) {
//            super(parent);
//        }

    @Override
    protected Class<DummyClass> findClass(String name) throws ClassNotFoundException {
      

        
            
            byte[] classBytes = null;
            try {
                classBytes = readClassBytes();
            } catch (IOException ex) {
                Logger.getLogger(ClassLoaderCheck3.class.getName()).log(Level.SEVERE, null, ex);
            }
            
//                        try {
//			super.findSystemClass(name);
//			
//			
//		} catch (ClassNotFoundException e) {
//			
//		}
            
            Class c =  defineClass(name, classBytes, 0, classBytes.length);
            resolveClass(c);
            return c;
           
        }
        
        private byte[] readClassBytes()  throws IOException{
            FileInputStream fis = new FileInputStream("C:\\Users\\slwk\\Documents\\NetBeansProjects\\QuickChecks\\target\\classes\\sb\\quickchecks\\classloading\\model\\DummyClass.class");
            
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            
//            fis.close();
            
            return bytes;
        }

    @Override
    protected Class<DummyClass> loadClass(String className, boolean resolve) throws ClassNotFoundException {
      
        
        

		Class result;
		byte[] classBytes = null;





            try {
                // ----- Try to load it from preferred source
                // Note loadClassBytes() is an abstract method
                classBytes = readClassBytes();
            } catch (IOException ex) {
                Logger.getLogger(MyClassLoader.class.getName()).log(Level.SEVERE, null, ex);
            }

            try {
			result = super.findSystemClass(className);
			
			return result;
		} catch (ClassNotFoundException e) {
			
		}

		// ----- Define it (parse the class file)
		result = defineClass(className, classBytes, 0, classBytes.length);



			resolveClass(result);



		return result;
	}
    }
