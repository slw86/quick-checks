/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.classloading;

import com.sun.java.accessibility.util.EventQueueMonitor;
import jdk.nashorn.api.scripting.NashornException;

/**
 *
 * @author slwk
 */
public class ClassLoaderCheck {
    
    public static void main(String[] args) {
        

        printClassLoaderInfo(String.class);
        
        printClassLoaderInfo(NashornException.class);
        
        printClassLoaderInfo(ClassLoaderCheck.class);
    }
    
    private static void printClassLoaderHierarchy(ClassLoader classLoader) {
        if(classLoader != null) {
            printClassLoaderHierarchy(classLoader.getParent());
            System.out.println(classLoader);
        } else {
            System.out.println("NULL - NATIVE");
        }

    }
    
    private static void printClassLoaderInfo(Class<?> clazz) {
        System.out.println("[Class loader hierarchy for class: "+clazz.getCanonicalName()+" ]");
        printClassLoaderHierarchy(clazz.getClassLoader());
        System.out.println();
    }
}
