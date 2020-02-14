/*
By: Dev DeCoste
Description: This program opens Firefox and browses to roimotors.com, opens the new inventory VLP and clicks on the first listing
with custom photos. It then opens the image slideshow and browses a few photos, checks and prints and highlighted
features and internet price.
 */


package org.example;


import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


public class DeCosteSTETest {

    public static void main(String[] args) {


        WebDriver driver = new FirefoxDriver();
        // Open ROI and maximiaze window
        driver.get("https://www.roimotors.com/");
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        //Navigate to New VLP
        driver.findElement(By.xpath("//a[@id=\'content-cta1-button\']")).click();

        // Select vehicle
        driver.findElement(By.cssSelector(".vehicle-card:nth-child(1) .vehicle-card-details-container a")).click();

        //Open slideshow
        driver.findElement(By.cssSelector(".slider-slide:nth-child(1) > img")).click();
        driver.manage().timeouts().implicitlyWait(20, SECONDS); //Intention here is for a brief pause between clicks
        driver.findElement(By.cssSelector(".pswp__button--arrow--right")).click();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.findElement(By.cssSelector(".pswp__button--arrow--right")).click();
        driver.manage().timeouts().implicitlyWait(10, SECONDS);
        driver.findElement(By.cssSelector(".pswp__button--arrow--right")).click();

        //Close slideshow
        driver.findElement(By.cssSelector(".pswp__button--close")).click();
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,1000)");

        //Navigate to Highlighted Features Section

        try {
            /*
            * Waiting 30 seconds for an element to be present on the page, checking
            * for its presence once every 5 seconds.
            * Source: https://selenium.dev/selenium/docs/api/java/org/openqa/selenium/support/ui/FluentWait.html
            * IMPORTANT NOTE: I tried a few different selectors as well as locating by cssSelector and xPath
            * however I am still working on debugging this section
            */

        {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("#hl-features1-app-root")));
        }
            Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                    .withTimeout(30, SECONDS)
                    .pollingEvery(5, SECONDS)
                    .ignoring(NoSuchElementException.class);

            WebElement wElement = wait.until(new Function<WebDriver, WebElement>() {
                public WebElement apply(WebDriver highlightedFeatures) {
                    String hFeatures = highlightedFeatures.toString();
                    System.out.println(hFeatures);
                    highlightedFeatures.manage().timeouts().implicitlyWait(10, SECONDS);
                    return driver.findElement(By.id("#hl-features1-app-root"));

                }

            });

        } catch (NoSuchElementException e) {
            System.out.println("Highlighted Features Element could not be found.");
        }

        js.executeScript("window.scrollBy(1000,0)");
        /* Checks for internetPrice first, if not present checks for MSRP and prints value
         * IMPORTANT NOTE: I got stuck trying to get this to work. I tried a few different selectors as well as
         * locating by cssSelector and xPath however I am still working on debugging this section.
        */

        try {
            WebElement interNetPrice = driver.findElement(By.id("#vdp-final-price-price-value"));
            WebElement MSRP = driver.findElement(By.id("#vdp-msrp-price-value']"));
            if (interNetPrice.isDisplayed()) {
                System.out.println(interNetPrice);
            } else
                System.out.println(MSRP);
        } catch (NoSuchElementException e) {
            System.out.println("MSRP or InternetPrice could not be found.");
        }

    }

}

