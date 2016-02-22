package com.akka.service;

import java.io.Serializable;

/**
 * Created by linyang on 16/1/7.
 */
public interface ExampleInterface extends Serializable {
    public String test(String text);
    public String sayHello(String name);
}
