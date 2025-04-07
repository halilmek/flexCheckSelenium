package com.test.stepdefinitions;

import com.test.utilities.Driver;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks_de {
    
    /**
     * Setzt den WebDriver auf
     */
    @Before
    public void setUpDriver() {
        System.out.println("Driver ist aufgesetzt!");
    }

    /**
     * Schließt den WebDriver
     */
    @After
    public void tearDown() {
        System.out.println("Driver wird geschlossen!");
        Driver.closeDriver();
    }
} 