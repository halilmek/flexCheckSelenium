package com.test.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.test.utilities.Driver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Page class representing the "Berechnungsergebnis" (Calculation Results) page
 */
public class BerechnungsergebnisPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BerechnungsergebnisPage.class);

    @FindBy(css = "#sollzinsbindungen-form")
    private WebElement resultsForm;

    @FindBy(css = "#sollzinsbindungen-form .panel-card")
    private WebElement resultsPanel;

    @FindBy(css = "#sollzinsbindungen-form button.btn.btn--nav-next")
    private WebElement angebotAnfordernButton;

    public BerechnungsergebnisPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    public void clickAngebotAnfordern() {
        waitForResultsToLoad();
        wait.until(ExpectedConditions.elementToBeClickable(angebotAnfordernButton));
        highlightElement(angebotAnfordernButton);
        takeScreenshot("angebotAnfordern-before");
        angebotAnfordernButton.click();
        takeScreenshot("angebotAnfordern-after");
        logger.info("Successfully clicked Angebot Anfordern button");
    }

    public boolean waitForResultsToLoad() {
        try {
            // Wait for loading animation to appear first (indicates calculation started)
            WebElement loadingAnimation = Driver.getDriver().findElement(By.cssSelector(".loading-animation"));
            wait.until(ExpectedConditions.visibilityOf(loadingAnimation));
            logger.info("Loading animation appeared");

            // Wait for loading animation to disappear
            wait.until(ExpectedConditions.invisibilityOf(loadingAnimation));
            logger.info("Loading animation disappeared");

            // Wait for results form to be present in DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#sollzinsbindungen-form")));
            logger.info("Results form is present in DOM");

            // Wait for results form to be visible
            WebElement resultsForm = Driver.getDriver().findElement(By.cssSelector("#sollzinsbindungen-form"));
            wait.until(ExpectedConditions.visibilityOf(resultsForm));
            logger.info("Results form is now visible");

            // Wait for table head to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".panel-card-table-head")));
            logger.info("Results table head is now visible");

            // Take a screenshot of the results
            File screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get("target/screenshots/results-loaded.png"), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot of results saved as results-loaded.png");

            return true;
        } catch (Exception e) {
            logger.error("Error waiting for results to load: " + e.getMessage());
            return false;
        }
    }
} 