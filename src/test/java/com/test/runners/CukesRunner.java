package com.test.runners;

import org.junit.platform.suite.api.*;

/**
 * Cucumber Test Runner Klasse
 * Konfiguriert die Testausführung und Berichterstattung
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameters({
    @ConfigurationParameter(key = "cucumber.glue", value = "com.test.stepdefinitions"),
    @ConfigurationParameter(key = "cucumber.plugin", value = "pretty, html:target/cucumber-reports/cucumber.html, json:target/cucumber-reports/cucumber.json"),
    @ConfigurationParameter(key = "cucumber.execution.parallel.enabled", value = "true"),
    @ConfigurationParameter(key = "cucumber.execution.parallel.config.strategy", value = "fixed"),
    @ConfigurationParameter(key = "cucumber.execution.parallel.config.fixed.parallelism", value = "3"),
    @ConfigurationParameter(key = "cucumber.filter.tags", value = "@wip")
})
public class CukesRunner {
    
    /**
     * Hauptmethode zum Starten der Tests
     * @param args Kommandozeilenargumente (nicht verwendet)
     */
    public static void main(String[] args) {
        org.junit.platform.launcher.LauncherDiscoveryRequest request = org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request()
                .selectors(org.junit.platform.engine.discovery.DiscoverySelectors.selectClass(CukesRunner.class))
                .build();
        
        org.junit.platform.launcher.Launcher launcher = org.junit.platform.launcher.core.LauncherFactory.create();
        launcher.execute(request);
    }
} 