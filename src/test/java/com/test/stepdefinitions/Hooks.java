package com.test.stepdefinitions;


import com.test.utilities.Driver;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    

    @Before
    public void setUpDriver () {

        System.out.println("Driver is set up!");
    }

    @After
    public void tearDown () {

        System.out.println("Driver is closed!");
        Driver.closeDriver();
    }

}
