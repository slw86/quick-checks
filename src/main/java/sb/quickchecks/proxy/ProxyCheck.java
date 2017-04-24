/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sb.quickchecks.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author slwk
 */
public class ProxyCheck {
 
    interface SampleInterface {
        public void doSmth();
    }
    
    private static class ProxyCheckHandler implements InvocationHandler {
        
        private SampleInterface original;

        public ProxyCheckHandler(SampleInterface original) {
            this.original = original;
        }
        
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Before method invoke");
            Object returned = method.invoke(original, args);
            System.out.println("After method invoked");
            return returned;
        }
        
    }
    
    public static void main(String[] args) {
        
        SampleInterface sampleInterface = new SampleInterface() {

            @Override
            public void doSmth() {
                System.out.println("Inside doSmth method");
            }
        };
        
        SampleInterface sampleInterfaceWithoutProxy = new SampleInterface() {

            @Override
            public void doSmth() {
                System.out.println("Inside doSmth method without proxy");
            }
        };
        
        InvocationHandler handler = new ProxyCheckHandler(sampleInterface);
        
        SampleInterface proxy = (SampleInterface) Proxy.newProxyInstance(SampleInterface.class.getClassLoader(), new Class[] {SampleInterface.class}, handler);
        
        proxy.doSmth();
        
        sampleInterfaceWithoutProxy.doSmth();

	System.out.println();

	System.out.println();
    }
}
