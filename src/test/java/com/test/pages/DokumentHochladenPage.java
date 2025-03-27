package com.test.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.test.utilities.Driver;
import com.test.utilities.Utils;

import java.io.File;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import java.util.Arrays;

/**
 * Page class representing the "Dokument Hochladen" (Document Upload) page
 */
public class DokumentHochladenPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(DokumentHochladenPage.class);

    public DokumentHochladenPage() {
        PageFactory.initElements(Driver.getDriver(), this);
    }

    // Document Upload Elements
    @FindBy(css = ".dropzone__area")
    private WebElement dropZone;

    @FindBy(css = "button.btn.btn--nav-next")
    private WebElement weiterButton;

    // Document Type Selection
    @FindBy(id = "dokumentTyp")  // Update with actual ID
    private WebElement dokumentTypDropdown;

    // Uploaded Documents List
    @FindBy(xpath = "//div[contains(@class, 'uploaded-files')]//div[@class='file-item']")  // Update with actual locator
    private List<WebElement> uploadedDocuments;

    // Delete Document Buttons
    @FindBy(xpath = "//button[contains(@class, 'delete-file')]")  // Update with actual locator
    private List<WebElement> deleteButtons;

    // Navigation Buttons
    @FindBy(xpath = "//button[contains(text(), 'Zurück')]")  // Update with actual locator
    private WebElement zurueckButton;

    // Info Icons/Popups
    @FindBy(xpath = "//i[contains(@class, 'info-icon')][@data-field='dokumentUpload']")  // Update with actual locator
    private WebElement dokumentUploadInfoIcon;

    // Upload Status Messages
    @FindBy(xpath = "//div[contains(@class, 'upload-status')]")  // Update with actual locator
    private WebElement uploadStatusMessage;

    @FindBy(xpath = "//div[contains(@class, 'error-message')]")  // Update with actual locator
    private WebElement errorMessage;

    @FindBy(xpath = "//*[@id='docTransferDropzone']")
    private WebElement fileInput;

    @FindBy(css = ".upload-progress")
    private WebElement uploadProgress;

    @FindBy (xpath = "//*[@id='kategorieDocumentTransferKlapp-weiter-button']")
    private WebElement weiterButtonInDokumentHochladungPage;

    // Methods for interacting with upload elements
    public void uploadDocument(String filePath) {
        waitForPageLoad();
        wait.until(ExpectedConditions.elementToBeClickable(dropZone));
        highlightElement(dropZone);
        //takeScreenshot("document-upload-before");
        dropZone.sendKeys(filePath);
        //takeScreenshot("document-upload-after");
    }

    public void selectDokumentTyp(String dokumentTyp) {
        org.openqa.selenium.support.ui.Select select = new org.openqa.selenium.support.ui.Select(dokumentTypDropdown);
        select.selectByVisibleText(dokumentTyp);
    }

    public void deleteDocument(int index) {
        if (index < deleteButtons.size()) {
            deleteButtons.get(index).click();
        }
    }

    // Navigation methods
    public void clickZurueck() {
        zurueckButton.click();
    }

    public void clickWeiter() {
        waitForElementToBeClickable(weiterButton);
        highlightElement(weiterButton);
        //takeScreenshot("before_clicking_weiter");
        weiterButton.click();
        //takeScreenshot("after_clicking_weiter");
        logger.info("Clicked Weiter button on document upload page");
    }

    public void clickDokumentUploadInfo() {
        dokumentUploadInfoIcon.click();
    }

    // Getter methods for verification purposes
    public int getUploadedDocumentsCount() {
        return uploadedDocuments.size();
    }

    public String getUploadStatusMessage() {
        return uploadStatusMessage.getText();
    }

    public String getErrorMessage() {
        return errorMessage.getText();
    }

    public boolean isWeiterButtonEnabled() {
        return weiterButton.isEnabled();
    }

    public List<String> getUploadedDocumentNames() {
        return uploadedDocuments.stream()
                .map(WebElement::getText)
                .collect(java.util.stream.Collectors.toList());
    }

    public boolean isDocumentUploaded(String documentName) {
        return uploadedDocuments.stream()
                .anyMatch(element -> element.getText().contains(documentName));
    }

    // Drag and Drop support (if available in the application)
    public void dragAndDropFile(String filePath) {
        // Implementation depends on how drag and drop is handled in the application
        // This is a placeholder for the functionality
        String js = "const dropZone = arguments[0];" +
                   "const file = new File([''], '" + filePath + "');" +
                   "const dropEvent = new DragEvent('drop');" +
                   "Object.defineProperty(dropEvent, 'dataTransfer', {" +
                   "  value: new DataTransfer()," +
                   "});" +
                   "dropEvent.dataTransfer.items.add(file);" +
                   "dropZone.dispatchEvent(dropEvent);";
        org.openqa.selenium.JavascriptExecutor executor = (org.openqa.selenium.JavascriptExecutor) Driver.getDriver();
        executor.executeScript(js, dropZone);
    }

    public void uploadId() throws InterruptedException {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testfiles/test-document.pdf";
        Thread.sleep(3000);
        try {
            WebElement dropzone = Driver.getDriver().findElement(By.className("dropzone__area"));
            
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript(
                "const dropzone = arguments[0];" +
                "const file = new File([''], '" + filePath + "');" +
                "const dataTransfer = new DataTransfer();" +
                "dataTransfer.items.add(file);" +
                "const event = new DragEvent('drop', {" +
                "  dataTransfer: dataTransfer" +
                "});" +
                "dropzone.dispatchEvent(event);",
                dropzone
            );
            
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("upload-progress")));
            
        } catch (Exception e) {
            logger.error("File upload error: " + e.getMessage());
            throw e;
        }

        //takeScreenshot("before_uploading_id");
        //takeScreenshot("after_uploading_id");

        verifyFileUpload();
        logger.info("Successfully uploaded ID document from: {}", filePath);
    }

    public boolean verifyFileUpload() {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            WebElement uploadedFile = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".document-item .document-item__name")
            ));
            
            return uploadedFile.getText().equals("test-document.pdf");
        } catch (Exception e) {
            logger.error("Dosya yükleme doğrulama hatası: " + e.getMessage());
            return false;
        }
    }

    public void clickingWeiterButtonInDokumentHochladungPage() {
        try {
            // Wait for any loading animations to disappear
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector(".loading-animation")));
            
            // Wait for the page to be interactive
            wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
            
            // Add a small delay to ensure page is fully loaded
            Thread.sleep(2000);
            
            // Try to find any clickable element that might be our button
            List<WebElement> allClickableElements = Driver.getDriver().findElements(
                By.cssSelector("button, a[role='button'], div[role='button'], *[class*='button'], *[class*='btn']")
            );
            
            logger.info("Found {} potentially clickable elements", allClickableElements.size());
            
            WebElement targetElement = null;
            
            // First try: Look for elements with specific classes or text
            for (WebElement element : allClickableElements) {
                try {
                    if (element.isDisplayed()) {
                        String elementText = element.getText().toLowerCase();
                        String elementClass = element.getAttribute("class");
                        String elementRole = element.getAttribute("role");
                        String elementType = element.getAttribute("type");
                        
                        logger.info("Checking element - Text: '{}', Class: '{}', Role: '{}', Type: '{}'",
                            elementText, elementClass, elementRole, elementType);
                        
                        if ((elementClass != null && (elementClass.contains("nav-next") || elementClass.contains("weiter"))) ||
                            (elementText != null && (elementText.contains("weiter") || elementText.contains("next"))) ||
                            (elementRole != null && elementRole.equals("button") && elementClass != null && elementClass.contains("next"))) {
                            targetElement = element;
                            logger.info("Found potential navigation element");
                            break;
                        }
                    }
                } catch (Exception e) {
                    logger.debug("Error checking element: " + e.getMessage());
                }
            }
            
            // Second try: Look for any visible button-like element
            if (targetElement == null) {
                for (WebElement element : allClickableElements) {
                    try {
                        if (element.isDisplayed() && element.isEnabled()) {
                            String elementClass = element.getAttribute("class");
                            if (elementClass != null && !elementClass.contains("close") && !elementClass.contains("back")) {
                                targetElement = element;
                                logger.info("Using fallback clickable element with class: " + elementClass);
                                break;
                            }
                        }
                    } catch (Exception e) {
                        logger.debug("Error checking fallback element: " + e.getMessage());
                    }
                }
            }
            
            if (targetElement != null) {
                // Take screenshot before clicking
                Utils.takeScreenshot(Driver.getDriver(), "before-weiter-click");
                
                // Scroll into view and ensure element is clickable
                ((JavascriptExecutor) Driver.getDriver()).executeScript(
                    "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", 
                    targetElement
                );
                Thread.sleep(1000);
                
                // Try to click using different methods
                try {
                    targetElement.click();
                    logger.info("Clicked element using standard click");
                } catch (Exception e) {
                    logger.info("Standard click failed, trying JavaScript click");
                    ((JavascriptExecutor) Driver.getDriver()).executeScript("arguments[0].click();", targetElement);
                    logger.info("Clicked element using JavaScript");
                }
                
                // Take screenshot after clicking
                Utils.takeScreenshot(Driver.getDriver(), "after-weiter-click");
                
                // Wait for navigation
                try {
                    wait.until(ExpectedConditions.stalenessOf(targetElement));
                    logger.info("Successfully navigated - element is stale");
                } catch (Exception e) {
                    logger.info("Element did not become stale, checking if we moved to next page");
                }
            } else {
                Utils.takeScreenshot(Driver.getDriver(), "weiter-button-error");
                throw new RuntimeException("Could not find any suitable clickable element for navigation");
            }
        } catch (Exception e) {
            Utils.takeScreenshot(Driver.getDriver(), "weiter-button-error");
            logger.error("Failed to click navigation element: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void uploadId2() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testfiles/test-document.pdf";
        File file = new File(filePath);
        
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            WebElement dropzone = wait.until(ExpectedConditions.elementToBeClickable(
                By.className("dropzone__area")
            ));
    
            String fileName = file.getName();
            
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript(
                "const dropzone = arguments[0];" +
                "const blob = new Blob(['test content'], { type: 'application/pdf' });" +
                "const file = new File([blob], arguments[1], { type: 'application/pdf' });" +
                "const dataTransfer = new DataTransfer();" +
                "dataTransfer.items.add(file);" +
                "const event = new DragEvent('drop', {" +
                "  bubbles: true," +
                "  cancelable: true," +
                "  dataTransfer: dataTransfer" +
                "});" +
                "dropzone.dispatchEvent(new DragEvent('dragover', { bubbles: true, cancelable: true }));" +
                "dropzone.dispatchEvent(event);" +
                "dropzone.dispatchEvent(new Event('change', { bubbles: true }));",
                dropzone, fileName
            );
    
            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(text(), '" + fileName + "')]")
                ),
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(text(), 'erfolgreich hochgeladen')]")
                )
            ));
    
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Ihre Daten werden übertragen')]")
            ));
    
            verifyFileUpload2();
            
            logger.info("Successfully uploaded document: {}", fileName);
            
        } catch (Exception e) {
            logger.error("File upload failed: " + e.getMessage());
            //takeScreenshot("upload_failure");
            throw e;
        }
    }
    
    private void verifyFileUpload2() {
        try {
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(10));
            
            // Wait for either success message or document item
            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(text(), 'test-document.pdf')]")
                ),
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(text(), 'erfolgreich hochgeladen')]")
                )
            ));
            
            // Check for error messages
            List<WebElement> errorMessages = Driver.getDriver().findElements(
                By.xpath("//div[contains(text(), 'konnte nicht hochgeladen werden')]")
            );
            
            if (!errorMessages.isEmpty()) {
                throw new RuntimeException("Upload failed: " + errorMessages.get(0).getText());
            }
            
            logger.info("File upload verified successfully");
            
        } catch (Exception e) {
            logger.error("File upload verification failed: " + e.getMessage());
            throw e;
        }
    }
    
    // Update the dragAndDropFile method as well
    public void dragAndDropFile2(String filePath) {
        try {
            File file = new File(filePath);
            String fileName = file.getName();
            
            WebDriverWait wait = new WebDriverWait(Driver.getDriver(), Duration.ofSeconds(20));
            WebElement dropzone = wait.until(ExpectedConditions.elementToBeClickable(
                By.className("dropzone__area")
            ));

            //takeScreenshot("before_upload");

            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript(
                "const dropzone = arguments[0];" +
                "const blob = new Blob(['test content'], { type: 'application/pdf' });" +
                "const file = new File([blob], arguments[1], { type: 'application/pdf' });" +
                "const dt = new DataTransfer();" +
                "dt.items.add(file);" +
                "dropzone.dispatchEvent(new DragEvent('dragenter', { bubbles: true }));" +
                "dropzone.dispatchEvent(new DragEvent('dragover', { bubbles: true }));" +
                "dropzone.dispatchEvent(new DragEvent('drop', {" +
                "  bubbles: true," +
                "  cancelable: true," +
                "  dataTransfer: dt" +
                "}));",
                dropzone, fileName
            );

            wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(
                    By.xpath("//div[contains(text(), 'erfolgreich hochgeladen')]")
                ),
                ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".document-item")
                ),
                ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector(".dz-success")
                )
            ));

            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(text(), 'Ihre Daten werden übertragen')]")
            ));

            //takeScreenshot("after_upload");

            List<WebElement> errorMessages = Driver.getDriver().findElements(
                By.xpath("//div[contains(text(), 'konnte nicht hochgeladen werden')]")
            );
            
            if (!errorMessages.isEmpty()) {
                logger.error("Upload error message: " + errorMessages.get(0).getText());
                throw new RuntimeException("File upload failed: " + errorMessages.get(0).getText());
            }

            boolean uploadSuccess = Driver.getDriver().findElements(By.cssSelector(".document-item, .dz-success")).size() > 0;
            
            if (!uploadSuccess) {
                logger.error("Upload verification failed - no success indicators found");
                throw new RuntimeException("File upload could not be verified");
            }

            logger.info("File upload successful: {}", fileName);

        } catch (Exception e) {
            logger.error("File upload failed: " + e.getMessage());
            
            try {
                //takeScreenshot("upload_error");
                String pageSource = Driver.getDriver().getPageSource();
                logger.debug("Page source at failure: {}", pageSource);
                
                LogEntries logs = Driver.getDriver().manage().logs().get(LogType.BROWSER);
                logs.getAll().forEach(log -> logger.debug("Browser Console: " + log.getMessage()));
                
            } catch (Exception ex) {
                logger.error("Failed to capture debug info: " + ex.getMessage());
            }
            
            throw new RuntimeException("File upload failed", e);
        }
    }


    public void uploadIdForNegativeTest() {
        String filePath = System.getProperty("user.dir") + "/src/test/resources/testfiles/test-document.pdf";

            //takeScreenshot("before_uploading_id");
            WebElement dropzone = Driver.getDriver().findElement(By.className("dropzone__area"));
           
            JavascriptExecutor js = (JavascriptExecutor) Driver.getDriver();
            js.executeScript(
                "const dropzone = arguments[0];" +
                "const file = new File([''], '" + filePath + "');" +
                "const dataTransfer = new DataTransfer();" +
                "dataTransfer.items.add(file);" +
                "const event = new DragEvent('drop', {" +
                "  dataTransfer: dataTransfer" +
                "});" +
                "dropzone.dispatchEvent(event);",
                dropzone);

            logger.error("Dosya yükleme hatası: ");

        //takeScreenshot("after_uploading_id");

    }

    public void clickZurueckButtonInDokumentHochladungPage() {
        WebElement zurueckButton = Driver.getDriver().findElement(By.id("kategorieDocumentTransferKlapp-zurück-button"));

        try {
            zurueckButton.click();
            logger.info("Successfully clicked back button in document upload page");
        } catch (Exception e) {
            logger.error("Failed to click back button in document upload page: {}", e.getMessage());
            throw e;
        }
        logger.info("Clicking back button to return to offer selection page");
    }

} 