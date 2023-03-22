package org.apache.dubbo.samples.impl;

import org.apache.dubbo.samples.api.HelloService;


public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello(String name) {
        return name;
    }
}
