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
 * Page-Klasse für die "Berechnungsergebnis"-Seite.
 * Enthält Elemente und Methoden zur Interaktion mit der Seite, die die Ergebnisse
 * der Finanzierungsberechnung anzeigt.
 */
public class BerechnungsergebnisPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BerechnungsergebnisPage.class);

    @FindBy(css = "#sollzinsbindungen-form")
    private WebElement resultsForm;

    @FindBy(css = "#sollzinsbindungen-form .panel-card")
    private WebElement resultsPanel;

    @FindBy(css = "#sollzinsbindungen-form button.btn.btn--nav-next")
    private WebElement angebotAnfordernButton;

    /**
     * Konstruktor für die BerechnungsergebnisPage.
     * Initialisiert PageFactory-Elemente.
     */
    public BerechnungsergebnisPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    /**
     * Klickt auf den Button "Angebot anfordern", um zur nächsten Seite zu navigieren.
     * Wartet, bis die Ergebnisse vollständig geladen sind und der Button anklickbar ist.
     */
    public void clickAngebotAnfordern() {
        waitForResultsToLoad();
        wait.until(ExpectedConditions.elementToBeClickable(angebotAnfordernButton));
        highlightElement(angebotAnfordernButton);
        //takeScreenshot("angebotAnfordern-before");
        angebotAnfordernButton.click();
        //takeScreenshot("angebotAnfordern-after");
        logger.info("Successfully clicked Angebot Anfordern button");
    }

    /**
     * Wartet, bis die Berechnungsergebnisse geladen sind.
     * 
     * @return true, wenn die Ergebnisse erfolgreich geladen wurden, sonst false
     */
    public boolean waitForResultsToLoad() {
        try {
            // Wait for loading animation to appear first (indicates calculation started)
            WebElement loadingAnimation = driver.findElement(By.cssSelector(".loading-animation"));
            wait.until(ExpectedConditions.visibilityOf(loadingAnimation));
            logger.info("Loading animation appeared");

            // Wait for loading animation to disappear
            wait.until(ExpectedConditions.invisibilityOf(loadingAnimation));
            logger.info("Loading animation disappeared");

            // Wait for results form to be present in DOM
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#sollzinsbindungen-form")));
            logger.info("Results form is present in DOM");

            // Wait for results form to be visible
            WebElement resultsForm = driver.findElement(By.cssSelector("#sollzinsbindungen-form"));
            wait.until(ExpectedConditions.visibilityOf(resultsForm));
            logger.info("Results form is now visible");

            // Wait for table head to be visible
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".panel-card-table-head")));
            logger.info("Results table head is now visible");

            boolean isResultsFormVisible = resultsForm.isDisplayed();
            logger.info("Results form is visible: " + isResultsFormVisible);

            // Take a screenshot of the results
            File screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), Paths.get("target/screenshots/results-loaded.png"), StandardCopyOption.REPLACE_EXISTING);
            logger.info("Screenshot of results saved as results-loaded.png");

            return isResultsFormVisible;
        } catch (Exception e) {
            logger.error("Error waiting for results to load: " + e.getMessage());
            return false;
        }
    }

    /**
     * Prüft, ob ein bestimmter Text in den Ergebnissen angezeigt wird.
     * 
     * @param expectedText Der zu suchende Text
     * @return true, wenn der Text in den Ergebnissen gefunden wurde, sonst false
     */
    public boolean isTextDisplayedInResults(String expectedText) {
        try {
            wait.until(ExpectedConditions.visibilityOf(resultsPanel));
            return resultsPanel.getText().contains(expectedText);
        } catch (Exception e) {
            logger.error("Error checking if text is displayed in results: " + e.getMessage());
            return false;
        }
    }

    /**
     * Erstellt einen Screenshot des aktuellen Zustands der Ergebnisseite.
     * 
     * @param filename Der Name des Screenshots
     * @return true, wenn der Screenshot erfolgreich erstellt wurde, sonst false
     */
    public boolean takeResultsScreenshot(String filename) {
        try {
            // Ensure directory exists
            Files.createDirectories(Paths.get("target/screenshots"));
            
            // Take screenshot
            File scrFile = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.FILE);
            Files.copy(scrFile.toPath(), Paths.get("target/screenshots/" + filename + ".png"), StandardCopyOption.REPLACE_EXISTING);
            
            logger.info("Screenshot saved: {}.png", filename);
            return true;
        } catch (Exception e) {
            logger.error("Error taking screenshot: " + e.getMessage());
            return false;
        }
    }
} 