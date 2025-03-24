package com.test.pages;

import com.test.utilities.BrowserUtil;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Page Object für die Dokumenten-Upload-Seite des FLEXCHECK Moduls.
 * Behandelt alle Funktionalitäten im Zusammenhang mit dem Dokumenten-Upload.
 */
public class DocumentUploadPage extends BasePage {

    @FindBy(css = "[data-testid='file-upload']")
    private WebElement fileUploadInput;

    @FindBy(css = "[data-testid='upload-button']")
    private WebElement uploadButton;

    @FindBy(css = ".upload-success")
    private WebElement uploadSuccessMessage;

    @FindBy(css = ".upload-error")
    private WebElement uploadErrorMessage;

    @FindBy(css = ".uploaded-file-name")
    private WebElement uploadedFileName;

    /**
     * Lädt eine Datei hoch
     * @param filePath Der absolute Pfad zur hochzuladenden Datei
     * @return true wenn der Upload erfolgreich war
     */
    public boolean uploadDocument(String filePath) {
        BrowserUtil.waitForVisibility(fileUploadInput, 10);
        fileUploadInput.sendKeys(filePath);
        BrowserUtil.waitForClickability(uploadButton, 10);
        uploadButton.click();
        return isUploadSuccessful();
    }

    /**
     * Prüft ob der Upload erfolgreich war
     * @return true wenn die Erfolgsmeldung angezeigt wird
     */
    public boolean isUploadSuccessful() {
        try {
            return BrowserUtil.waitForVisibility(uploadSuccessMessage, 10).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Holt die Fehlermeldung beim Upload
     * @return String mit der Fehlermeldung oder null wenn keine vorhanden
     */
    public String getUploadError() {
        try {
            return BrowserUtil.waitForVisibility(uploadErrorMessage, 5).getText();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Prüft ob die hochgeladene Datei angezeigt wird
     * @param expectedFileName Der erwartete Dateiname
     * @return true wenn der Dateiname übereinstimmt
     */
    public boolean verifyUploadedFileName(String expectedFileName) {
        try {
            String actualFileName = BrowserUtil.waitForVisibility(uploadedFileName, 10).getText();
            return actualFileName.equals(expectedFileName);
        } catch (Exception e) {
            return false;
        }
    }
} 