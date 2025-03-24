package com.test.pages;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.utilities.Driver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page class representing the Final page with calculation results
 */
public class FinalPage extends BasePage {
    private static final Logger logger = LoggerFactory.getLogger(FinalPage.class);

    public FinalPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    @FindBy(xpath = "//*[@id='informationen']")
    private WebElement messageTextArea;

    @FindBy(xpath = "//*[@id='sendeAnfrage']")
    private WebElement submitButton;

    @FindBy(xpath = "//*[@id='datenUebermittlung']/div/div/div[1]/div[1]")
    private WebElement successMessage;

    public void writeMessage() {
        String message = "This is a test message for the financing request.";
        waitForElementToBeClickable(messageTextArea);
        highlightElement(messageTextArea);
        //takeScreenshot("before_writing_message");
        messageTextArea.clear();
        messageTextArea.sendKeys(message);
        //takeScreenshot("after_writing_message");
        logger.info("Wrote message: {}", message);
    }

    public void clickSubmitButton() {
        waitForElementToBeClickable(submitButton);
        highlightElement(submitButton);
        //takeScreenshot("before_clicking_submit");
        submitButton.click();
        //takeScreenshot("after_clicking_submit");
        logger.info("Clicked submit button");
    }

    public void takeScreenshot(String name) {
        try {
            TakesScreenshot ts = (TakesScreenshot) Driver.getDriver();
            File source = ts.getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String destination = "test-output/screenshots/" + name + "_" + timestamp + ".png";
            FileUtils.copyFile(source, new File(destination));
            System.out.println("Screenshot saved: " + destination);
        } catch (Exception e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    public boolean isSuccessMessageDisplayed() {
        waitForPageLoad();
        wait.until(ExpectedConditions.visibilityOf(successMessage));
        System.out.println("\nOverall Verification: " + (successMessage.isDisplayed() ? "✅ PASSED" : "❌ FAILED"));

        return successMessage.isDisplayed();
    }

    public boolean isSuccessMessageDisplayedForFileUpload() {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(driver -> {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                String pageText = (String) js.executeScript(
                    "return Array.from(document.querySelectorAll('*')).map(el => el.textContent).join(' ')"
                );
                
                if (pageText != null) {
                    // Başarı mesajlarını kontrol et
                    boolean uploadSuccess = pageText.contains("Die Datei \"test-document.pdf\" wurden erfolgreich hochgeladen");
                    boolean thankYouSuccess = pageText.contains("Vielen Dank für Ihre Anfrage");
                    
                    System.out.println("\nPage Content Check:");
                    System.out.println("✓ Upload Message Found: " + uploadSuccess);
                    System.out.println("✓ Thank You Message Found: " + thankYouSuccess);
                    
                    return uploadSuccess || thankYouSuccess;
                }
                return false;
            });
            System.out.println("✅ Success: Required messages found on the page");
        } catch (Exception e) {
            System.out.println("❌ Warning: Success messages not found: " + e.getMessage());
        }
        return false;
    }

    public boolean notSuccessMessageDisplayedForFileUpload() {
        JavascriptExecutor js1 = (JavascriptExecutor) Driver.getDriver();
      
        boolean messageFound = false;
        int maxRetries = 10; // 10 kez deneyecek (toplam 5 saniye)
        int retry = 0;
 
 
        while (!messageFound && retry < maxRetries) {
            String bodyText = (String) js1.executeScript("return document.body.innerText;");
            if (bodyText.contains("konnte nicht hochgeladen werden")) {
                System.out.println("Başarı mesajı bulundu!");
                System.out.println(bodyText);
                messageFound = true;
            } else {
                System.out.println("Mesaj bekleniyor...");
            }
           
            try {
                Thread.sleep(500); // 500ms bekle
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry++;
        } 
        return messageFound;
    }
}