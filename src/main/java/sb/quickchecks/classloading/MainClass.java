/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.classloading;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
public class MainClass {
       public static void main(String[] args) throws InstantiationException, IllegalAccessException,
                 NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {

	      CustomClassLoaderDemo loader = new CustomClassLoaderDemo();
              Class<?> c = loader.findClass("sb.quickchecks.classloading.model.DummyClass");
//              Object ob = c.newInstance();
//              Method md = c.getMethod("show");
//              md.invoke(ob);
       }
}