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
public class ProxyCheckWithInterfaceOnly {
    
    
    private static class ProxyCheckHandler implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("Before invocation ");
            System.out.println("I am pretending  invoke method: "+method.getName());
            System.out.println("After invocation");
            return null;
        }
    
    }
    
    public static void main(String[] args) {
        
        InvocationHandler handler = new ProxyCheckHandler();
        
        ProxyCheck.SampleInterface proxy = (ProxyCheck.SampleInterface) Proxy.newProxyInstance(ProxyCheck.SampleInterface.class.getClassLoader(), new Class[] {ProxyCheck.SampleInterface.class}, handler);
        
        proxy.doSmth();
    }
    
}
