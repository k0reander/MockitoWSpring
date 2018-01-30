package com.packt.junit;

import org.junit.*;

public class SanityTest {

    @BeforeClass
    public static void beforeClass(){
        System.out.println("-----------------------");
        System.out.println("before Class is invoked");
    }

    @Before
    public void before(){
        System.out.println("-----------------");
        System.out.println("before is invoked");
    }

    @After
    public void after(){
        System.out.println("----------------");
        System.out.println("after is invoked");
    }

    @AfterClass
    public static void afterClass(){
        System.out.println("----------------------");
        System.out.println("after Class is invoked");
    }

    @Test
    public void someTest(){
        System.out.println("Some first test");
    }

    @Test
    public void someTest2(){
        System.out.println("Some second test");
    }


}
