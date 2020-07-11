package com.yz.console;

import org.junit.jupiter.api.Test;

//@SpringBootTest
class ConsoleApplicationTests {

    @Test
    void contextLoads() {
        int random = (int) (Math.random() * 100);
        System.out.println(random);
    }

}
