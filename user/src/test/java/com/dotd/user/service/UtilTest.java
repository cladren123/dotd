package com.dotd.user.service;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
public class UtilTest {

    @Test
    public void randomNumber() {
        Random random = new Random();
        int number = random.nextInt(10001);
        System.out.println(number);
    }

}
