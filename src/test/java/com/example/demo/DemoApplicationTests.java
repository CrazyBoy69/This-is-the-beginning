package com.example.demo;

import com.example.demo.entity.Entourage;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;
import java.util.*;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }
    public static void main(String[] args) {
        Entourage entourage = new Entourage();
        entourage.setAggressivity(1);
        Entourage entourage1 = new Entourage();
        entourage1.setAggressivity(2);
        Entourage entourage2 = new Entourage();
        entourage2.setAggressivity(3);
        Entourage entourage3 = new Entourage();
        entourage3.setAggressivity(4);
        List<Entourage> lost = new ArrayList<>();
        lost.add(entourage);
        lost.add(entourage1);
        lost.add(entourage2);
        lost.add(entourage3);
        List<Entourage> list = new ArrayList<>(lost);
        list.get(0).setAggressivity(list.get(0).getAggressivity()+1);
        System.out.println("lost"+lost);
        System.out.println("list"+list);
    }
}
