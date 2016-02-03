/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.classloading;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Accordion;
import javafx.util.converter.ShortStringConverter;
import jdk.nashorn.api.scripting.NashornException;


/**
 *
 * @author slwk
 */
public class ClassLoaderCheck2 {
    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        
        Class<?> thisClass = loadClass(NashornException.class, "sb.quickchecks.classloading.ClassLoaderCheck2");
        
        Class<?> shortStringConverterJavaFX = loadClass(ClassLoaderCheck2.class, "javafx.util.converter.ShortStringConverter");
        
        Class<?> shortStringConverterJavaFX2 = loadClass(NashornException.class, "javafx.util.converter.ShortStringConverter");

    }
    
    private static Class<?> loadClass(Class<?> loadedClazz, String classNameToLoad) {
        try {
            Class<?> clazz = loadedClazz.getClassLoader().loadClass(classNameToLoad);
            System.out.println("Class: "+clazz.getCanonicalName()+" loaded successfully");
            return clazz;
        } catch (ClassNotFoundException ex) {
            System.out.println("Cannot load class: "+classNameToLoad);
        }
        return null;
    }
}
