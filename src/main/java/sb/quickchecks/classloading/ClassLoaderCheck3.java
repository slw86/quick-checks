/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.classloading;

import java.io.FileInputStream;
import java.io.IOException;
import javafx.util.converter.ShortStringConverter;

/**
 *
 * @author slwk
 */
public class ClassLoaderCheck3 {

    private static final String classNameToLoad = "sb.java8.TestStreams";
//    private static final String classNameToLoad = "sb.quickchecks.classloading.model.DummyClass";
    
    private static final String pathToClassFile ="C:\\Users\\slwk\\Documents\\NetBeansProjects\\Java8Testing\\target\\classes\\sb\\java8\\TestStreams.class";
//    private static final String pathToClassFile ="C:\\Users\\slwk\\Documents\\NetBeansProjects\\QuickChecks\\target\\classes\\sb\\quickchecks\\classloading\\model\\DummyClass.class";

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        MyClassLoader classLoader = new MyClassLoader(pathToClassFile, ShortStringConverter.class.getClassLoader());
//        MyClassLoader classLoader = new MyClassLoader();

        System.out.println(ShortStringConverter.class.getClassLoader());
        System.out.println(classLoader);

        Class d = classLoader.loadClass(classNameToLoad);
//        Class d = classLoader.findClass(classNameToLoad);

        System.out.println("Class loader of loaded class: " + d.getClassLoader());
        System.out.println("Loaded class name: " + d.getCanonicalName());
        System.out.println(d.newInstance().toString());

    }

}

class MyClassLoader extends ClassLoader {
    
    private final String pathToClassFile;

    public MyClassLoader(String pathToClassFile) {
        this.pathToClassFile=pathToClassFile;
    }

    public MyClassLoader(String pathToClassFile, ClassLoader parent) {
        super(parent);
        this.pathToClassFile=pathToClassFile;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        byte[] classBytes = null;
        try {
            classBytes = readClassBytes();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try {
            super.findSystemClass(name);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        Class c = defineClass(name, classBytes, 0, classBytes.length);
        resolveClass(c);
        return c;
    }

    private byte[] readClassBytes() throws IOException {
        FileInputStream fis = new FileInputStream(pathToClassFile);
        byte[] bytes = new byte[fis.available()];
        fis.read(bytes);
        fis.close();
        return bytes;
    }

//    @Override
//    protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
//
//        Class result;
//        byte[] classBytes = null;
//
//        try {
//            classBytes = readClassBytes();
//        } catch (IOException ex) {
//            Logger.getLogger(MyClassLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        try {
//            result = super.findSystemClass(className);
//            return result;
//        } catch (ClassNotFoundException e) {
//
//        }
//
//        result = defineClass(className, classBytes, 0, classBytes.length);
//        resolveClass(result);
//        return result;
//    }
}
