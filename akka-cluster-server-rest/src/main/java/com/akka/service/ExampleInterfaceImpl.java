package com.akka.service;

/**
 * Created by linyang on 16/1/7.
 */
public class ExampleInterfaceImpl implements ExampleInterface {
    @Override
    public String test(String text) {
        return "Hello -> " + text;
    }
    
    @Override
    public String sayHello(String name) {
        System.out.println("Be Called !");
        return "Hello " + name;
    }
}
